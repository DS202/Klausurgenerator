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

}
