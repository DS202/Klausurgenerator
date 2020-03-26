package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import com.itextpdf.text.Paragraph;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;

public class Klausurgenerator extends PDFCreator {

	@Override
	protected String getPDFName() {

		return klausur.getKlausurName()+".pdf";
	}

	@Override
	protected void addAntwortElement(Paragraph frageParagraph, AbstractFrage frage) {

		if(frage.getFrageTyp().equals(Freitext.class.getSimpleName())) {
			
		}
	}

}
