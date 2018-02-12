package com.pucp.chanot.dto;

/**
 *
 * @author jose
 */
public class ArchivoDTO extends BaseDTO {

    private String archivo;
    private Integer estado;

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

}
