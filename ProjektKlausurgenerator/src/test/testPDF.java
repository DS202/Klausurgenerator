package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class testPDF {
	
	Document document = new Document();

	public void makePDF() throws FileNotFoundException, DocumentException {
		PdfWriter.getInstance(document, new FileOutputStream("Test.pdf"));
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		for(int i = 0; i<=100;i++) {
			
			Paragraph paragraph = new Paragraph("Hallo WElt", font);
			document.add(paragraph);
		}
		
		document.close();
	}
}
