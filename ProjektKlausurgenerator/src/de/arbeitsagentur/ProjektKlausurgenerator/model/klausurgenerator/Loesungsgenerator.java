package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Paragraph;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;

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
	protected void addAntwortElement(Paragraph frageParagraph, AbstractFrage frage)
			throws BadElementException, MalformedURLException, IOException {
		if (frage.getFrageTyp().equals(Freitext.class.getSimpleName())) {
			Paragraph loesung = new Paragraph();
			for (String schluesselwort : ((Freitext) frage).getSchluesselwoerter()) {
				loesung.add(schluesselwort);
				loesung.add(", ");
			}
			frageParagraph.add(loesung);

		}
		if (frage.getFrageTyp().equals(MultiChoiceFrage.class.getSimpleName())) {
			for (String moeglicheAntwort : ((MultiChoiceFrage) frage).getMoeglichAntworten()) {
				if (moeglicheAntwort.equals(((MultiChoiceFrage) frage).getRichtigeAntwort())) {
					frageParagraph.add(new Paragraph("[X]  " + moeglicheAntwort));
				} else {
					frageParagraph.add(new Paragraph("[  ]  " + moeglicheAntwort));
				}
			}

		}
		frageParagraph.add(new Paragraph(" "));
	}
}
