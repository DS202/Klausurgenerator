package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;

public abstract class PDFCreator {

	protected Document klausurDokument = new Document();
	protected List<AbstractFrage> klausurfragen;
	
	public PDFCreator(List<AbstractFrage> klausurfragen) {
		this.klausurfragen = klausurfragen;
	}
	
	
	public void createKlausur(Klausur klausur) throws FileNotFoundException, DocumentException{
		PdfWriter.getInstance(klausurDokument, new FileOutputStream(getPDFName()));
		klausurDokument.open();
		addMetaDaten();
		addTitleBlatt();
		addInhalt();
	}


	protected abstract String getPDFName();


	private void addInhalt() {
		int frageIndex = 1;
		
	}


	private void addTitleBlatt() {
		int frageZahl = klausurfragen.size();
		
		
		
		
	}


	private void addMetaDaten() {
		klausurDokument.addTitle("Klausur zum Seminar: "+ klausurfragen.get(0).getSeminar());
		klausurDokument.addAuthor(System.getProperty("user.name"));
		
	}
	
}
