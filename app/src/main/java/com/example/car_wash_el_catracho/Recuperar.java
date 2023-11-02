package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recuperar extends AppCompatActivity {
    Button btncorreo;
    EditText txtcorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        btncorreo= (Button) findViewById(R.id.btnvercorreo);
        txtcorreo = (EditText) findViewById(R.id.txtCorreoRecu);

        btncorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()==true) {
                    Intent intent = new Intent(getApplicationContext(), Verificacion.class);
                    intent.putExtra("valor", "Recuperar");
                    intent.putExtra("correo", txtcorreo.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validar() {
        boolean valor=false;

        String corr = txtcorreo.getText().toString().replaceAll("\\s","");

        if(corr.isEmpty()){
            Toast.makeText(getApplicationContext(),"Ingrese su correo electronico",Toast.LENGTH_LONG).show();
        }
        else {
            valor=true;
        }
        return valor;
    }
}