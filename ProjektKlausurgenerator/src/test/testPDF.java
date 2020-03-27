package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Klausurgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Loesungsgenerator;

public class testPDF {

	Document document = new Document();

	public void makePDF() throws FileNotFoundException, DocumentException {
		PdfWriter.getInstance(document, new FileOutputStream("Test.pdf"));
		document.open();

		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		for (int i = 0; i <= 100; i++) {

			Paragraph paragraph = new Paragraph("Hallo WElt", font);
			document.add(paragraph);
		}

		document.close();
	}

	public void testKlausur() {
		List<AbstractFrage> list = new ArrayList<AbstractFrage>();
		String[] mA = { "Pokemon", "Pikachu", "Maunzi" };
		for (int i = 5; i > 0; i--) {

			list.add(new Freitext("Test", Schwierigkeitsgrad.leicht, i, "TestSeminar", mA));
		}
		
		String[][] mA2 = {{"Pikachu",Boolean.toString(false)},{"Pokemon",Boolean.toString(true)},{"Maunzi",Boolean.toString(false)}};

		list.add(new MultiChoiceFrage("Franchisename", Schwierigkeitsgrad.mittel, 5, "TestMulti" ,mA2) );

		Klausur klausur = new Klausur(100, "KlausurTest", list);

		try {
			new Klausurgenerator().createKlausur(klausur);
			new Loesungsgenerator().createKlausur(klausur);
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
