package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
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
import javax.swing.JOptionPane;

import de.arbeitsagentur.ProjektKlausurgenerator.enums.Schwierigkeitsgrad;
import de.arbeitsagentur.ProjektKlausurgenerator.model.AbstractFrage;
import de.arbeitsagentur.ProjektKlausurgenerator.model.Freitext;
import de.arbeitsagentur.ProjektKlausurgenerator.model.MultiChoiceFrage;

/* Allgemeine Funktionen fuer die Oberflaeche + Komplette Logik neu.
 * 
 * @author Nico
 *
 */
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

	/**
	 * Erzeugt eine Windows Notification.
	 * 
	 * @param ueberschrift
	 * @param text         Textnachricht der Notification
	 * @param messagetyp   (Info / Error/ Warning)
	 * 
	 * @author Anna
	 */
	public void winNotification(String ueberschrift, String text, String messagetyp) {
		try {
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().createImage("resources/images/info.PNG");
			TrayIcon trayIcon = new TrayIcon(image, "");
			trayIcon.setImageAutoSize(true);
			tray.add(trayIcon);
			if (messagetyp.equalsIgnoreCase("Error")) {
				trayIcon.displayMessage(ueberschrift, text, MessageType.ERROR);
			} else if (messagetyp.equalsIgnoreCase("Warning")) {
				trayIcon.displayMessage(ueberschrift, text, MessageType.WARNING);
			} else if (messagetyp.equalsIgnoreCase("Info")) {
				trayIcon.displayMessage(ueberschrift, text, MessageType.INFO);
			} else {
				JOptionPane.showMessageDialog(null, "TYP ANGEBEN AMK", "Fehler", 0);
			}
		} catch (Exception ex) {
			System.out.println("Fehler beim erzeugen der Windows Notification.");
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * Einlesen einer CSV-Datei mit allen Fragen und auswerten der Daten.
	 * 
	 * 
	 * 
	 */

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

	/*
	 * Erstellt aus einer Liste mit Zeilen eine Liste mit Abstrakten Fragen.
	 * 
	 * @param zeilenListe Liste mit Strings.
	 * 
	 * @return Liste mit Abstrakten Fragen.
	 */
	private List<AbstractFrage> fragenErstellenAusZeilenliste(List<String> zeilenListe) {

		zeilenListe = removeEmptyLines(zeilenListe);

		List<AbstractFrage> fragenListe = new ArrayList<>();

		for (int i = 0; i < zeilenListe.size(); i++) {

			String[] splittArray = zeilenListe.get(i).split(";");
			AbstractFrage frage = null;

			if (zeilenListe.get(i).contains("Freitext")) {

				frage = new Freitext(splittArray[1], Enum.valueOf(Schwierigkeitsgrad.class, splittArray[2]),
						Double.parseDouble(splittArray[3]), splittArray[4], splittArray[5].split(" "));

			} else if (zeilenListe.get(i).contains("Multichoice")) {

				String[][] arr = { { splittArray[5], splittArray[6] }, { splittArray[7], splittArray[8] },
						{ splittArray[9], splittArray[10] }, { splittArray[11], splittArray[12] } };

				frage = new MultiChoiceFrage(splittArray[1], Enum.valueOf(Schwierigkeitsgrad.class, splittArray[2]),
						Double.parseDouble(splittArray[3]), splittArray[4], arr);
			}
			fragenListe.add(frage);
		}
		return fragenListe;
	}

	/**
	 * Entfernt leere Elemente aus einer Liste mit Zeilen.
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

	/*
	 *
	 * Schreibt Fragen in die CSV-Datei mit allen Fragen.
	 * 
	 * 
	 */

	/**
	 * Schreibt eine Frage in die CSV Datei (fragen.csv)
	 * 
	 * @param text Frage als Komma-Separated-Value (Trennzeichen == ;).
	 */
	public void schreibeFrageInCSV(String text) {
		text = text.replaceAll("Ö", "Oe").replaceAll("Ä", "Ae").replaceAll("Ü", "Ue").replaceAll("ß", "ss")
				.replaceAll("ö", "oe").replaceAll("ä", "ae").replaceAll("ü", "ue");

		try {
			Files.write(Paths.get("resources/dateien/fragen.csv"), text.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("An error occurred while filewriting.");
			e.printStackTrace();
		}
	}

}
