package de.arbeitsagentur.ProjektKlausurgenerator.model;

import java.util.List;

public class Klausur {

	private int anzahlPunkte;
	private double punkteDouble;
	private String klausurname;
	private List<AbstractFrage> fragenList;
	
	public int getPunkte() {
		return anzahlPunkte;
	}

	public String getKlausurName() {
		return klausurname;
	}

	public List<AbstractFrage> getFragenList() {
		return fragenList;
	}

	public Klausur(int anzahlPunkte, String klausurname, List<AbstractFrage> fragenList){
		this.anzahlPunkte = anzahlPunkte;
		this.klausurname = klausurname;
		this.fragenList = fragenList;
	}
	
	public Klausur(Double punkteDouble, String klausurname, List<AbstractFrage> fragenList){
		this.punkteDouble = punkteDouble;
		this.klausurname = klausurname;
		this.fragenList = fragenList;
	}
	
	
	
}
