package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

import java.io.BufferedReader;
import java.io.File;
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
	
	/**
	 * Liest eine csv-Datei und wandelt deren Inhalt in eine Liste um
	 * 
	 * @param csvDatei	Namen der auszulesenden csv-Datei
	 * @return 			Eine Liste aus AbstaktenFragen zurück
	 */
	public static List<AbstractFrage> importFragen(String csvDatei) {
		BufferedReader br = null;
		String line = "";
		
		List<AbstractFrage> fragen = new ArrayList<AbstractFrage>();

		try {
			File csvPfad = new File(csvDatei);
			
			if (csvDatei.equals(csvFile) && csvPfad.exists() || csvPfad.exists()) {
					br = new BufferedReader(new FileReader(csvFile));
					
					while ((line = br.readLine()) != null) {
						AbstractFrage frage = AbstractFrage.getFrage(line);
						fragen.add(frage);
					}
			} else {
				csvPfad.createNewFile();
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
