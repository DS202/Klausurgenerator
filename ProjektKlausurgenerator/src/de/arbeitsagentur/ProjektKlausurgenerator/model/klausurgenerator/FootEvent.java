package de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
/**
 * Eventklasse um die Seitenzahlen zu Speichern
 * @author DDJ
 *
 */
public class FootEvent extends PdfPageEventHelper {
	
	private int pageCounter = 1;
	
	public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();

        Phrase footer = new Phrase(Integer.toString(pageCounter));
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);
        
        pageCounter++;
    }
}
