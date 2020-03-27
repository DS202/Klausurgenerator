package de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;

public class CsvCreator {
	private FileWriter newCsvDatei;
	private static final char DEFAULT__SEPARATOR = ';';
	
	public boolean createCsvDatei(int anzahlPunkte, String klausurname, List<AbstractFrage> fragenListe) {
		if (klausurname.isEmpty()) {
			klausurname = "neueKlausur";
		} else if(anzahlPunkte <= 0
					|| fragenListe.isEmpty()) {
			return false;
		}
		
		try {
			newCsvDatei = new FileWriter(klausurname + ".csv");
			
			for (AbstractFrage abstractFrage : fragenListe) {
				if (abstractFrage instanceof Freitext) {
					addFreiTextCsv((Freitext) abstractFrage);
				} else if (abstractFrage instanceof MultiChoiceFrage) {
					addMultiChoiceFrageCsv((MultiChoiceFrage) abstractFrage);
				}
			}
			newCsvDatei.flush();
			newCsvDatei.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private void addFreiTextCsv(Freitext freiText) throws IOException {
		newCsvDatei.append(freiText.getFrageTyp());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(freiText.getFrageText());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(freiText.getSchwierigkeitsgrad().toString());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(Integer.toString(freiText.getPunkte()));
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(freiText.getSeminar());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		
		for (String schluesselwoerter : freiText.getSchluesselwoerter()) {
			newCsvDatei.append(schluesselwoerter);
			newCsvDatei.append(DEFAULT__SEPARATOR);
		}
		
		newCsvDatei.append("\n");
	}
	
	private void addMultiChoiceFrageCsv(MultiChoiceFrage multiChoiceFrage) throws IOException {
		newCsvDatei.append(multiChoiceFrage.getFrageTyp());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(multiChoiceFrage.getFrageText());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(multiChoiceFrage.getSchwierigkeitsgrad().toString());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(Integer.toString(multiChoiceFrage.getPunkte()));
		newCsvDatei.append(DEFAULT__SEPARATOR);
		newCsvDatei.append(multiChoiceFrage.getSeminar());
		newCsvDatei.append(DEFAULT__SEPARATOR);	
		
		for (String moeglicheAntworten : multiChoiceFrage.getAntworten()) {
			newCsvDatei.append(moeglicheAntworten);
			newCsvDatei.append(DEFAULT__SEPARATOR);
		}
		
		newCsvDatei.append(multiChoiceFrage.getrAntwort());
		newCsvDatei.append(DEFAULT__SEPARATOR);
		
		newCsvDatei.append("\n");
	}
	
}
