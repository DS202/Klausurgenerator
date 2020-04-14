package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;

/** Auswahlfenster zum Auswaehlen von Fragen fuer eine Klausur.
 * 
 * @author Nico
 *
 */
public class FrageAuswaehlenFenster {
	
	// *** Eigenschaften *** //

	private JFrame frame;
	private JList<String> list;
	private List<AbstractFrage> fragenList;
	private FragenTabelleFenster fenster;

	// *** Konstruktor *** //
	
	public FrageAuswaehlenFenster(FragenTabelleFenster fenster, List<AbstractFrage> fragenListe) {
		this.fragenList = fragenListe;
		this.fenster = fenster;

		initialize();

		frame.setVisible(true);
	}
	
	// *** Methoden *** //

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 875, 580);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Fragen Auswahl");
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 859, 541);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblHeader = new JLabel("<html><u>Frage Ausw\u00E4hlen</u></html>");
		lblHeader.setFont(new Font("Arial", Font.PLAIN, 15));
		lblHeader.setBounds(375, 11, 125, 22);
		panel.add(lblHeader);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 839, 430);
		panel.add(scrollPane);
		
		DefaultListModel<String> modelListe=new DefaultListModel<>();
				fuelleListeMitFragen(modelListe);

		list = new JList<>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(modelListe);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				System.out.println("Listeneintrag ausgewählt");
			}
		});
		scrollPane.setViewportView(list);

		JButton btnFrageHinzu = new JButton("Frage hinzuf\u00FCgen");
		btnFrageHinzu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Frage zu Klausur Hinzufügen");

				for (int i = 0; i < fragenList.size(); i++) {
					String tmp = list.getSelectedValue().split(">>>")[0].trim();
					
					if (tmp.equalsIgnoreCase((fragenList.get(i).getFrageText()))) {
						fenster.addElementToKlausur(fragenList.get(i));
						break;
					}
				}
			}
		});
		btnFrageHinzu.setBounds(694, 507, 155, 23);
		panel.add(btnFrageHinzu);
	}
	
	// *** Hilfsmethoden *** //
	
	/** Fuellt ein DefaultListModel mit den Werten aus der fragenliste.
	 * 
	 * @param model DefaultListModel
	 * @return DefaultListModel mit Werten (Fragen) aus der fragenliste.
	 */
	private DefaultListModel<String> fuelleListeMitFragen(DefaultListModel<String> model){
		
		for (int i = 0; i < fragenList.size(); i++) {
			model.addElement(fragenList.get(i).getFrageText() + " >>> " + fragenList.get(i).getFrageTyp() + " >>> " + fragenList.get(i).getSchwierigkeitsgrad());
		}
		
		return model;
	}
	
}