package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Image;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.ImageIcon;

public class GuiUtils {

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

}
