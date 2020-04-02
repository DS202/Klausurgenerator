package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.subModel.KlausurParagraph;

/**
 * KLasse um die Lösungen zu generieren. Erbt von PDFCreator
 * 
 * @author DDJ
 *
 */
public class Loesungsgenerator extends PDFCreator {

	@Override
	protected String getPDFName() {
		return "Lösung " + klausur.getKlausurName() + ".pdf";
	}

	@Override
	protected void addAntwortElement(KlausurParagraph frageParagraph, AbstractFrage frage)
			throws BadElementException, MalformedURLException, IOException {
		if (frage.getFrageTyp().equals(Freitext.class.getSimpleName())) {
			KlausurParagraph loesung = new KlausurParagraph();
			for (String schluesselwort : ((Freitext) frage).getSchluesselwoerter()) {
				loesung.addText(schluesselwort+", ");
			}
			frageParagraph.addParagraph(loesung);

		}
		if (frage.getFrageTyp().equals(MultiChoiceFrage.class.getSimpleName())) {
			for (String[] moeglicheAntwort : ((MultiChoiceFrage) frage).getAntworten()) {
				KlausurParagraph kreuzAntwort = new KlausurParagraph();
				if (Boolean.valueOf(moeglicheAntwort[1])) {
					kreuzAntwort.addText("[X]  " + moeglicheAntwort[0]);
				} else {
					kreuzAntwort.addText("[ ]  " + moeglicheAntwort[0]);
				}
				frageParagraph.addParagraph(kreuzAntwort);
			}

		}
		KlausurParagraph leer = new KlausurParagraph();
		leer.addText(" ");
		frageParagraph.addParagraph(leer);
	}
}
