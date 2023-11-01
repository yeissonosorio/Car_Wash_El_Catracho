package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Verificacion extends AppCompatActivity {
    Button ver;
    String valor;

    TextView envio;

    CountDownTimer tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        ver = (Button) findViewById(R.id.btnverificar);
        envio = (TextView) findViewById(R.id.txtxrenvio);


        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valor = getIntent().getStringExtra("valor");

                if(valor.equals("Recuperar")) {
                    Toast.makeText(getApplicationContext(),valor,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), password.class);
                    startActivity(intent);
                } else if (valor.equals("Crear")) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        long totaltiempoenmilisegundo= 1*60*1000;
        long intervalosminisegundos= 1000;

        tiempo = new CountDownTimer(totaltiempoenmilisegundo,intervalosminisegundos) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutos = millisUntilFinished/60000;
                long segundos = (millisUntilFinished % 60000)/1000;
                String Formatotiempo = String.format("%02d:%02d",minutos,segundos);
                envio.setText(Formatotiempo);
            }

            @Override
            public void onFinish() {
                envio.setText("Reenviar Codigo");
            }
        };
        tiempo.start();


        envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (envio.getText().toString().equals("Reenviar Codigo")){
                    Toast.makeText(getApplicationContext(),"Codigo reenviado",Toast.LENGTH_LONG).show();
                    tiempo.start();
                }
            }
        });
    }
}