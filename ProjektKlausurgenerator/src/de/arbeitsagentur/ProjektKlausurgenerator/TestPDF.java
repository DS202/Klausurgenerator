package de.arbeitsagentur.ProjektKlausurgenerator;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Klausur;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Klausurgenerator;
import de.arbeitsagentur.ProjektKlausurgenerator.model.klausurgenerator.Loesungsgenerator;

public class TestPDF {

	protected Klausur klausur;
	protected Document klausurDokument = new Document();
	protected PdfWriter writer;

	public TestPDF() { // 42 Zeilen, 78 Zeichen, Linie 2 Zeilen
		testKlausur();
	}

	public void testKlausur() {
		List<AbstractFrage> list = new ArrayList<>();
		String[] mA = { "Pokemon", "Pikachu", "Maunzi" };
		for (int i = 5; i > 0; i--) {

			list.add(new Freitext("NewTest", Schwierigkeitsgrad.leicht, Double.parseDouble(i + ""), "TestSeminar", mA));
		}
		String[][] mA2 = { { "Pokemon", "true" }, { "Pikachu", "false" }, { "Maunzi", "false" } };
		list.add(new MultiChoiceFrage("Franchisename", Schwierigkeitsgrad.mittel, 5.5, "Seminar", mA2));

		Klausur klausur = new Klausur(100.0, "KlausurTest", list);

		new Klausurgenerator().createKlausur(klausur);
		new Loesungsgenerator().createKlausur(klausur);
	}
}
