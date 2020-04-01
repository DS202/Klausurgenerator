package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;

public class EinzelneFrageHinzuFenster {

	private JFrame frame;
	private JPanel panel;
	private JPanel updatePanel = new JPanel();
	private JTextField textFieldFrage;
	private JTextField txtSeminar;
	private String letzterFragenTyp = "Multichoice";

	private JTextField textField_1Antwort;
	private JTextField textField_2Antwort;
	private JTextField textField_3Antwort;
	private JTextField textField_4Antwort;

	public EinzelneFrageHinzuFenster() {
		initialize();

		frame.setVisible(true);
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 440, 590);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Fragentabelle");
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 434, 561);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Ueberschrift
		JLabel lblKlausur = new JLabel("<html><u><b>Frage hinzuf�gen</u></b></html>");
		lblKlausur.setFont(new Font("Arial", Font.PLAIN, 16));
		lblKlausur.setBounds(144, 11, 135, 24);
		panel.add(lblKlausur);

		// Fragentyp
		JComboBox<String> comboBoxTyp = new JComboBox<>();
		comboBoxTyp.setBackground(Color.white);
		comboBoxTyp.setModel(new DefaultComboBoxModel<String>(new String[] { "Multichoice", "Freitext" }));
		comboBoxTyp.setBounds(10, 82, 179, 24);
		comboBoxTyp.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (!comboBoxTyp.getSelectedItem().equals(letzterFragenTyp)) {

					letzterFragenTyp = comboBoxTyp.getSelectedItem() + "";

					if (comboBoxTyp.getSelectedItem().equals("Multichoice")) {
						updateMultichoice();
					} else {
						updateFreitextfrage();
					}
				}
				panel.repaint();
			}
		});
		panel.add(comboBoxTyp);

		JLabel lblFragentyp = new JLabel("Fragentyp:");
		lblFragentyp.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFragentyp.setBounds(10, 57, 70, 14);
		panel.add(lblFragentyp);

		// Label-Bild
		ImageIcon imageIcon = GuiUtils.getScaledImageIcon("images/hinzu.PNG", 39, 39);

		JLabel lblBild = new JLabel();
		lblBild.setIcon(imageIcon);
		lblBild.setBounds(370, 11, 39, 39);
		panel.add(lblBild);

		// Frage
		JLabel lblFrage = new JLabel("Frage:");
		lblFrage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFrage.setBounds(10, 135, 70, 14);
		panel.add(lblFrage);

		textFieldFrage = new JTextField();
		textFieldFrage.setToolTipText("Frage hier eingeben.");
		textFieldFrage.setBounds(10, 160, 404, 20);
		panel.add(textFieldFrage);
		textFieldFrage.setColumns(10);

		// Schwierigkeit
		JLabel lblSchwierigkeit = new JLabel("Schwierigkeit:");
		lblSchwierigkeit.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSchwierigkeit.setBounds(10, 205, 89, 14);
		panel.add(lblSchwierigkeit);

		JComboBox<Schwierigkeitsgrad> comboBoxSchwierigkeit = new JComboBox<>();
		comboBoxSchwierigkeit.setBackground(Color.white);
		comboBoxSchwierigkeit.setModel(new DefaultComboBoxModel<Schwierigkeitsgrad>(Schwierigkeitsgrad.values()));
		comboBoxSchwierigkeit.setBounds(10, 230, 135, 20);
		panel.add(comboBoxSchwierigkeit);

		// Punkte
		JLabel lblPunkte = new JLabel("Punkte:");
		lblPunkte.setFont(new Font("Arial", Font.PLAIN, 12));
		lblPunkte.setBounds(245, 57, 46, 14);
		panel.add(lblPunkte);

		JSpinner spinnerPunkte = new JSpinner();
		spinnerPunkte.setBackground(Color.white);
		spinnerPunkte.setModel(new SpinnerNumberModel(0.5, 0.5, 100.0, 0.5));// 0.05=Double //0.5=StepSize
		spinnerPunkte.setBounds(245, 82, 81, 24);
		panel.add(spinnerPunkte);

		// Seminar
		JLabel lblSeminar = new JLabel("Seminar:");
		lblSeminar.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSeminar.setBounds(245, 206, 70, 14);
		panel.add(lblSeminar);

		txtSeminar = new JTextField();
		txtSeminar.setToolTipText("Seminar eingeben");
		txtSeminar.setBounds(245, 230, 157, 20);
		panel.add(txtSeminar);
		txtSeminar.setColumns(10);

		// Speichern Button
		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!txtSeminar.getText().isEmpty() && !textFieldFrage.getText().isEmpty()) {
					System.out.println("Frage Speichern");
					// TODO: Einzelne Frage Speichern in CSV --> Logik!!
					JOptionPane.showMessageDialog(null, "Pr�fungsfrage erfolgreich gespeichert.", "Speichern",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Alle Felder bitte ausf�llen.", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSpeichern.setBounds(304, 517, 110, 23);
		panel.add(btnSpeichern);

		updateMultichoice();
	}

	// *** Update-Methoden *** //

	private void updateFreitextfrage() {

		panel.remove(updatePanel);

		updatePanel = new JPanel();
		updatePanel.setBounds(10, 275, 404, 213);
		updatePanel.setBackground(Color.WHITE);
		panel.add(updatePanel);
		updatePanel.setLayout(null);

		textField_1Antwort = new JTextField();
		textField_1Antwort.setToolTipText("Antwort");
		textField_1Antwort.setBounds(10, 40, 200, 20);
		updatePanel.add(textField_1Antwort);

		JLabel lblRichtigeAntwort = new JLabel("Richtige Antwort:");
		lblRichtigeAntwort.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRichtigeAntwort.setBounds(10, 15, 124, 14);
		updatePanel.add(lblRichtigeAntwort);
	}

	private void updateMultichoice() {

		panel.remove(updatePanel);

		updatePanel = new JPanel();
		updatePanel.setBounds(10, 275, 404, 213);
		updatePanel.setBackground(Color.WHITE);
		panel.add(updatePanel);
		updatePanel.setLayout(null);

		textField_1Antwort = new JTextField();
		textField_1Antwort.setToolTipText("1.Antwort");
		textField_1Antwort.setBounds(23, 36, 200, 20);
		updatePanel.add(textField_1Antwort);

		textField_2Antwort = new JTextField();
		textField_2Antwort.setToolTipText("2.Antwort");
		textField_2Antwort.setBounds(23, 67, 200, 20);
		updatePanel.add(textField_2Antwort);

		textField_3Antwort = new JTextField();
		textField_3Antwort.setToolTipText("3.Antwort");
		textField_3Antwort.setBounds(23, 98, 200, 20);
		updatePanel.add(textField_3Antwort);

		textField_4Antwort = new JTextField();
		textField_4Antwort.setToolTipText("4.Antwort");
		textField_4Antwort.setBounds(23, 129, 200, 20);
		updatePanel.add(textField_4Antwort);

		JLabel lblMglicheAntworten = new JLabel("M\u00F6gliche Antworten:");
		lblMglicheAntworten.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMglicheAntworten.setBounds(23, 11, 137, 14);
		updatePanel.add(lblMglicheAntworten);
	}
}