package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Verificacion extends AppCompatActivity {
    Button ver;
    String valor;

    EditText vericod;

    TextView envio;

    CountDownTimer tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        ver = (Button) findViewById(R.id.btnverificar);
        envio = (TextView) findViewById(R.id.txtxrenvio);
        vericod = (EditText) findViewById(R.id.TxtCodigo);


        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validacion()==true) {
                    valor = getIntent().getStringExtra("valor");

                    if (valor.equals("Recuperar")) {
                        Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), password.class);
                        startActivity(intent);
                    } else if (valor.equals("Crear")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
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

        vericod.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // Oculta el teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private boolean validacion() {
        boolean valor=false;

        String cod = vericod.getText().toString().replaceAll("\\s","");


        if(cod.isEmpty()){
            vericod.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }
}