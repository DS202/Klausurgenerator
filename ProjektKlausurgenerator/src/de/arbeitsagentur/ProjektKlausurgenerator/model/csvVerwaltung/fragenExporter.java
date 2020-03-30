package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

import java.io.FileWriter;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;

/**
 * Exportieren der Fragen zum CSV-Datei
 * 
 * @author DDJ
 *
 */
public class fragenExporter extends Verwalter {

	public static void exportFragen(List<AbstractFrage> fragen) throws Exception {
		FileWriter writer = new FileWriter(csvFile);

		for (AbstractFrage frage : fragen) {
			writer.append(frage.toString());
			writer.append(System.getProperty("line.separator"));
		}

		writer.flush();
		writer.close();

	}
	
	public static boolean exportKlausur(int anzahlPunkte, String klausurName, List<AbstractFrage> fragenListe) throws Exception {
		if (klausurName.isEmpty()) {
			klausurName = "neueKlausur";
		} else if(anzahlPunkte <= 0
					|| fragenListe.isEmpty()) {
			return false;
		}
		
		FileWriter writer = new FileWriter(klausurName + ".csv");
		
		for (AbstractFrage frage : fragenListe) {
			writer.append(frage.toString());
			writer.append(System.getProperty("line.separator"));
		}

		writer.flush();
		writer.close();
		return true;
	}

}
