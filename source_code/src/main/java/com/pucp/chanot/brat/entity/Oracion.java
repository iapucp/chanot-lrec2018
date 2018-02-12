package com.pucp.chanot.brat.entity;

import java.util.ArrayList;

/**
 *
 * @author jose
 */
public class Oracion {

    private int id;
    private String texto;
    private String traduccion;
    private ArrayList<Palabra> palabra = new ArrayList<>();
    private ArrayList<Oracion> morf = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public ArrayList<Palabra> getPalabras() {
        return palabra;
    }

    public void setPalabras(ArrayList<Palabra> palabra) {
        this.palabra = palabra;
    }

    public ArrayList<Oracion> getMorf() {
        return morf;
    }

    public void setMorf(ArrayList<Oracion> morf) {
        this.morf = morf;
    }

}
