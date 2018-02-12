package com.pucp.chanot.brat.entity;

import java.util.ArrayList;

public class Relacion {
	private ArrayList<Oracion> oraciones;
	private ArrayList<String> relaciones;
	
	public ArrayList<Oracion> getOraciones() {
		return oraciones;
	}

	public void setOraciones(ArrayList<Oracion> oraciones) {
		this.oraciones = oraciones;
	}
	
	public ArrayList<String> getRelaciones(){
		return relaciones;
	}
	
	public void setRelaciones(ArrayList<String> relaciones){
		this.relaciones = relaciones;
	}
}
