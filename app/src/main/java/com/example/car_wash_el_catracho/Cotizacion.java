package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cotizacion extends AppCompatActivity {
    private int salir=0;
    Button btncerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizacion);
        btncerrar = (Button) findViewById(R.id.btncerrarcot);

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {


        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    finishAffinity();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    if(salir==1){
                        super.onBackPressed();
                    }

                })
                .show();
    }
}