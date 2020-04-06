package de.arbeitsagentur.ProjektKlausurgenerator.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger-Klasse
 * 
 * @author DDJ
 *
 */
public class KlausurLogger {

	private final static Logger log = Logger.getLogger(KlausurLogger.class.getName());
	private static KlausurLogger instance;

	/**
	 * Get die Instance der Klasse
	 * 
	 * @return
	 */
	public static KlausurLogger getInstance() {
		if (instance == null) {
			instance = new KlausurLogger();
		}
		return instance;
	}

	private KlausurLogger() {
		Handler handler = null;
		try {
			handler = new FileHandler("KlausurGeneratorLog.txt");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.setLevel(Level.ALL);
		log.addHandler(handler);
	}

	/**
	 * Loggen von Schritten
	 * 
	 * @param info
	 */
	public void addLog(String info) {
		log.info(info);
	}

	/**
	 * Nimmt Errors auf zum Loggen Wenn Dateien nicht gefunden oder Geladen werden
	 * konnten
	 * 
	 * @param error
	 */
	public void addLoadError(Exception error) {
		log.log(Level.WARNING, "Datei nicht gefunden", error);
	}

	/**
	 * Nimmt erros auf bei Speicherfehlern
	 * 
	 * @param error
	 */
	public void addSaveError(Exception error) {
		log.log(Level.WARNING, "Speichern fehlgeschlagen", error);
	}

	/**
	 * Nimmt Fehler von der View auf
	 * 
	 * @param error
	 */
	public void addViewError(Exception error) {
		log.log(Level.SEVERE, "Fenster konnte nicht geladen werden!", error);
	}

}
