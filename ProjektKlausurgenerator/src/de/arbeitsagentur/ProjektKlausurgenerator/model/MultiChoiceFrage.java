package de.arbeitsagentur.ProjektKlausurgenerator.model;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;

/**
 * Klasse für Multiple-Choice Fragen
 * @author DDJ
 *
 */
public class MultiChoiceFrage extends AbstractFrage {
	
	private String[] antworten;
	private String rAntwort;
/**
 * Basiskonstruktor für Benutzereingaben
 * @param frage
 * @param schwierigkeitsgrad
 * @param punkte
 * @param seminar
 * @param rAntwort
 * @param antworten
 */
	MultiChoiceFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String rAntwort ,String[] antworten) {
		super(frage, schwierigkeitsgrad, punkte, seminar);
		this.antworten = antworten;
		this.rAntwort = rAntwort;
	}
	
	MultiChoiceFrage(String[] rawFrage) {
		super(rawFrage);
		rAntwort = rawFrage[5];
		antworten = antwortSplitter(rawFrage, 6);
	}


	@Override
	protected Object getAntwort() {
		StringBuilder antworten = new StringBuilder(rAntwort);
		setSeperator(antworten);
		
		for(String antwort : this.antworten) {
			antworten.append(antwort);
			setSeperator(antworten);
		}
		
		return antworten.toString();
	}

	@Override
	protected String getFrageTyp() {
		return this.getClass().getSimpleName();
	}

}
