package de.arbeitsagentur.ProjektKlausurgenerator;

import java.io.FileNotFoundException;

import com.itextpdf.text.DocumentException;

import test.testPDF;

public class Placeholder {

	public static void main(String[] args) {
		System.out.println("Placeholder");
		
		try {
			new testPDF().makePDF();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
