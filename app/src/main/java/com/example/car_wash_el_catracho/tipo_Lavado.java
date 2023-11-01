package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class tipo_Lavado extends AppCompatActivity {
    Button btnvol;
    TextView tipo,titul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_lavado);

        tipo=(TextView) findViewById(R.id.tvprecio);
        titul = (TextView) findViewById(R.id.tituloL);


        String servicio = getIntent().getStringExtra("tipo");

        if (servicio.equals("Completo")){
            titul.setText("Lavado Completo");
            tipo.setText("Precio: L.150");
        } else if (servicio.equals("fuera")) {
            tipo.setText("Precio: L.100");
            titul.setText("Lavado por fuera");
        }

        btnvol =(Button) findViewById(R.id.btnAtras);

        btnvol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                startActivity(intent);
            }
        });

    }
}