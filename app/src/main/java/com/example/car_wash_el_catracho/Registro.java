package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registro extends AppCompatActivity {
    Button crear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        crear = (Button) findViewById(R.id.btnagregarVehiculo);

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Verificacion.class);
                intent.putExtra("valor","Crear");
                startActivity(intent);
            }
        });
    }
}