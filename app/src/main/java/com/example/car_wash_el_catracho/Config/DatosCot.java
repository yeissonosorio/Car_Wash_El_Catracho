package com.example.car_wash_el_catracho.Config;

public class DatosCot {
    private  String id;
    private  String idcliente;
    private  String correo;
    private  String idvehiculo;
    private  String marca;
    private  String modelo;
    private  String year;
    private  String aceite;
    private  String fecha;
    private  String hora;
    private  String servicio;

    public DatosCot(String id, String idcliente, String correo, String idvehiculo, String marca, String modelo, String year, String aceite, String fecha, String hora, String servicio) {
        this.id = id;
        this.idcliente = idcliente;
        this.correo = correo;
        this.idvehiculo = idvehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.year = year;
        this.aceite = aceite;
        this.fecha = fecha;
        this.hora = hora;
        this.servicio = servicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdvehiculo() {
        return idvehiculo;
    }

    public void setIdvehiculo(String idvehiculo) {
        this.idvehiculo = idvehiculo;
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
