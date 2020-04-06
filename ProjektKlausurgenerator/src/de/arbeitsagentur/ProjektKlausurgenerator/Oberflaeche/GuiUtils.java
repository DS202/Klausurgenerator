package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;

public class GuiUtils {

//	public static void main(String[] args) {
//		GuiUtils g = new GuiUtils();
//		g.csvEinlesen();
//	}

	/**
	 * Gibt ein Skaliertes ImageIcon zurueck.
	 * 
	 * @param url relativer Pfad zum PDF
	 * @param x   Hohe in int
	 * @param y   Breite in int
	 * @return Skaliertes ImageIcon
	 */
	public static ImageIcon getScaledImageIcon(String url, int x, int y) {

		URL imageUrl = Hauptfenster.class.getClassLoader().getResource(url);
		ImageIcon imageIcon = new ImageIcon(imageUrl); // Bild

		Image image = imageIcon.getImage(); // umwandeln
		Image newimg = image.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH); // smooth Skalieren
		imageIcon = new ImageIcon(newimg); // zurueck umwandeln

		return imageIcon;
	}

	/**
	 * Gibt den aktuellen Wochentag + das aktuelle Datum als String zurueck.
	 * 
	 * @return String aktuelles Datum.
	 */
	public static String holeAktuellesDatum() {

		String wochentag = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE", Locale.GERMAN));

		return wochentag + ", der " + LocalDateTime.now().getDayOfMonth() + "." + LocalDateTime.now().getMonthValue()
				+ "." + LocalDateTime.now().getYear();
	}

	/**
	 * Liest die Fragen.CSV ein und gibt eine Liste mit Abstrakte Fragen zurueck.
	 * 
	 * @return List<AbstrakteFrage>
	 */
	public List<AbstractFrage> csvEinlesen() {

		List<String> zeilenListe = new ArrayList<>();

		try {
			zeilenListe = Files.readAllLines(Paths.get("resources/dateien/fragen.csv"));
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen der Datei");
			e.printStackTrace();
		}

		return fragenErstellenAusZeilenliste(zeilenListe);
	}

	/**
	 * Schreibt eine Frage in die CSV Datei (fragen.csv)
	 * 
	 * @param text Frage als Komma-Separated-Value (Trennzeichen == ;).
	 */
	public void schreibeFrageInCSV(String text) {

		try {
			Files.write(Paths.get("resources/dateien/fragen.csv"), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("An error occurred while filewriting.");
			e.printStackTrace();
		}
	}

	/**
	 * Erstellt aus einer Liste mit Zeilen eine Liste mit Abstrakten Fragen.
	 * 
	 * @param zeilenListe Liste mit Strings.
	 * @return Liste mit Abstrakten Fragen.
	 */
	private List<AbstractFrage> fragenErstellenAusZeilenliste(List<String> zeilenListe) {
		
		zeilenListe= removeEmptyLines(zeilenListe);

		List<AbstractFrage> fragenListe = new ArrayList<>();

		for (int i = 0; i < zeilenListe.size(); i++) {

			String[] splittArray = zeilenListe.get(i).split(";");
			AbstractFrage frage = null;

			if (zeilenListe.get(i).contains("Freitext")) {
				frage = new Freitext(splittArray[1], Enum.valueOf(Schwierigkeitsgrad.class, splittArray[2]),
						Double.parseDouble(splittArray[3]), splittArray[4], null);

			} else if (zeilenListe.get(i).contains("Multichoice")) {
				
				String[][] arr = {{splittArray[5]},{splittArray[6]},{splittArray[7]},{splittArray[8]}};
				
				
				//String[] arr = { splittArray[5], splittArray[6], splittArray[7], splittArray[8] };
				
				frage = new MultiChoiceFrage(splittArray[1], Enum.valueOf(Schwierigkeitsgrad.class, splittArray[2]),
						Double.parseDouble(splittArray[3]), splittArray[4], arr);
			}
			fragenListe.add(frage);
		}
		return fragenListe;
	}

	/**Entfernt leere Elemente aus einer Liste mit Zeilen. 
	 * 
	 * @param listFragen List mit Strings (Zeilen)
	 * @return Liste mit String ohne Leere Zeilen
	 */
	private List<String> removeEmptyLines(List<String> listFragen) {

		List<String> result = new ArrayList<>();

		for (int i = 0; i < listFragen.size(); i++) {
			if (!listFragen.get(i).equals("")) {
				result.add(listFragen.get(i));
			}
		}
		return result;
	}

}
