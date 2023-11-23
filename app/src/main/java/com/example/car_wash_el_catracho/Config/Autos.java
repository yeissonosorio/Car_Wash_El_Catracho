package com.example.car_wash_el_catracho.Config;

public class Autos {
    private  String id;
    private  String marca;
    private  String modelo;
    private String year;
    private String acite;

    public Autos(String id, String marca, String modelo, String year, String acite) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.year = year;
        this.acite = acite;
    }

    public Autos() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAcite() {
        return acite;
    }

    public void setAcite(String acite) {
        this.acite = acite;
    }
}
