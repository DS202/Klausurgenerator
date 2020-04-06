package de.arbeitsagentur.ProjektKlausurgenerator.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

import com.sun.org.apache.xpath.internal.operations.And;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Klausurgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Loesungsgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.Verwalter;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.fragenExporter;
import de.arbeitsagentur.ProjektKlausurgenerator.model.csvVerwaltung.fragenImporter;

public class Controller {
	private List<AbstractFrage> endgueltigeKlausurListeAbstractFragen = new ArrayList<AbstractFrage>();
	
	/**
	 * Erstellt eine Freitextfrage, welche in die Liste der bereits vorhanden hinzugefügt wird.
	 * 
	 * @param frage 				Fragetext als String
	 * @param schwierigkeitsgrad   	Eines von drei enums, welches die Schwierigkeit einer Frage angibt
	 * @param punkte   				Punkt die in einer Frage erreicht werden können in int
	 * @param seminar  				String der angibt, aus welchem Seminar bzw. für welches Seminar die Frage gehört
	 * @param schluesselwoerter   	StringArray, welches Antworten bzw. Antwortmöglichkeiten zu einer Frage beinhaltet
	 * @return 						Nichts wiedergegeben
	 */
	public void erstelleFreitextFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String[] schluesselwoerter) {
		if (frage.isEmpty()
				|| punkte <= 0
					|| seminar.isEmpty()
						|| schluesselwoerter.equals(null)) {
			JOptionPane.showMessageDialog(null, "Die Parameter für eine neue Freitextfrage sind unvollständig!", "Erstellvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
		} else {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen(Verwalter.getCsvFile());
			fragenListe.add(new Freitext(frage, schwierigkeitsgrad, punkte, seminar, schluesselwoerter));
			fragenExporter.exportFragen(fragenListe);
		}
	}
	
	/**
	 * Erstellt eine Multiplechoicefrage, welche in die Liste der bereits vorhanden hinzugefügt wird.
	 * 
	 * @param frage 				Fragetext als String
	 * @param schwierigkeitsgrad   	Eines von drei enums, welches die Schwierigkeit einer Frage angibt
	 * @param punkte   				Punkt die in einer Frage erreicht werden können in int
	 * @param seminar  				String der angibt, aus welchem Seminar bzw. für welches Seminar die Frage gehört
	 * @param schluesselwoerter   	StringArray, welches Antworten bzw. Antwortmöglichkeiten zu einer Frage beinhaltet und als zweiten Wert den passenden boolean zur Antwort auf die Frage enthält
	 * @return 						Nichts wiedergegeben
	 */
	public void erstelleMultiChoiceFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String[][] antworten) {
		if (frage.isEmpty()
				|| punkte <= 0
					|| seminar.isEmpty()
						|| enthaeltNurFalscheAntwort(antworten)
							|| antworten.length < 4 || antworten.length > 4
							) {
			JOptionPane.showMessageDialog(null, "Die Parameter für eine neue Multiplechoicefrage sind unvollständig!", "Erstellvorgang abgebrochen", JOptionPane.ERROR_MESSAGE);
		} else {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen(Verwalter.getCsvFile());
			fragenListe.add(new MultiChoiceFrage(frage, schwierigkeitsgrad, punkte, seminar, antworten));
			fragenExporter.exportFragen(fragenListe);
		}
	}
	
	 private boolean enthaeltNurFalscheAntwort(String[][] antworten) {
		for (int i = 0; i < antworten.length; i++) {
			if (antworten[i][1].equals("true")) {
				return false;
			}
		}
		return true;
	 }
	
	/**
	 * Gibt alle bereits vorhandene Fragen wieder
	 * 
	 * @return	Gibt eine Liste aller vorhandenen Fragen wieder
	 */
	public List<AbstractFrage> getAlleFragen() {
		return fragenImporter.importFragen(Verwalter.getCsvFile()); 
	}
	
	/**
	 * Liest aus einer bereits vorhandenen Klausur .csv-Datei alle Fragen aus und gibt diese als Liste zurück
	 * 
	 * @param csvInput	vorhandene .csv-Datei
	 * @return 			eine Liste mit aller in der .csv-Datei enthaltenden Fragen
	 */
	public List<AbstractFrage> bearbeiteKlausur(File csvInput) throws Exception {	

		String dateiName = csvInput.toPath().getFileName().toString();
		
		if (dateiName.contains(".csv")) {
			List<AbstractFrage> fragenListe = fragenImporter.importFragen(dateiName);	
			
			return fragenListe;
		} else {
			return null;
		}
	}
	
	/**
	 * Erstellt eine Klausur (PDF- und csv-Datei) und eine LösungsPDF-Datei aus den bereits vorhandenen Fragen mit der gewünschten Punktezahl.
	 * 
	 * @param klausur	Ein Klausurobjekt, welches alle Werte befüllt hat
	 * @return			Nichts wiedergegeben	
	 */
	public void erstelleKlausur(Klausur klausur) {
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
				}
			}else if (aktuelleGesamtpunkte > anzahlPunkte) {
				i = 0;
				aktuelleGesamtpunkte = 0;
				Collections.shuffle(fragenList);
			}
		}
		
		for (int i = 0; i < listPostion; i++) {
			endgueltigeKlausurListeAbstractFragen.add(fragenList.get(i));
		}
		Klausur berechneteKlausur = new Klausur(anzahlPunkte, klausur.getKlausurName(), endgueltigeKlausurListeAbstractFragen);
		
			new Klausurgenerator().createKlausur(berechneteKlausur);
			fragenExporter.exportKlausur(anzahlPunkte, klausur.getKlausurName(), endgueltigeKlausurListeAbstractFragen);
			new Loesungsgenerator().createKlausur(berechneteKlausur);
	}	
}