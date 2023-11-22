package com.example.car_wash_el_catracho.Config;

public class ResapiMethod {

    public static final String separador="/";
    public static final String ipaddress="192.168.0.8";
    public static final String restapi="cw";

    public static final String PostRouting="CrearCliente.php";

    public static final String GettRouting="Clientesfiltro.php";

    public static final String GettVerificar="CorreoRepetido.php";

    public static final String Gettfoto="fotocliente.php";

    public static final String EndpoitpostCliente= "http://"+ipaddress+separador+restapi+separador+PostRouting;

    public static final String GetClienteF= "http://"+ipaddress+separador+restapi+separador+GettRouting;

    public static final String GetVerficacion= "http://"+ipaddress+separador+restapi+separador+GettVerificar;
    public static final String Getfoto= "http://"+ipaddress+separador+restapi+separador+Gettfoto;
}
