package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;

/**
 * Importer der Fragen
 * 
 * @author DDJ & Nico
 *
 */
public class FragenImporter extends Verwalter {

	/**
	 * Liest eine csv-Datei und wandelt deren Inhalt in eine Liste um
	 * 
	 * @param csvDatei Namen der auszulesenden csv-Datei
	 * @return Eine Liste aus AbstaktenFragen zurück
	 */
	public static List<AbstractFrage> importFragen(String csvDatei) {
		BufferedReader br = null;
		String line = "";

		List<AbstractFrage> fragen = new ArrayList<>();

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

	/**
	 * 
	 * Einlesen einer CSV-Datei mit allen Fragen und auswerten der Daten.
	 * 
	 * @author Nico
	 * 
	 */

	/**
	 * Liest die Fragen.CSV ein und gibt eine Liste mit Abstrakte Fragen zurueck.
	 * 
	 * @return List<AbstrakteFrage>
	 * 
	 * @author Nico
	 */
	public List<AbstractFrage> csvEinlesen() {

		List<String> zeilenListe = new ArrayList<>();

		try {

//			zeilenListe = Files.readAllLines(Paths
//					.get(this.getClass().getResource("resources/dateien/fragen.csv").getPath()));

			zeilenListe = Files.readAllLines(Paths.get("resources/dateien/fragen.csv"));
		} catch (Exception e) {
			System.out.println("Fehler beim Einlesen der Datei");
			e.printStackTrace();
		}

		return fragenErstellenAusZeilenliste(zeilenListe);
	}

	/*
	 * Erstellt aus einer Liste mit Zeilen eine Liste mit Abstrakten Fragen.
	 * 
	 * @param zeilenListe Liste mit Strings.
	 * 
	 * @return Liste mit Abstrakten Fragen.
	 * 
	 * @author Nico
	 */
	private List<AbstractFrage> fragenErstellenAusZeilenliste(List<String> zeilenListe) {

		zeilenListe = removeEmptyLines(zeilenListe);

		List<AbstractFrage> fragenListe = new ArrayList<>();

		for (int i = 0; i < zeilenListe.size(); i++) {

			String[] splittArray = zeilenListe.get(i).split(";");
			AbstractFrage frage = null;

			if (zeilenListe.get(i).contains("Freitext")) {

				frage = new Freitext(splittArray[1], Enum.valueOf(Schwierigkeitsgrad.class, splittArray[2]),
						Double.parseDouble(splittArray[3]), splittArray[4], splittArray[5].split(" "));

			} else if (zeilenListe.get(i).contains("Multichoice")) {

				String[][] arr = { { splittArray[5], splittArray[6] }, { splittArray[7], splittArray[8] },
						{ splittArray[9], splittArray[10] }, { splittArray[11], splittArray[12] } };

				frage = new MultiChoiceFrage(splittArray[1], Enum.valueOf(Schwierigkeitsgrad.class, splittArray[2]),
						Double.parseDouble(splittArray[3]), splittArray[4], arr);
			}
			fragenListe.add(frage);
		}
		return fragenListe;
	}

	/**
	 * Entfernt leere Elemente aus einer Liste mit Zeilen.
	 * 
	 * @param listFragen List mit Strings (Zeilen)
	 * @return Liste mit String ohne Leere Zeilen
	 * 
	 * @author Nico
	 */
	private List<String> removeEmptyLines(List<String> listFragen) {

		List<String> result = new ArrayList<>();

		for (int i = 0; i < listFragen.size(); i++) {
			if (!listFragen.get(i).equals("")) {
				result.add(listFragen.get(i));
			}
		}
		return result;
	}

}
