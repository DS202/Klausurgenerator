package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.KlausurLogger;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.subModel.KlausurParagraph;

/**
 * KLasse um die KLausuren zu generieren. Erbt von PDFCreator
 * 
 * @author DDJ
 *
 */
public class Klausurgenerator extends PDFCreator {

	@Override
	protected String getPDFName() {

		return klausur.getKlausurName() + ".pdf";

	}

	@Override
	protected void addAntwortElement(KlausurParagraph frageParagraph, AbstractFrage frage)
			throws BadElementException, MalformedURLException, IOException {

		if (frage.getFrageTyp().equals(Freitext.class.getSimpleName())) {
			for (int i = 1; i <= 3; i++) {
				KlausurLogger.getInstance().addLog("Setzte Linien");
				frageParagraph.addLine();

			}

		}

		if (frage.getFrageTyp().equals(MultiChoiceFrage.class.getSimpleName())) {
			for (String[] moeglicheAntwort : ((MultiChoiceFrage) frage).getAntworten()) {
				KlausurLogger.getInstance().addLog("Setze Antwort");
				KlausurParagraph antowrtmoeglichkeit = new KlausurParagraph("[  ]  " + moeglicheAntwort[0]);
				frageParagraph.addParagraph(antowrtmoeglichkeit);
			}
			frageParagraph.addParagraph(new KlausurParagraph(" "));
		}

	}

}
