package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Hilfefenster der Anwendung mit Hilfetexten zur groben Erlauterung.
 * 
 * @author Nico & Anna & Daniel
 *
 */
public class HilfeFenster {
	
	// *** Eigenschaften *** //

	private JFrame frame;
	private static final String HILFETEXT = "\nPrüfungstool der Gruppe F18."
			+ "\n\nKlausur Importieren:\nHier kann eine existierende Klausur als CSV eingelesen werden."
			+ "\n\nKlausur Exportieren:\nHier kann eine erstellte Klausur als CSV oder als PDF exportiert werden."
			+ "\n\nKlausur Erstellen:\nEs öffnet sich ein neues Fenster mit einer Tabellenansicht, in der man eine Klausur aus bereits existierende Fragen zusammenstellen kann."
			+ "\n\nFrage Hinzufügen:\nIn diesem Fenster erstellt man neu Fragen.";

	// *** Konstruktor *** //
	
	public HilfeFenster() {
		initialize();

		frame.setVisible(true);
	}
	
	// *** Methoden *** //

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 435, 630);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Hilfe Klausurtool");
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 429, 601);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Ueberschrift
		JLabel lblPrfungsgenerierertool = new JLabel("<html><u>Hilfe-Klausurtool</u></html>");
		lblPrfungsgenerierertool.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPrfungsgenerierertool.setBounds(107, 11, 192, 31);
		panel.add(lblPrfungsgenerierertool);

		// Textarea - Hilfetext
		JTextArea textAreaHilfetext = new JTextArea();
		textAreaHilfetext.setFont(new Font("Arial", Font.PLAIN, 15));
		textAreaHilfetext.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textAreaHilfetext.setText(HILFETEXT);
		textAreaHilfetext.setEditable(false);
		textAreaHilfetext.setLineWrap(true);
		textAreaHilfetext.setWrapStyleWord(true);
		textAreaHilfetext.setBounds(10, 53, 399, 365);
		panel.add(textAreaHilfetext);

		// Label-Bild
		ImageIcon imageIcon = GuiUtils.bekommeSkaliertesImageIcon("images/fragezeichen.PNG", 39, 39);

		JLabel lblBild = new JLabel();
		lblBild.setIcon(imageIcon);
		lblBild.setBounds(370, 11, 39, 39);
		panel.add(lblBild);

		// Label-Bild 2
		imageIcon = GuiUtils.bekommeSkaliertesImageIcon("images/hilfeIcon.PNG", 160, 160);

		JLabel lblBild2 = new JLabel();
		lblBild2.setIcon(imageIcon);
		lblBild2.setBounds(227, 403, 180, 180);
		panel.add(lblBild2);
	}

}
