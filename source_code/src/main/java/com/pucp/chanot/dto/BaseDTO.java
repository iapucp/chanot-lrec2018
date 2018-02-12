package com.pucp.chanot.dto;

import java.io.Serializable;

/**
 *
 * @author jose
 */
public class BaseDTO implements Serializable {

    private Boolean error = false;
    private String mensaje;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}
