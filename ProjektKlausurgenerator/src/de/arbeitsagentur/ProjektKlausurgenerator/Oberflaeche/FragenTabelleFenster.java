package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Klausurgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Loesungsgenerator;

public class FragenTabelleFenster {

	private JFrame frame;
	private JPanel panel;
	private JTable table;
	private JScrollPane scrollPane;
	private static final DefaultTableModel unchangedModell = new DefaultTableModel(
			new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
					{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
					{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
					{ null, null, null, null }, { null, null, null, null }, { null, null, null, null }, },
			new String[] { "Frage", "Typ", "Punkte", "Schwierigkeitsgrad" });

	private DefaultTableModel modell = rewriteModell(unchangedModell);
	private List<AbstractFrage> alleFragenliste;
	private List<AbstractFrage> klausurFragenListe = new ArrayList<>();
	private Boolean zuruecksetzen = false;
	private JLabel lblKlausurPunkte;

	public FragenTabelleFenster() {
		initialize();
		initTable(modell);

		frame.setVisible(true);
	}

	public FragenTabelleFenster(List<AbstractFrage> fragenliste) {

		this.alleFragenliste = fragenliste;

		initialize();
		initTable(modell);

		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 915, 675);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Fragentabelle");
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 909, 646);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Ueberschrift
		JLabel lblPrfungsgenerierertool = new JLabel("<html><u>Pr\u00FCfungsgenerierer-Tool</u></html>");
		lblPrfungsgenerierertool.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPrfungsgenerierertool.setBounds(293, 33, 278, 31);
		panel.add(lblPrfungsgenerierertool);

		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnAbbrechen.setBounds(659, 602, 110, 23);
		panel.add(btnAbbrechen);

		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Speichern");

				klausurAlsPdfErstellen();

			}
		});
		btnSpeichern.setBounds(779, 602, 110, 23);
		panel.add(btnSpeichern);

		JButton btnZurcksetzten = new JButton("Zur\u00FCcksetzen");
		btnZurcksetzten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("Zurücksetzen");
				panel.remove(scrollPane);

				modell = rewriteModell(unchangedModell);
				zuruecksetzen = true;
				// ausgabe(unchangedModell);

				initTable(modell);
			}
		});
		btnZurcksetzten.setBounds(10, 602, 120, 23);
		panel.add(btnZurcksetzten);

		JButton btnPlus = new JButton("+");
		FragenTabelleFenster fenster = this;
		btnPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Plus");
				new FrageAuswaehlenFenster(fenster, alleFragenliste);
			}
		});
		btnPlus.setBounds(800, 11, 89, 23);
		panel.add(btnPlus);

		lblKlausurPunkte = new JLabel("Anzahl Klausur-Punkte: " + getKlausurpunkte());
		lblKlausurPunkte.setFont(new Font("Arial", Font.PLAIN, 12));
		lblKlausurPunkte.setBounds(159, 602, 155, 23);
		panel.add(lblKlausurPunkte);
	}

	/*
	 * Zuruecksetzen + Hinzufuegen von Elementen zur Table.
	 * 
	 * @param modell derzeitiges Defaultlistmodell.
	 */
	private void initTable(DefaultTableModel modell) {
		// Tabelle
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 889, 474);
		scrollPane.setBackground(Color.white);
		panel.add(scrollPane);

		if (Boolean.TRUE.equals(zuruecksetzen)) {
			table = new JTable();
			table.setModel(modell);
			zuruecksetzen = false;
			lblKlausurPunkte.setText("Anzahl Klausur-Punkte: 0");
			klausurFragenListe = new ArrayList<>();
		} else if (klausurFragenListe != null) {
			table = tableFuellen();
		} else {
			table = new JTable();
			table.setModel(modell);
		}

		table.setBackground(Color.white);
		scrollPane.setViewportView(table);
	}

	// *** Hilfsmethoden *** //

	/** Fuellt eine JTable mit Daten aus klausurFragenListe und gibt sie zurueck.
	 * 
	 * @return Gefuellte JTable.
	 */
	private JTable tableFuellen() {

		String[] columnNamesBewertung = { "Frage", "Typ", "Punkte", "Schwierigkeitsgrad" };
		Object[][] dataBewertung = new Object[klausurFragenListe.size()][4];

		for (int i = 0; i < klausurFragenListe.size(); i++) {
			if (klausurFragenListe.get(i) != null) {
				dataBewertung[i][0] = klausurFragenListe.get(i).getFrageText();
				dataBewertung[i][1] = klausurFragenListe.get(i).getFrageTyp();
				dataBewertung[i][2] = klausurFragenListe.get(i).getPunkte();
				dataBewertung[i][3] = klausurFragenListe.get(i).getSchwierigkeitsgrad();
			}
		}

		return new JTable(dataBewertung, columnNamesBewertung);
	}

	/** Ermittelt die Gesamtpunktzahl an Punkten derzeit in klausurFragenListe.
	 * 
	 * @return Double Wert (Anzahl der Pruefungspunkte.)
	 */
	private Double getKlausurpunkte() {

		Double result = 0.00;

		for (int i = 0; i < klausurFragenListe.size(); i++) {
			result += klausurFragenListe.get(i).getPunkte();
		}

		return result;
	}

	/** Methode zum "Leeren" des DeafultTableModels.
	 * 
	 * @param data DefaulttableModell voll
	 * @return DefaulttableModell leer
	 */
	private DefaultTableModel rewriteModell(DefaultTableModel data) {
		DefaultTableModel modell = null;

		modell = new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null }, },
				new String[] { "Frage", "Typ", "Punkte", "Schwierigkeitsgrad" });

		return modell;
	}

//	private void ausgabe(DefaultTableModel data) {
//
//		Vector<Vector<String>> vector = data.getDataVector();
//
//		for (int i = 0; i < vector.size(); i++) {
//			for (int j = 0; j < vector.get(i).size(); j++) {
//				System.out.println(vector.get(i).get(j));
//			}
//		}
//	}

	/**
	 * Erstellt eine Klausur als PDF (Zwei unterschiedliche Exceptions - 1.
	 * Erstellen der Klausur - 2. Erstellen des Lösungsbogens)
	 */
	private void klausurAlsPdfErstellen() {
		Klausur berechneteKlausur = new Klausur(getKlausurpunkte(), "Test-Klausur", klausurFragenListe);

		try {
			new Klausurgenerator().createKlausur(berechneteKlausur);

		} catch (Exception exception) {
			System.out.println("Fehler beim Erzeugen der Klausur.");
			exception.printStackTrace();
		}

		try {
			new Loesungsgenerator().createKlausur(berechneteKlausur);
		} catch (Exception e) {
			System.out.println("Fehler beim Erzeugen der Lösung.");
			e.printStackTrace();
		}
	}

	// *** Eine Art Setter zum setzen von Fragen in die Klausur *** //

	public void addElementToKlausur(AbstractFrage frage) {
		klausurFragenListe.add(frage);

		initTable(modell);

		lblKlausurPunkte.setText("Anzahl Klausur-Punkte: " + getKlausurpunkte());
	}

}
