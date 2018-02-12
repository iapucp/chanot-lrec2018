package com.pucp.chanot.dto;

/**
 *
 * @author jose
 */
public class AnotacionDTO extends BaseDTO {

    private String palabra;
    private String lema;
    private String categoria;
    private String subCategoria; 
    private String subCat; //subCategoria
    private ListAfijoDTO afijos;

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public ListAfijoDTO getAfijos() {
        return afijos;
    }

    public void setAfijos(ListAfijoDTO afijos) {
        this.afijos = afijos;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

}
