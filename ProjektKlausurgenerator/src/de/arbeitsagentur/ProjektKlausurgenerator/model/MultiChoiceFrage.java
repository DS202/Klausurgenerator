package de.arbeitsagentur.ProjektKlausurgenerator.model;

import java.util.ArrayList;
import java.util.List;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;

/**
 * Klasse für Multiple-Choice Fragen.
 * @author DDJ
 *
 */
public class MultiChoiceFrage extends AbstractFrage {

	private String[][] antworten;
/**
 * Basiskonstruktor für Benutzereingaben
 * @param frage
 * @param schwierigkeitsgrad
 * @param punkte
 * @param seminar
 * @param rAntwort
 * @param antworten
 */
	public MultiChoiceFrage(String frage, Schwierigkeitsgrad schwierigkeitsgrad, int punkte, String seminar, String[][] antworten) {
		super(frage, schwierigkeitsgrad, punkte, seminar);
		this.antworten = antworten;

	}
	
	public MultiChoiceFrage(String[] rawFrage) {
		super(rawFrage);
		antwortMatrix(rawFrage);
	}
	
	private void antwortMatrix(String[] rawFrage) {
		List<String[]> list= new ArrayList<String[]>();
		for(int position = 5; position< rawFrage.length;position=position+2) {
			String[] answer = {rawFrage[position], rawFrage[position+1]};
			list.add(answer);
		}
		antworten = new String[list.size()][2];
		for(int position = 0; position<antworten.length;position++) {
			antworten[position] = list.get(position);
		}
		
	}

	
	
	
	public String[][] getAntworten() {
		return antworten;
	}



	@Override
	protected Object getAntwort() {
		StringBuilder antworten = new StringBuilder();
		
		for(String[] antwort : this.antworten) {
			antworten.append("\"" + antwort[0] + "\"");
			setSeperator(antworten);
			antworten.append(antwort[1]);
		}
		
		return antworten.toString();
	}

	@Override
	public String getFrageTyp() {
		return this.getClass().getSimpleName();
	}

}