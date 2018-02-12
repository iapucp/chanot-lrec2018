package com.pucp.chanot.brat.entity;

/**
 *
 * @author jose
 */
public class Palabra {

    private int Id;
    private String Token;
    private String Lema;
    private String CategGram;
    private String SubCategGram;
    private String Order;
    private String Type;

    //private ArrayList<Afijo>afijo = new ArrayList<>();
    
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getLema() {
        return Lema;
    }

    public void setLema(String lema) {
        Lema = lema;
    }

    public String getCategGram() {
        return CategGram;
    }

    public void setCategGram(String categGram) {
        CategGram = categGram;
    }

    public String getSubCategGram() {
        return SubCategGram;
    }

    public void setSubCategGram(String subCategGram) {
        SubCategGram = subCategGram;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
