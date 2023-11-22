package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Recuperar extends AppCompatActivity {
    Button btncorreo;
    EditText txtcorreo;

    final String username ="carwashelcatracho@gamil.com";
    final String password="Grupo51@";
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
                    //Intent intent = new Intent(getApplicationContext(), Verificacion.class);
                    //intent.putExtra("valor", "Recuperar");
                    //intent.putExtra("correo", txtcorreo.getText().toString());
                    //startActivity(intent);
                    enviarmensaje();
                }
            }
        });

        txtcorreo.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // Oculta el teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private void enviarmensaje() {
    }


    private boolean validar() {
        boolean valor=false;

        String corr = txtcorreo.getText().toString().replaceAll("\\s","");

        if(corr.isEmpty()){
            txtcorreo.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }
}