package de.arbeitsagentur.ProjektKlausurgenerator.model;

import java.util.List;

public class Klausur {

	private double punkteDouble;
	private String klausurname;
	private List<AbstractFrage> fragenList;

	public double getPunkte() {
		return punkteDouble;
	}

	public String getKlausurName() {
		return klausurname;
	}

	public List<AbstractFrage> getFragenList() {
		return fragenList;
	}

	public Klausur(Double punkteDouble, String klausurname, List<AbstractFrage> fragenList) {
		this.punkteDouble = punkteDouble;
		this.klausurname = klausurname;
		this.fragenList = fragenList;
	}

}
