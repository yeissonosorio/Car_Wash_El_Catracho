package com.example.car_wash_el_catracho.Config;

public class Historial {
    private String servicio;
    private String fecha;
    private String hora;
    private String latitud;
    private String longitud;
    private String toal;

    public Historial(String servicio, String fecha, String hora, String latitud, String longitud, String toal) {
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.toal = toal;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
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

    public String getToal() {
        return toal;
    }

    public void setToal(String toal) {
        this.toal = toal;
    }
}
