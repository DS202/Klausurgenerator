package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
/**
 * Importer der Fragen
 * @author DDJ
 *
 */
public class fragenImporter extends Verwalter {
	
	public static List<AbstractFrage> importFragen() {
		BufferedReader br = null;
		String line = "";
		
		List<AbstractFrage> fragen = new ArrayList<AbstractFrage>();

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				AbstractFrage frage = AbstractFrage.getFrage(line);
				fragen.add(frage);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fragen;

	}
}
