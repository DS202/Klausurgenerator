package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

public class Klausurgenerator extends PDFCreator {

	@Override
	protected String getPDFName() {
		// TODO Auto-generated method stub
		 
		//return "Test.pdf";
		//Ausgetauscht durch neues return-Statement.
		
		return klausur.getKlausurName() + ".pdf";
	}

}
