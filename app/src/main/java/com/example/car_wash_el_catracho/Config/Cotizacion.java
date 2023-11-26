package com.example.car_wash_el_catracho.Config;

public class Cotizacion {
    private String id;
    private String id_cliente;
    private String id_vehiculo;
    private String fecha;
    private String hora;
    private String id_servicio;
    private String estado;

    public Cotizacion(String id, String id_cliente, String id_vehiculo, String fecha, String hora, String id_servicio, String estado) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_vehiculo = id_vehiculo;
        this.fecha = fecha;
        this.hora = hora;
        this.id_servicio = id_servicio;
        this.estado = estado;
    }

    public Cotizacion() {
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

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
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

    public String getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(String id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
