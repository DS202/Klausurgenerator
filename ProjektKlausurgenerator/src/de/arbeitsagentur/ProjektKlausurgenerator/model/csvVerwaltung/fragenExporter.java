package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;

/**
 * Exportieren der Fragen zum CSV-Datei
 * 
 * @author DDJ
 *
 */
public class fragenExporter extends Verwalter {
	
	/**
	 * Erstellt aus einer Liste von AbstraktenFragen eine csv-Datei
	 * 
	 * @param frage	Eine Liste von AbstraktenFragen, welche als csv-Datei gespeichert werden soll
	 * @return 		Nichts wiedergegeben
	 */
	public static void exportFragen(List<AbstractFrage> fragen) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(csvFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim erstellen der Datei " + csvFile + " aufgetreten!", "Schreibvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		for (AbstractFrage frage : fragen) {
			try {
				writer.append(frage.toString());
				writer.append(System.getProperty("line.separator"));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Fehler beim beschreiben der Datei " + csvFile + " mit dem Wert:\n" + frage.toString() + "\nist aufgetreten!", "Schreibvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}	
		}
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim speichern der Datei " + csvFile + " ist aufgetreten!", "Schreibvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static void exportKlausur(int anzahlPunkte, String klausurName, List<AbstractFrage> fragenListe) {
		if (klausurName.isEmpty()) {
			klausurName = "neueKlausur";
		} else if(anzahlPunkte <= 0
					|| fragenListe.isEmpty()) {
		}
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(klausurName + ".csv");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim erstellen der Datei " + klausurName + ".csv aufgetreten!", "Schreibvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		for (AbstractFrage frage : fragenListe) {
			try {
				writer.append(frage.toString());
				writer.append(System.getProperty("line.separator"));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Fehler beim beschreiben der Datei " + klausurName + ".csv mit dem Wert:\n" + frage.toString() + "\nist aufgetreten!", "Schreibvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fehler beim speichern der Datei " + klausurName + ".csv ist aufgetreten!", "Schreibvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}

}
