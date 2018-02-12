package com.pucp.chanot.dto;

import java.util.List;

/**
 *
 * @author jose
 */
public class ListArchivoDTO extends BaseDTO {

    private List<ArchivoDTO> archivos;
    private Integer total;

    public List<ArchivoDTO> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<ArchivoDTO> archivos) {
        this.archivos = archivos;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
