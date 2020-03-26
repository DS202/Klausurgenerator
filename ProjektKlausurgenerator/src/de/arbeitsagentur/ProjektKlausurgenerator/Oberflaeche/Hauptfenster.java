package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Date;

import javax.swing.ImageIcon;
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

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Filetype;

public class Hauptfenster {

	private JFrame frame;
	private JPanel panel;

	public Hauptfenster() {
		initialize();
		initMenue();

		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 915, 675);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Prüfungstool  || " + System.getProperty("user.name"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 899, 635);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Ueberschrift
		JLabel lblPrfungsgenerierertool = new JLabel("<html><u>Pr\u00FCfungsgenerierer-Tool</u></html>");
		lblPrfungsgenerierertool.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPrfungsgenerierertool.setBounds(293, 33, 278, 31);
		panel.add(lblPrfungsgenerierertool);

		// Label-Bild
		URL imageUrl = Hauptfenster.class.getClassLoader().getResource("images/pruefungIcon.PNG");
		// Fängt im Ordner /resources/ an.
		// imageUrl = this.getClass().getResource("images/pruefungIcon.PNG");
		// -> Geht nicht.
		ImageIcon imageIcon = new ImageIcon(imageUrl); // Bild
		// laden
		Image image = imageIcon.getImage(); // umwandeln
		Image newimg = image.getScaledInstance(400, 350, java.awt.Image.SCALE_SMOOTH); // smooth Skalieren
		imageIcon = new ImageIcon(newimg); // zurueck umwandeln
		// TODO:
		JLabel lblBild = new JLabel();
		lblBild.setIcon(imageIcon);
		lblBild.setBounds(220, 75, 400, 350);
		panel.add(lblBild);

		// Statusleiste
		JPanel panelStatusleiste = new JPanel();
		panelStatusleiste.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelStatusleiste.setBounds(0, 615, 900, 21);
		panel.add(panelStatusleiste);
		panelStatusleiste.setLayout(null);

		JLabel lblDatum = new JLabel("Datum:" + new Date());
		lblDatum.setBounds(631, 0, 269, 18);
		panelStatusleiste.add(lblDatum);
	}

	private void initMenue() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 900, 21);
		panel.add(menuBar);

		JMenu mnMenue = new JMenu("Men\u00FC");
		menuBar.add(mnMenue);

		JMenuItem mntmFrageHinzuefgen = new JMenuItem("Hilfe");
		mntmFrageHinzuefgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hilfe anzeigen");
				JOptionPane.showMessageDialog(null, "Prüfungstool der Gruppe F18", "Hilfe",
						JOptionPane.INFORMATION_MESSAGE);
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

				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("csv", "CSV");

				chooser.setFileFilter(filter); // Filepicker auf CSV einschraenken
				chooser.showDialog(null, "Klausur auswählen");

				File file = chooser.getSelectedFile();
				if (file != null) {
					if (file.exists()) {
						// TODO:Uebergabe an Logik!
						System.out.println("Übergabe an Logik" + "   " + file.getAbsolutePath());
					}else {
						JOptionPane.showMessageDialog(null, "Ausgewählte Datei existiert nicht.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});

		JMenuItem mntmexportieren = new JMenuItem("...Exportieren");
		mntmexportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");
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

					// TODO:File speichern nach erhalt von Logik
					System.out.println("Save as file: " + fileToSave.getAbsolutePath() + "." + type);
				}
			}
		});

		JMenuItem mntmerstellen = new JMenuItem("...Erstellen");
		mntmerstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Erstellen");
			}
		});

		mnKlausur.add(mntmexportieren);
		mnKlausur.add(mntmimportieren);
		mnKlausur.add(mntmerstellen);
	}
}
