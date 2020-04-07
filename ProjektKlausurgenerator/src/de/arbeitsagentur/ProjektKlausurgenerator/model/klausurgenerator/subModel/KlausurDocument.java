package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.subModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import de.arbeitsagentur.ProjektKlausurgenerator.model.KlausurLogger;

/**
 * Erweiterte Klasse für Klausurgenerator. Selbstständige Seiteneinteilung der
 * Aufgaben
 * 
 * @author DDJ
 *
 */
public class KlausurDocument {

	private final int maxCounterLines = 35;

	private int contentCounter = 0;

	private Document document = new Document();

	/**
	 * Nimmt Klausurparagraphen an um mit desen Größe die Seiteneinteilung bestimmen
	 * 
	 * @param paragraph
	 * @throws DocumentException
	 */
	public void addParagraphs(KlausurParagraph paragraph) throws DocumentException {
		contentCounter = contentCounter + paragraph.getSize();
		KlausurLogger.getInstance().addLog("Prüfe Seitengröße");
		if (contentCounter > maxCounterLines) {
			KlausurLogger.getInstance().addLog("Setze neue Seite");
			document.newPage();
			contentCounter = paragraph.getSize();
		}
		KlausurLogger.getInstance().addLog("Füge neuen Paragraphen ein");
		document.add(paragraph.getParagraph());
	}

	/**
	 * 
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

}
