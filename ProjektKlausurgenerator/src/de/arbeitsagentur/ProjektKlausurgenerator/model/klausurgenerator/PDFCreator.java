package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;

public abstract class PDFCreator {

	protected Document klausurDokument = new Document();
	protected Klausur klausur;
	protected List<AbstractFrage> fragenListe;

	protected int frageZahl = 1;

	public void createKlausur(Klausur klausur) throws FileNotFoundException, DocumentException {
		setClassVariables(klausur);
		PdfWriter.getInstance(klausurDokument, new FileOutputStream(getPDFName()));
		klausurDokument.open();
		addMetaDaten();
		addTitleBlatt();
		addInhalt();
		klausurDokument.close();
	}

	protected void setClassVariables(Klausur klausur) {
		this.klausur = klausur;
		this.fragenListe = klausur.getFragenList();
	}

	protected abstract String getPDFName();

	private void addInhalt() {
		int frageIndex = 1;
		for (AbstractFrage frage : fragenListe) {
			Paragraph frageParagraph = new Paragraph();
			frageParagraph.add(new Paragraph(frageIndex + ") " + frage.getFrageText()));
			addPunkte(frageParagraph, frage.getPunkte());
			addAntwortElement(frageParagraph, frage);
		}
	}

	protected abstract void addAntwortElement(Paragraph frageParagraph, AbstractFrage frage);

	private void addPunkte(Paragraph frageParagraph, int punkte) {
		Paragraph punkteParagraph = new Paragraph("(   /"+punkte+")");
		punkteParagraph.setAlignment(Element.ALIGN_LEFT);
		frageParagraph.add(punkteParagraph);
		
	}

	private void addTitleBlatt() throws DocumentException {

		setTitel();

		setEintragsZeile();

		setLeerZeilen(26 - 2 * getDurchgeange());

		setPunkteTabelle();

	}

	private void setPunkteTabelle() throws DocumentException {
		List<List<AbstractFrage>> subLists = getSubLists();

		for (List<AbstractFrage> list : subLists) {
			setTeilTabelle(list);
		}
		setLeerZeilen(1);

		PdfPTable notenBildung = new PdfPTable(2);
		notenBildung.addCell("Erreichte Punkte gesamt:");
		notenBildung.addCell(" ");
		notenBildung.addCell("Note:");
		notenBildung.addCell(" ");

		klausurDokument.add(notenBildung);

	}

	private void setTeilTabelle(List<AbstractFrage> list) throws DocumentException {

		PdfPTable punkteTable = new PdfPTable(list.size() + 1);

		PdfPCell c1 = new PdfPCell(new Phrase("Frage:"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		punkteTable.addCell(c1);

		for (int frageIndex = 1; frageIndex <= list.size(); frageIndex++) {
			c1 = new PdfPCell(new Phrase(Integer.toString(frageZahl)));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			punkteTable.addCell(c1);
			frageZahl++;
		}
		punkteTable.setHeaderRows(1);

		punkteTable.addCell("Erreichbar");
		for (AbstractFrage frage : list) {
			PdfPCell cell = new PdfPCell(new Phrase(Integer.toString(frage.getPunkte())));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			punkteTable.addCell(cell);
		}

		punkteTable.addCell("Erreicht");
		for (int frageIndex = 1; frageIndex <= list.size(); frageIndex++) {
			punkteTable.addCell(" ");
		}

		klausurDokument.add(punkteTable);
	}

	private List<List<AbstractFrage>> getSubLists() {
		int listenGroesse = fragenListe.size();
		int position = 0;

		List<List<AbstractFrage>> subLists = new ArrayList<List<AbstractFrage>>();

		for (int durchgaenge = getDurchgeange(); durchgaenge > 0; durchgaenge--) {
			List<AbstractFrage> list = new ArrayList<AbstractFrage>();
			for (int zaehler = 0; zaehler < 6; zaehler++) {
				if (position < listenGroesse) {
					list.add(fragenListe.get(position));
					position++;
				}
			}
			subLists.add(list);
		}

		return subLists;
	}

	private void setEintragsZeile() throws DocumentException {
		Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16);

		Paragraph eintrag = new Paragraph(
				"\nName: ______________                                     Gruppe: ______\nDatum: _____________",
				font);

		klausurDokument.add(eintrag);
	}

	private int getDurchgeange() {
		int listenGroesse = fragenListe.size();
		int mod5 = listenGroesse % 6;
		if (mod5 != 0) {
			listenGroesse = listenGroesse + (6 - mod5);
		}
		return listenGroesse / 6;
	}

	private void setLeerZeilen(int zeilen) throws DocumentException {
		for (int i = 0; i <= zeilen; i++) {
			klausurDokument.add(new Paragraph(" "));
		}
	}

	private void setTitel() throws DocumentException {
		Font titelFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);

		Paragraph title = new Paragraph("Klausur für Seminar:\n" + klausur.getKlausurName(), titelFont);
		title.setAlignment(Element.ALIGN_CENTER);
		klausurDokument.add(title);

	}

	private void addMetaDaten() {
		klausurDokument.addTitle("Klausur zum Seminar: " + klausur.getKlausurName());
		klausurDokument.addAuthor(System.getProperty("user.name"));

	}

}
