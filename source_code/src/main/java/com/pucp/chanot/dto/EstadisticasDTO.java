package com.pucp.chanot.dto;

/**
 *
 * @author jose
 */
public class EstadisticasDTO extends BaseDTO {

    private String oraciones;
    private String palabras;
    private String tiempoTotal;
    private String tiempoProm;

    public String getOraciones() {
        return oraciones;
    }

    public void setOraciones(String oraciones) {
        this.oraciones = oraciones;
    }

    public String getPalabras() {
        return palabras;
    }

    public void setPalabras(String palabras) {
        this.palabras = palabras;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public String getTiempoProm() {
        return tiempoProm;
    }

    public void setTiempoProm(String tiempoProm) {
        this.tiempoProm = tiempoProm;
    }

}
