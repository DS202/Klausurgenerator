package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.subModel;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

/**
 * Erweiterte Klasse von Paragraph. Enthält Zähler für Zeilengröße des
 * Pragraphens
 * 
 * @author DDJ
 *
 */
public class KlausurParagraph {

	private final int maxCounterChar = 78;
	private int size = 0;

	private Paragraph paragraph;

	/**
	 * Konstruktor mit Textannahme
	 * 
	 * @param string
	 */
	public KlausurParagraph(String string) {
		zeilenTrenner(string);
		paragraph = new Paragraph(string);
	}

	/**
	 * Standartkonstructor
	 */
	public KlausurParagraph() {
		paragraph = new Paragraph();
	}

	/**
	 * Fügt eine also Bilddatei gespeicherte Linie in den Paragraphen hinzu
	 * 
	 * @throws BadElementException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void addLine() throws BadElementException, MalformedURLException, IOException {
		size = size + 2;
		Image linie = Image.getInstance(this.getClass().getClassLoader().getResource("images/schreibLinie.png"));
		paragraph.add(linie);
	}

	/**
	 * Nimmt einen Klausurparagraphen an. Fügt dessen Größe den eigenen hinzu
	 * 
	 * @param paragraph
	 */
	public void addParagraph(KlausurParagraph paragraph) {

		size = size + paragraph.getSize();
		this.paragraph.add(paragraph.getParagraph());
	}

	/**
	 * Nimmt Text an und bestimmt dessen Größe
	 * 
	 * @param text
	 */
	public void addText(String text) {

		zeilenTrenner(text);
		paragraph.add(text);
	}

	private void zeilenTrenner(String text) {
		if (text.contains("/n")) {
			String[] splitText = text.split("/n");
			for (String subString : splitText) {
				countLines(subString);
			}
		} else {
			countLines(text);
		}
	}

	private void countLines(String text) {
		int length = text.length();
		int rest = text.length() % maxCounterChar;
		if (rest != 0) {
			length = length + (maxCounterChar - rest);
		}
		size = size + length / maxCounterChar;

	}

	/**
	 * Gibt Zeilengröße des Paragraphens zurück
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	public Paragraph getParagraph() {
		return paragraph;
	}

}
