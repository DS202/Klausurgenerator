package de.arbeitsagentur.ProjektKlausurgenerator.controller;

import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.FragenExporter;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.FragenImporter;

/**
 * Allgemeine Logik Funktionen.
 * 
 * @author Nico
 *
 */
public class Controller {

	/**
	 * Schreibt eine Frage als Text in die Fragen.CSV.
	 * 
	 * @param fragenText Frage als String.
	 */
	public void schreibeFrageInCSV(String fragenText) {

		FragenExporter.schreibeFrageInCSV(fragenText);
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

}