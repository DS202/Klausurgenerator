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
	protected int punkte;
	protected String seminar;

	/**
	 * Basiskonstruktor für Benutzereingaben
	 * 
	 * @param frage
	 * @param schwierigkeitsgrad
	 * @param punkte
	 * @param seminar
	 */
	AbstractFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar) {
		frageText = frage;
		this.schwierigkeitsgrad = schwierigkeitsgrad;
		this.punkte = punkte;
		this.seminar = seminar;

	}

	AbstractFrage(String[] rawFrage) {
		frageText = rawFrage[1];
		schwierigkeitsgrad = Schwierigkeitsgrad.valueOf(rawFrage[2]);
		punkte = Integer.valueOf(rawFrage[3]);
		seminar = rawFrage[4];
	}

	public String getFrageText() {
		return frageText;
	}

	public Schwierigkeitsgrad getGrad() {
		return schwierigkeitsgrad;
	}

	public int getPunkte() {
		return punkte;
	}

	public String getSeminar() {
		return seminar;
	}

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
	 * Seperator für die CSV-Dateien. Im derzeitigen Stand ist es der Tab
	 * 
	 * @return
	 */
	private static String getSeperator() {
		return "\t";
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
		if (frageTyp.equals(MultiChoiceFrage.class.getSimpleName())) {
			return new MultiChoiceFrage(rawFrage);
		}
		return null;
	}

	protected String[] antwortSplitter(String[] rawFrage, int startPunkt) {
		List<String> antwortListe = new ArrayList<String>();
		for (int position = startPunkt; position < rawFrage.length; position++) {
			antwortListe.add(rawFrage[position]);
		}

		return (String[]) antwortListe.toArray();
	}

	protected abstract Object getAntwort();

	public abstract String getFrageTyp();
}
