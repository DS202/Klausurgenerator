package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.arbeitsagentur.ProjektKlausurgenerator.controller.Controller;
import de.arbeitsagentur.ProjektKlausurgenerator.enums.Filetype;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;

/** Hauptfenster der Anwendung
 * 
 * @author Nico & Anna & Daniel & Yannick & Patrick & Karl & Sven & Philipp 
 *
 */
public class Hauptfenster {

	private JFrame frame;
	private JPanel panel;
	private GuiUtils guiUtils = new GuiUtils();
	private Controller controller = new Controller();
	private List<AbstractFrage> fragenliste;

	public Hauptfenster() {

		this.fragenliste = controller.getAlleFragenAusFragenCSV();

		initialize();
		initMenue();
		initButtons();

		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 915, 675);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Prüfungstool  || " + System.getProperty("user.name"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 909, 646);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Ueberschrift
		JLabel lblPrfungsgenerierertool = new JLabel("<html><u>Pr\u00FCfungsgenerierer-Tool</u></html>");
		lblPrfungsgenerierertool.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPrfungsgenerierertool.setBounds(293, 33, 278, 31);
		panel.add(lblPrfungsgenerierertool);

		// Unter-Ueberschrift
		JLabel lblKlausur = new JLabel("<html><u><b>Klausur:</u></b></html>");
		lblKlausur.setFont(new Font("Arial", Font.PLAIN, 12));
		lblKlausur.setBounds(10, 530, 74, 15);
		panel.add(lblKlausur);

		// Label-Bild
		ImageIcon imageIcon = GuiUtils.bekommeSkaliertesImageIcon("images/pruefungIcon2.PNG", 350, 440);

		JLabel lblBild = new JLabel();
		lblBild.setIcon(imageIcon);
		lblBild.setBounds(265, 75, 350, 440);
		panel.add(lblBild);

		// Statusleiste
		JPanel panelStatusleiste = new JPanel();
		panelStatusleiste.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelStatusleiste.setBounds(0, 625, 915, 20);
		panel.add(panelStatusleiste);
		panelStatusleiste.setLayout(null);

		JLabel lblDatum = new JLabel(GuiUtils.holeAktuellesDatum());
		lblDatum.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDatum.setBounds(748, 0, 152, 18);
		panelStatusleiste.add(lblDatum);
	}

	private void initMenue() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 915, 21);
		panel.add(menuBar);

		JMenu mnMenue = new JMenu("Men\u00FC");
		menuBar.add(mnMenue);

		JMenuItem mntmFragehinzufgen = new JMenuItem("FrageHinzuf\u00FCgen");
		Hauptfenster tmpFenster = this;
		mntmFragehinzufgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EinzelneFrageHinzuFenster(tmpFenster);
			}
		});
		mnMenue.add(mntmFragehinzufgen);

		JMenuItem mntmFrageHinzuefgen = new JMenuItem("Hilfe");
		mntmFrageHinzuefgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hilfe anzeigen");
				new HilfeFenster();
				guiUtils.erzeugeWindowsNotification("Authoren",
						"Anna Sperling, Daniel Schmidt, Nico Loss, Sven Günther, Karl Ullrich, Patrick Özer-Peuerle, Philipp Maier, Yannick Marchl, David Jager, David Boroviak",
						"Info");
			}
		});

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		mnMenue.add(mntmFrageHinzuefgen);
		mnMenue.add(mntmBeenden);

		JMenu mnKlausur = new JMenu("Klausur...");
		menuBar.add(mnKlausur);

		JMenuItem mntmimportieren = new JMenuItem("...Importieren");
		mntmimportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				aktionImport();

			}
		});

		JMenuItem mntmexportieren = new JMenuItem("...Exportieren");
		mntmexportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				aktionExport();

			}
		});

		JMenuItem mntmerstellen = new JMenuItem("...Erstellen");
		mntmerstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				aktionErstellen();
			}
		});

		mnKlausur.add(mntmexportieren);
		mnKlausur.add(mntmimportieren);
		mnKlausur.add(mntmerstellen);
	}

	private void initButtons() {

		JButton btnErstellen = new JButton("Erstellen");
		btnErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktionErstellen();
			}
		});
		btnErstellen.setBounds(80, 560, 125, 23);
		panel.add(btnErstellen);

		JButton buttonImportieren = new JButton("Importieren");
		buttonImportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktionImport();
			}
		});
		buttonImportieren.setBounds(380, 560, 125, 23);
		panel.add(buttonImportieren);

		JButton buttonExportieren = new JButton("Exportieren");
		buttonExportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktionExport();
			}
		});
		buttonExportieren.setBounds(700, 560, 125, 23);
		panel.add(buttonExportieren);
	}

	// *** Aktionen Export / Import / Erstellen *** //

	private void aktionExport() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Speicherort Festlegen");
		fileChooser.setAcceptAllFileFilterUsed(false);// deaktiviert "eigene Datei" Auswahlmoeglickeit

		FileFilter filtercsv = new FileNameExtensionFilter("CSV", "csv");
		FileFilter filterpdf = new FileNameExtensionFilter("PDF", "pdf");

		fileChooser.addChoosableFileFilter(filtercsv);
		fileChooser.addChoosableFileFilter(filterpdf);

		int userSelection = fileChooser.showSaveDialog(frame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {

			File fileToSave = fileChooser.getSelectedFile();
			FileFilter filter = fileChooser.getFileFilter();
			Filetype type = null;

			if (filter.equals(filtercsv)) {
				type = Filetype.CSV;
			} else {
				type = Filetype.PDF;
			}

			// TODO:File speichern nach Erhalt von Logik
			System.out.println("Save as file: " + fileToSave.getAbsolutePath() + "." + type);
		}
	}

	private void aktionImport() {

		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("csv", "CSV");

		chooser.setFileFilter(filter); // Filepicker auf CSV einschraenken
		chooser.showDialog(null, "Klausur auswählen");

		File file = chooser.getSelectedFile();
		if (file != null) {
			if (file.exists()) {
				// TODO:Uebergabe an Logik!
				System.out.println("Übergabe an Logik" + "   " + file.getAbsolutePath());
			} else {
				JOptionPane.showMessageDialog(null, "Ausgewählte Datei existiert nicht.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void aktionErstellen() {
		if (fragenliste == null) {
			System.out.println("Erstellen");
			new FragenTabelleFenster(fragenliste);
		} else {
			System.out.println("Erstellen");
			new FragenTabelleFenster(fragenliste);
		}

	}
	
	/**
	 * Neu Einlesen der CSV-Datei, nachdem eine Frage hinzugefuegt wurde.
	 */
	protected void update() {
		this.fragenliste = controller.getAlleFragenAusFragenCSV();
	}

}
