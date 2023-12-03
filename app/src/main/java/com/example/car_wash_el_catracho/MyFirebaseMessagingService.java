package com.example.car_wash_el_catracho;

import android.content.SharedPreferences;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            // Si recibes datos en el mensaje
            String titulo = remoteMessage.getData().get("titulo");
            String mensaje = remoteMessage.getData().get("mensaje");
            // Mostrar la notificación usando NotificationHelper
            NotificationHelper.mostrarNotificacion(this, titulo, mensaje);

        }

        if (remoteMessage.getNotification() != null) {
            String titulo = remoteMessage.getNotification().getTitle();
            String cuerpo = remoteMessage.getNotification().getBody();
            NotificationHelper.mostrarNotificacion(this, titulo, cuerpo);
        }
    }

}
