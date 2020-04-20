package de.arbeitsagentur.ProjektKlausurgenerator.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.FragenExporter;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.FragenImporter;

/**
 * Allgemeine Logik Funktionen.
 * 
 * @author Nico
 *
 */
public class Controller {

	// *** Eigenschaften *** //

	// *** Methoden *** //

	/**
	 * Schreibt eine Frage als Text in die Fragen.CSV.
	 * 
	 * @param fragenText Frage als String.
	 */
	public void schreibeFrageInCSV(String fragenText) {

		// Leerer String als Parameter um in die Fragen.CSV Datei zu schreiben
		// (Standarddatei).
		FragenExporter.schreibeFrageInCSV("", fragenText);
	}

	/**
	 * Gibt eine Liste aller Fragen zurueck.
	 * 
	 * @return Liste mit Abstrakten Fragen aus Fragen.csv
	 */
	public List<AbstractFrage> getAlleFragenAusFragenCSV() {

		FragenImporter fragenimporter = new FragenImporter();

		return fragenimporter.csvEinlesen();
	}

	/**
	 * Exportiert eine Klasur als CSV-Datei.
	 * 
	 * @param absoluterPfad ohne Dateityp
	 * @param dateiname
	 * @param klausurListe  Liste<AbstrakeFrage> mit Klausurfrage.
	 */
	public void exportKlausurAlsCSV(String absoluterPfad, String dateiname, List<AbstractFrage> klausurListe) {
//		absoluterPfad = absoluterPfad.split(".")[0];
		File file = new File(absoluterPfad + ".CSV");

		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("Fehler beim Erzeugen einer neuen Textdatei.");
			e.printStackTrace();
		}

		for (int i = 0; i < klausurListe.size(); i++) {

			if (klausurListe.get(i).getFrageTyp().equalsIgnoreCase("Freitext")) {

				Freitext tmpfrage = (Freitext) klausurListe.get(i);

				String zeile = "Freitext;" + tmpfrage.getFrageText() + ";" + tmpfrage.getSchwierigkeitsgrad().name()
						+ ";" + tmpfrage.getPunkte() + ";" + tmpfrage.getSeminar() + ";"
						+ tmpfrage.schluesselWoerterZuString();

				FragenExporter.schreibeFrageInCSV(absoluterPfad + ".CSV", zeile);
			} else {

				MultiChoiceFrage tmpfrage = (MultiChoiceFrage) klausurListe.get(i);

				String zeile = "Multichoice;" + tmpfrage.getFrageText() + ";" + tmpfrage.getSchwierigkeitsgrad().name()
						+ ";" + tmpfrage.getPunkte() + ";" + tmpfrage.getSeminar() + ";"
						+ tmpfrage.gibAlleAntwortenAlsStringZurueck();

				FragenExporter.schreibeFrageInCSV(absoluterPfad + ".CSV", zeile);
			}
		}
	}

}