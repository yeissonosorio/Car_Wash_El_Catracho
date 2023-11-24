package com.example.car_wash_el_catracho.Config;

public class Servicios {
    private  String id;
    private String id_cliente;
    private String nombre;
    private String servicio;
    private String id_servicio;
    private String fecha;
    private String hora;
    private String latitud;
    private String longitud;
    private String total;

    public Servicios(String id, String id_cliente, String nombre, String servicio, String id_servicio, String fecha, String hora, String latitud, String longitud, String total) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.servicio = servicio;
        this.id_servicio = id_servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.total = total;
    }

    public Servicios() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(String id_servicio) {
        this.id_servicio = id_servicio;
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
