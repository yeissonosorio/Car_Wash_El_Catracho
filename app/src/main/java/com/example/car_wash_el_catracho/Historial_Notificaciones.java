package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Historial_Notificaciones extends AppCompatActivity {

    Button botonatras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_notificaciones);
        botonatras = (Button) findViewById(R.id.btnAtrasNoti);

        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Navegacion.class);
                startActivity(intent);
            }
        });

    }
}