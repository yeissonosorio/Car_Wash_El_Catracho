package com.example.car_wash_el_catracho.Config;

public class id {
    private static String id;

    private static String nombre;



    private static String correo;

    private static String pais;

    private static String foto;

    public static String getId() {
        return id;
    }

    public static void setId(String Id) {
        id = Id;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String Nombre) {
        nombre = Nombre;
    }
    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String Correo) {
        correo = Correo;
    }

    public static String getPais() {
        return pais;
    }

    public static void setPais(String Pais) {
        pais = Pais;
    }

    public static String getFoto() {
        return foto;
    }

    public static void setFoto(String Foto) {
        foto = Foto;
    }
}
