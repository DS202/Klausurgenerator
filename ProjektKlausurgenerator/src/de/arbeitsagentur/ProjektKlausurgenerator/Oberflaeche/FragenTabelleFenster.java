package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FragenTabelleFenster {

	private JFrame frame;
	private JPanel panel;
	private JTable table;
	private JScrollPane scrollPane;
	private final static DefaultTableModel unchangedModell = new DefaultTableModel(new Object[][] { { null, null, null, null, null },
			{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
			{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
			{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
			{ null, null, null, null, null }, { null, null, null, null, null }, },
			new String[] { "Frage", "Kategorie", "Schwierigkeit", "Lösung", "ZYX" });

	private DefaultTableModel modell = rewriteModell(unchangedModell);

	public FragenTabelleFenster() {
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
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 899, 636);
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
			}
		});
		btnSpeichern.setBounds(779, 602, 110, 23);
		panel.add(btnSpeichern);

		JButton btnZurcksetzten = new JButton("Zur\u00FCcksetzten");
		btnZurcksetzten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Zurücksetzen");
				panel.remove(scrollPane);

//				Vector<String> vector = new Vector<String>();
//				vector.addAll(Arrays.asList(new String[] { "Frage", "Kategorie", "Schwierigkeit", "Lösung", "ZYX" }));
//				modell = new DefaultTableModel(unchangedModell.getDataVector(), vector); // Zuruecksetzen des Modells
				modell = rewriteModell(unchangedModell);

				ausgabe(unchangedModell);
				
				initTable(modell);
			}
		});
		btnZurcksetzten.setBounds(10, 602, 120, 23);
		panel.add(btnZurcksetzten);
	}

	private void initTable(DefaultTableModel modell) {
		// Tabelle
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 879, 474);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(modell);
	}

	private DefaultTableModel rewriteModell(DefaultTableModel data) {
		DefaultTableModel modell = null;

		Vector<String> vector = new Vector<String>();
		vector.addAll(Arrays.asList(new String[] { "Frage", "Kategorie", "Schwierigkeit", "Lösung", "ZYX" }));
		modell = new DefaultTableModel(data.getDataVector(), vector); // Zuruecksetzen des Modells

//		modell = new DefaultTableModel(new Object[][] { { null, null, null, null, null },
//				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
//				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
//				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
//				{ null, null, null, null, null }, { null, null, null, null, null }, },
//				new String[] { "Frage", "Kategorie", "Schwierigkeit", "Lösung", "ZYX" });

		return modell;
	}
	
	private void ausgabe(DefaultTableModel data) {
		
		Vector<Vector<String>> vector =data.getDataVector();
		
		for (int i = 0; i < vector.size(); i++) {
			for (int j = 0; j < vector.get(i).size(); j++) {
				System.out.println(vector.get(i).get(j));
			}
			
		}
		
	}
	
}
