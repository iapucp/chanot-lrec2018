package com.pucp.chanot.dto;

/**
 *
 * @author alulab14
 */
public class PalabraDTO extends BaseDTO {

    private String palabra;
    private String sugerencia;
    private Integer estado;

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

}
