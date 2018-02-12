package com.pucp.chanot.dto;

import java.util.List;

/**
 *
 * @author alulab14
 */
public class ListPalabraDTO extends BaseDTO {

    private List<PalabraDTO> palabras;
    private Integer total;
    private String traduccion;
    private String oracion;
    private Integer totalOraciones;
    private Integer oracionActual;

    public List<PalabraDTO> getPalabras() {
        return palabras;
    }

    public void setPalabras(List<PalabraDTO> palabras) {
        this.palabras = palabras;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTraduccion() {
        return traduccion;
    }

    public void setTraduccion(String traduccion) {
        this.traduccion = traduccion;
    }

    public Integer getTotalOraciones() {
        return totalOraciones;
    }

    public void setTotalOraciones(Integer totalOraciones) {
        this.totalOraciones = totalOraciones;
    }

    public Integer getOracionActual() {
        return oracionActual;
    }

    public void setOracionActual(Integer oracionActual) {
        this.oracionActual = oracionActual;
    }

    public String getOracion() {
        return oracion;
    }

    public void setOracion(String oracion) {
        this.oracion = oracion;
    }

}
