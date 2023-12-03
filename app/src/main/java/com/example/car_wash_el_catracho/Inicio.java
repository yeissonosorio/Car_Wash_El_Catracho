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
        String estado=sharedPreferences.getString("estado","");
        String tok=sharedPreferences.getString("token","");
        //para hacer que actity se vea por 2 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(corr.equals("")){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else if(corr.equals("carwashelcatracho@gmail.com")){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else if (!corr.equals("carwashelcatracho@gmail.com")&&!corr.equals("")) {
                    Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                    id.setId(idu);
                    id.setCorreo(corr);
                    id.setNombre(nombre);
                    id.setPais(pais);
                    id.setFoto(foto);
                    id.setToken(tok);
                    startActivity(intent);
                }
            }
        }, TIEMPO_DEMOSTRACION);
    }

}