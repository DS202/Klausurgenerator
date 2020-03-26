package de.arbeitsagentur.ProjektKlausurgenerator.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.DocumentException;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.CsvCreator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Klausurgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.fragenImporter;

public class Controller {
	private List<AbstractFrage> endgueltigeKlausurListeAbstractFrages;
	
	
	public boolean erstelleFreitextFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String[] schluesselwoerter) {
		if (frage.isEmpty()
				|| punkte <= 0
					|| seminar.isEmpty()
						|| schluesselwoerter.equals(null)) {
				return false;
		} else {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen();
			fragenListe.set(fragenListe.size(), new Freitext(frage, schwierigkeitsgrad, punkte, seminar, schluesselwoerter));
			return true;
		}
	}
	
	public boolean erstelleMultiChoiceFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String rAntwort ,String[] antworten) {
		if (frage.isEmpty()
				|| punkte <= 0
					|| seminar.isEmpty()
						|| rAntwort.isEmpty()
							|| antworten.length < 5) {
				return false;
		} else {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen();
			fragenListe.set(fragenListe.size(), new MultiChoiceFrage(frage, schwierigkeitsgrad, punkte, seminar, rAntwort, antworten));
			return true;
		}
	}
	
	public List<AbstractFrage> getAlleFragen() {
		return fragenImporter.importFragen(); 
	}
	
	public List<AbstractFrage> bearbeiteKlausur(File csvInput) {
		
		
		
		//TODO
		//1. Validation if File is compatible
		//2. Convert File in List
		
		return null;
	}
	
	private void erstelleKlausur(Klausur klausur) {
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
			endgueltigeKlausurListeAbstractFrages.add(fragenList.get(i));
		}
		
		try {
			new Klausurgenerator().createKlausur(new Klausur(anzahlPunkte, klausur.getKlausurName(), endgueltigeKlausurListeAbstractFrages));
			new CsvCreator().createCsvDatei(anzahlPunkte, klausur.getKlausurName(), endgueltigeKlausurListeAbstractFrages);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
}