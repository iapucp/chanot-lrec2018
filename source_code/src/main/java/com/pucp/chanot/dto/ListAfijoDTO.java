package com.pucp.chanot.dto;

import java.util.List;

/**
 *
 * @author alulab14
 */
public class ListAfijoDTO {

    private List<AfijoDTO> afijos;
    private Integer total;

    public List<AfijoDTO> getAfijos() {
        return afijos;
    }

    public void setAfijos(List<AfijoDTO> afijos) {
        this.afijos = afijos;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
