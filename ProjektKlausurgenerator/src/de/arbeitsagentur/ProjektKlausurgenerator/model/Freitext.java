package de.arbeitsagentur.ProjektKlausurgenerator.model;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;

/**
 * KLasse für Freitextfragen
 * 
 * @author DDJ & Nico
 *
 */
public class Freitext extends AbstractFrage {
	private String[] schluesselwoerter;

	/**
	 * Basiskonstruktor für Benutzereingaben
	 * 
	 * @param frage
	 * @param schwierigkeitsgrad
	 * @param punkte
	 * @param seminar
	 * @param schluesselwoerter
	 */
	public Freitext(String frage, Schwierigkeitsgrad schwierigkeitsgrad, Double punkte, String seminar,
			String[] schluesselwoerter) {
		super(frage, schwierigkeitsgrad, punkte, seminar);
		this.schluesselwoerter = schluesselwoerter;
	}

	public Freitext(String[] rawFrage) {
		super(rawFrage);
		schluesselwoerter = antwortSplitter(rawFrage, 5);
	}

	/**
	 * 
	 * @return
	 */
	public String[] getSchluesselwoerter() {
		return schluesselwoerter;
	}

	/** Gibt das String[] Schluesselwoerter als String zurueck.
	 * 
	 * @return String aus Schluesselwoerter[].
	 */
	public String schluesselWoerterZuString() {
		String result = "";

		for (int i = 0; i < schluesselwoerter.length; i++) {
			result += schluesselwoerter[i];
		}

		return result;
	}

	@Override
	protected Object getAntwort() {
		StringBuilder antwort = new StringBuilder();

		for (String wort : schluesselwoerter) {
			antwort.append(wort);
			setSeperator(antwort);
		}

		return antwort.toString();
	}

	@Override
	public String getFrageTyp() {

		return this.getClass().getSimpleName();
	}

}
