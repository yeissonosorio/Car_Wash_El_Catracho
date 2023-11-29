package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.car_wash_el_catracho.Config.id;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {
    //Tiempo que tendra la pantalla de inicio
    private static final int TIEMPO_DEMOSTRACION = 2000;
    //obtner informacion del usuario guardado en el dispositivo
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        //Obtener informacion de las preferencias
        sharedPreferences = getSharedPreferences("Usuario", MODE_PRIVATE);
        String idu=sharedPreferences.getString("id","");
        String corr=sharedPreferences.getString("correo","");
        String nombre=sharedPreferences.getString("nombre","");
        String pais=sharedPreferences.getString("pais","");
        String foto=sharedPreferences.getString("foto","");

        //para hacer que actity se vea por 2 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //hacer si tiene informacion de usuario lo pueda ir al ativyti de proceso sino va al de inicio de sesion
                if(idu.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    id.setId(idu);
                    id.setNombre(nombre);
                    id.setPais(pais);
                    id.setCorreo(corr);
                    id.setFoto(foto);
                    Intent intent = new Intent(getApplicationContext(), Navegacion.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, TIEMPO_DEMOSTRACION);
    }

}