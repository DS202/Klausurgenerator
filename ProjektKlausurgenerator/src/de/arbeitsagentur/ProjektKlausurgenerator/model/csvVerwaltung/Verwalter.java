package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

public class Verwalter {

	/**
	 * Standartname der csv.Datei, die alle Fragen beinhaltet
	 */
	protected static String csvFile = "fragen.csv";

	public Verwalter() {
		super();
	}

	/**
	 * Getter, um den Namen der csv-Datei zu erhalten, welche alle Fragen Standartm‰ﬂig enth‰lt
	 */
	public static String getCsvFile() {
		return csvFile;
	}
	
}