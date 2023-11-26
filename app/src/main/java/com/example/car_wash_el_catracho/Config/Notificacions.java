package com.example.car_wash_el_catracho.Config;

public class Notificacions {
    private  String id;
    private String id_cliente;
    private String marca;
    private String modelo;
    private String anio;
    private String aceite;
    private String fecha;
    private  String hora;
    private String precio;

    public Notificacions(String id, String id_cliente, String marca, String modelo, String anio, String aceite, String fecha, String hora, String precio) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.aceite = aceite;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
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

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getAceite() {
        return aceite;
    }

    public void setAceite(String aceite) {
        this.aceite = aceite;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
