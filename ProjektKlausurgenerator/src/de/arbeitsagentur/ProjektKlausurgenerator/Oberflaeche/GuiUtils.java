package de.arbeitsagentur.ProjektKlausurgenerator.Oberflaeche;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
	public static ImageIcon bekommeSkaliertesImageIcon(String url, int x, int y) {

		URL imageUrl = Hauptfenster.class.getClassLoader().getResource(url);
		ImageIcon imageIcon = new ImageIcon(imageUrl); // Bild

		Image image = imageIcon.getImage(); // umwandeln
		Image newimg = image.getScaledInstance(x, y, Image.SCALE_SMOOTH); // smooth Skalieren
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
	public void erzeugeWindowsNotification(String ueberschrift, String text, String messagetyp) {
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

}
