package com.example.car_wash_el_catracho.Config;

public class ResapiMethod {

    public static final String separador="/";
    public static final String ipaddress="192.168.0.7";
    public static final String restapi="cw";

    public static final String PostRouting="CreatePerson.php";

    public static final String GettRouting="Clientesfiltro.php";

    public static final String Endpoitpost= "http://"+ipaddress+separador+restapi+separador+PostRouting;

    public static final String GetClienteF= "http://"+ipaddress+separador+restapi+separador+GettRouting;
}
