package com.example.car_wash_el_catracho.Config;

public class ResapiMethod {

    public static final String separador="/";
    public static final String ipaddress="192.168.0.7";
    public static final String restapi="cw";

    public static final String PostRouting="CrearCliente.php";

    public static final String PostRotingAuto="CrearVehiculo.php";

    public static final String PostHistorial="createhistorialDeReservacionesAdmin.php";

    public static final String PostCotizacion="createcotizacion.php";
    public static final String PostCotizacionAd="create_cotizacion_admin.php";
    public static final String GettRouting="Clientesfiltro.php";

    public static final String GettVerificar="CorreoRepetido.php";
    public static final String GettCotizacion="getcotizacion.php";

    public static final String Gettvalidar="VerificarReservacion.php";

    public static final String GettAutos="Listavehiculos.php";

    public static final String Gettidnotificacion="Notificacion.php";

    public static final String GettHistorial="historialDeReservacionesAdminFiltro.php";
    public static final String GettNotificacion="cotizacion_admin_filtro.php";
    public static final String PutCotizacion="updatecotizacion.php";

    public static final String PutNoti="update_cotizacion_admin.php";

    public static final String DeltAutos="eliminarVehiculo.php";

    public static final String EndpoitpostCliente= "http://"+ipaddress+separador+restapi+separador+PostRouting;
    public static final String EndpoitServcio= "http://"+ipaddress+separador+restapi+separador+PostHistorial;

    public static final String EndpoitpostAuto= "http://"+ipaddress+separador+restapi+separador+PostRotingAuto;

    public static final String Endpoitpostnot= "http://"+ipaddress+separador+restapi+separador+PostCotizacionAd;

    public static final String EndpoitCotizacion= "http://"+ipaddress+separador+restapi+separador+PostCotizacion;


    public static final String GetClienteF= "http://"+ipaddress+separador+restapi+separador+GettRouting;

    public static final String GetVerficacion= "http://"+ipaddress+separador+restapi+separador+GettVerificar;

    public static final String GettHistorialF="http://"+ipaddress+separador+restapi+separador+GettHistorial;
    public static final String GettNOTFINAL="http://"+ipaddress+separador+restapi+separador+GettNotificacion;
    public static final String Gettreservavalida="http://"+ipaddress+separador+restapi+separador+Gettvalidar;

    public static final String GettNumeroNoti="NumeroNotificacion.php";
    public static final String GettNotiNumeor="http://"+ipaddress+separador+restapi+separador+GettNumeroNoti;

    public static final String GettAutocliente="http://"+ipaddress+separador+restapi+separador+GettAutos;

    public static final String GettNotificaiconId="http://"+ipaddress+separador+restapi+separador+Gettidnotificacion;
    public static final String GettpointCotizacion="http://"+ipaddress+separador+restapi+separador+GettCotizacion;

    public static final String PutendpointCotizacion="http://"+ipaddress+separador+restapi+separador+PutCotizacion;
    public static final String PutendpointNOti="http://"+ipaddress+separador+restapi+separador+PutNoti;

    public static final String DeltAutocliente="http://"+ipaddress+separador+restapi+separador+DeltAutos;
}
