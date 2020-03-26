package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;

public class Klausurgenerator extends PDFCreator {

	@Override
	protected String getPDFName() {
		return klausur.getKlausurName() + ".pdf";
	}

	@Override
	protected void addAntwortElement(Paragraph frageParagraph, AbstractFrage frage)
			throws BadElementException, MalformedURLException, IOException {

		if (frage.getFrageTyp().equals(Freitext.class.getSimpleName())) {
			for (int i = 1; i <= 3; i++) {
				Image linie = Image
						.getInstance(this.getClass().getClassLoader().getResource("images/schreibLinie.png"));
				frageParagraph.add(linie);

			}

		}
		if (frage.getFrageTyp().equals(MultiChoiceFrage.class.getSimpleName())) {
			for (String moeglicheAntwort : ((MultiChoiceFrage) frage).getMoeglichAntworten()) {
				frageParagraph.add(new Paragraph("[ ]  " + moeglicheAntwort));
			}
		}

	}

}
