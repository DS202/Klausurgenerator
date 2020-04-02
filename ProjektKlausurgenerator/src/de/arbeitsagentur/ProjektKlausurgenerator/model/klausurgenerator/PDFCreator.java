package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BadElementException;
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
import de.arbeitsagentur.ProjektKlausurgenerator.model.KlausurLogger;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.subModel.KlausurDocument;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.subModel.KlausurParagraph;

/**
 * Abstracte Klasse um die Klausuren bzw. Lösungen zu erstellen
 * 
 * @author DDJ
 *
 */
public abstract class PDFCreator {

	protected KlausurDocument klausurDokument = new KlausurDocument();
	protected Klausur klausur;
	protected List<AbstractFrage> fragenListe;
	protected PdfWriter writer;
	protected FootEvent footEvent = new FootEvent();

	protected int frageZahl = 1;

	public boolean createKlausur(Klausur klausur) {
		try {
			setClassVariables(klausur);
			writer = PdfWriter.getInstance(klausurDokument.getDocument(), new FileOutputStream(getPDFName()));
			writer.setPageEvent(footEvent);
			KlausurLogger.getInstance().addLog("Öffne Dokument");
			klausurDokument.getDocument().open();
			addMetaDaten();
			KlausurLogger.getInstance().addLog("Setze Titelblatt");
			addTitleBlatt();
			klausurDokument.getDocument().newPage();
			KlausurLogger.getInstance().addLog("Setze Inhalte");
			addInhalt();
			KlausurLogger.getInstance().addLog("Schließe Dokument");
			klausurDokument.getDocument().close();
			KlausurLogger.getInstance().addLog("PDF erstellt");
		} catch (Exception e) {
			KlausurLogger.getInstance().addSaveError(e);
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	protected void setClassVariables(Klausur klausur) {
		this.klausur = klausur;
		this.fragenListe = klausur.getFragenList();
	}

	protected abstract String getPDFName();

	private void addInhalt() throws MalformedURLException, IOException, DocumentException {
		int frageIndex = 1;
		for (AbstractFrage frage : fragenListe) {
			KlausurLogger.getInstance().addLog("Setze Frage: "+frageIndex);
			KlausurParagraph frageParagraph = new KlausurParagraph();
			KlausurLogger.getInstance().addLog("Setze Fragetext");
			KlausurParagraph frageText = new KlausurParagraph();
			frageText.addText(frageIndex + ") " + frage.getFrageText());
			frageParagraph.addParagraph(frageText);
			KlausurLogger.getInstance().addLog("Setze Punkte");
			addPunkte(frageParagraph, frage.getPunkte());
			KlausurLogger.getInstance().addLog("Setze AntwortElemente");
			addAntwortElement(frageParagraph, frage);
			KlausurLogger.getInstance().addLog("Setze Paragraph in Dokument");
			klausurDokument.addParagraphs(frageParagraph);

			//pruefeFragenZahlAufSeite(frageIndex);

			frageIndex++;
		}
	}

	private void pruefeFragenZahlAufSeite(int frageIndex) {
		if (frageIndex % 3 == 0) {
			klausurDokument.getDocument().newPage();
		}
	}

	protected abstract void addAntwortElement(KlausurParagraph frageParagraph, AbstractFrage frage)
			throws BadElementException, MalformedURLException, IOException;

	private void addPunkte(KlausurParagraph frageParagraph, int punkte) {
		KlausurParagraph punkteParagraph = new KlausurParagraph();
		punkteParagraph.addText("(   /" + punkte + ")");
		punkteParagraph.getParagraph().setAlignment(Element.ALIGN_RIGHT);
		frageParagraph.addParagraph(punkteParagraph);

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

		klausurDokument.getDocument().add(notenBildung);

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

		klausurDokument.getDocument().add(punkteTable);
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

		klausurDokument.getDocument().add(eintrag);
	}

	private int getDurchgeange() {
		int listenGroesse = fragenListe.size();
		int mod6 = listenGroesse % 6;
		if (mod6 != 0) {
			listenGroesse = listenGroesse + (6 - mod6);
		}
		return listenGroesse / 6;
	}

	private void setLeerZeilen(int zeilen) throws DocumentException {
		for (int i = 0; i <= zeilen; i++) {
			klausurDokument.getDocument().add(new Paragraph(" "));
		}
	}

	private void setTitel() throws DocumentException {
		Font titelFont = new Font(Font.FontFamily.TIMES_ROMAN, 30, Font.BOLD);

		Paragraph title = new Paragraph("Klausur für Seminar:\n" + klausur.getKlausurName(), titelFont);
		title.setAlignment(Element.ALIGN_CENTER);
		klausurDokument.getDocument().add(title);

	}

	private void addMetaDaten() {
		klausurDokument.getDocument().addTitle("Klausur zum Seminar: " + klausur.getKlausurName());
		klausurDokument.getDocument().addAuthor(System.getProperty("user.name"));

	}

}
