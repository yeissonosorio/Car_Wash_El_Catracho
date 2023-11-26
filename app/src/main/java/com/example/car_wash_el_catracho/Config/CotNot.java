package com.example.car_wash_el_catracho.Config;

public class CotNot {
    private  String id;
    private String id_cot;
    private String precio;
    private String id_cliente;
    private String id_auto;

    public CotNot(String id, String id_cot, String precio, String id_cliente, String id_auto) {
        this.id = id;
        this.id_cot = id_cot;
        this.precio = precio;
        this.id_cliente = id_cliente;
        this.id_auto = id_auto;
    }

    public CotNot() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_cot() {
        return id_cot;
    }

    public void setId_cot(String id_cot) {
        this.id_cot = id_cot;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getId_auto() {
        return id_auto;
    }

    public void setId_auto(String id_auto) {
        this.id_auto = id_auto;
    }
}
