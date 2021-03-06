package de.arbeitsagentur.ProjektKlausurgenerator.model;

import java.util.ArrayList;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;

/**
 * Abstracte Klasse aller Fragen
 * 
 * @author DDJ
 *
 */
public abstract class AbstractFrage {
	protected String frageText;
	protected Schwierigkeitsgrad schwierigkeitsgrad;
	protected Double punkte;
	protected String seminar;

	/**
	 * Basiskonstruktor f�r Benutzereingaben
	 * 
	 * @param frage
	 * @param schwierigkeitsgrad
	 * @param punkte
	 * @param seminar
	 */
	AbstractFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, Double punkte, String seminar) {
		frageText = frage;
		this.schwierigkeitsgrad = schwierigkeitsgrad;
		this.punkte = punkte;
		this.seminar = seminar;

	}

	AbstractFrage(String[] rawFrage) {
		frageText = rawFrage[1];
		schwierigkeitsgrad = Schwierigkeitsgrad.valueOf(rawFrage[2]);
		punkte = Double.valueOf(rawFrage[3]);
		seminar = rawFrage[4];
	}

	public String getFrageText() {
		return frageText;
	}

	public Double getPunkte() {
		return punkte;
	}

	public String getSeminar() {
		return seminar;
	}

	public Schwierigkeitsgrad getSchwierigkeitsgrad() {
		return schwierigkeitsgrad;
	}

	@Override
	public String toString() {
		StringBuilder frageString = new StringBuilder(getFrageTyp());
		setSeperator(frageString);

		frageString.append(frageText);
		setSeperator(frageString);

		frageString.append(schwierigkeitsgrad.toString());
		setSeperator(frageString);

		frageString.append(punkte);
		setSeperator(frageString);

		frageString.append(seminar);
		setSeperator(frageString);

		frageString.append(getAntwort());

		return frageString.toString();
	}

	protected void setSeperator(StringBuilder frageString) {
		frageString.append(getSeperator());
	}

	/**
	 * Seperator f�r die CSV-Dateien. Im derzeitigen Stand ist es der Tab
	 * 
	 * @return
	 */
	private static String getSeperator() {
		return ";";
	}

	/**
	 * Zum Bestimmen der Frageart beim Auslesen von CSV-Dateien
	 * 
	 * @param line
	 * @return
	 */
	public static AbstractFrage getFrage(String line) {
		String[] rawFrage = line.split(getSeperator());
		String frageTyp = rawFrage[0];
		if (frageTyp.equals(Freitext.class.getSimpleName())) {
			return new Freitext(rawFrage);
		}
//		if (frageTyp.equals(MultiChoiceFrage.class.getSimpleName())) {
		return new MultiChoiceFrage(rawFrage);
//		}
	}

	protected String entfernerApostroph(String strStream) {
		strStream = strStream.replaceAll("\"", "");
		return strStream;
	}

	protected String[] antwortSplitter(String[] rawFrage, int startPunkt) {
		List<String> antwortListe = new ArrayList<String>();
		for (int position = startPunkt; position < rawFrage.length; position++) {
			antwortListe.add(rawFrage[position]);
		}
		String[] returnAnswer = new String[antwortListe.size()];

		for (int i = 0; i < antwortListe.size(); i++) {
			returnAnswer[i] = antwortListe.get(i);
		}
		return returnAnswer;
	}

	protected abstract Object getAntwort();

	public abstract String getFrageTyp();
}
