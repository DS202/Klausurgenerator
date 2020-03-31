package de.arbeitsagentur.ProjektKlausurgenerator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.DocumentException;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Klausurgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Loesungsgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.CsvCreator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.Verwalter;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.fragenExporter;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.fragenImporter;

public class Controller {
	private List<AbstractFrage> endgueltigeKlausurListeAbstractFragen;
	
	
	public boolean erstelleFreitextFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String[] schluesselwoerter) {
		if (frage.isEmpty()
				|| punkte <= 0
					|| seminar.isEmpty()
						|| schluesselwoerter.equals(null)) {
				return false;
		} else {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen(Verwalter.getCsvFile());
			fragenListe.set(fragenListe.size(), new Freitext(frage, schwierigkeitsgrad, punkte, seminar, schluesselwoerter));
			return true;
		}
	}
	
	public boolean erstelleMultiChoiceFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String rAntwort ,String[][] antworten) {
		if (frage.isEmpty()
				|| punkte <= 0
					|| seminar.isEmpty()
						|| rAntwort.isEmpty()
							|| antworten.length < 5 || antworten.length > 5) { //1stelle antwort und zweite stelle ist boolean
				return false;
		} else {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen(Verwalter.getCsvFile());
			fragenListe.set(fragenListe.size(), new MultiChoiceFrage(frage, schwierigkeitsgrad, punkte, seminar, antworten));
			return true;
		}
	}
	
	public List<AbstractFrage> getAlleFragen() {
		return fragenImporter.importFragen(Verwalter.getCsvFile()); 
	}
	
	public List<AbstractFrage> bearbeiteKlausur(File csvInput) throws Exception {	

		String dateiName = csvInput.toPath().getFileName().toString();
		
		if (dateiName.contains(".csv")) {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen(dateiName);	
			
			return fragenListe;
		} else {
			return null;
		}
	}
	
	public void erstelleKlausur(Klausur klausur) throws Exception {
		int anzahlPunkte = klausur.getPunkte();
		List<AbstractFrage> fragenList = klausur.getFragenList();
		int aktuelleGesamtpunkte = 0;
		int listPostion = 0;
		
		//Liste wird gemischt
		Collections.shuffle(fragenList);
		
		for (int i = 0; i < fragenList.size(); i++) {
			aktuelleGesamtpunkte += fragenList.get(i).getPunkte();
			
			if (aktuelleGesamtpunkte <= anzahlPunkte) {
				if (aktuelleGesamtpunkte == anzahlPunkte) {
					listPostion = i;
					i = fragenList.size();
				}if (aktuelleGesamtpunkte > anzahlPunkte) {
					i = 0;
					Collections.shuffle(fragenList);
				}
			}
		}
		
		for (int i = 0; i < listPostion; i++) {
			endgueltigeKlausurListeAbstractFragen.add(fragenList.get(i));
		}
		Klausur berechneteKlausur = new Klausur(anzahlPunkte, klausur.getKlausurName(), endgueltigeKlausurListeAbstractFragen);
		
		try {
			new Klausurgenerator().createKlausur(berechneteKlausur);
			fragenExporter.exportKlausur(anzahlPunkte, klausur.getKlausurName(), endgueltigeKlausurListeAbstractFragen);
			new Loesungsgenerator().createKlausur(berechneteKlausur);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}