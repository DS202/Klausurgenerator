package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

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
		ImageIcon imageIcon = new ImageIcon(
				"D:\\Eclipse\\Workspace\\Klausurgenerator\\ProjektKlausurgenerator\\resources\\images\\pruefungIcon.PNG"); // Bild
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

		JMenuItem mntmFragenAnzeigen = new JMenuItem("Fragentabelle anzeigen");
		mntmFragenAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FragenTabelleFenster();
			}
		});

		JMenuItem mntmFrageHinzuefgen = new JMenuItem("Frage Hinzuf\u00FCgen");
		mntmFrageHinzuefgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("frage hinzu...");
			}
		});

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		mnMenue.add(mntmFrageHinzuefgen);
		mnMenue.add(mntmFragenAnzeigen);
		mnMenue.add(mntmBeenden);

		JMenu mnHilfe = new JMenu("Hilfe");
		mnHilfe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Hilfe");
			}
		});

		JMenu mnKlausur = new JMenu("Klausur...");
		menuBar.add(mnKlausur);

		JMenuItem mntmimportieren = new JMenuItem("...Importieren");
		mntmimportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("import");
			}
		});

		JMenuItem mntmexportieren = new JMenuItem("...Exportieren");
		mntmexportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("export");
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
		menuBar.add(mnHilfe);
	}
}
