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

public class Agregar_Auto extends AppCompatActivity {

    EditText marca,modelo,year,aceite;

    Button agregar,atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);
        marca=(EditText) findViewById(R.id.txtmarca);
        modelo=(EditText) findViewById(R.id.txtmodelo);
        year=(EditText) findViewById(R.id.txtyea);
        aceite=(EditText) findViewById(R.id.txtaceite);

        agregar = (Button) findViewById(R.id.btnagregar);
        atras = (Button) findViewById(R.id.btnatraAgre);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()==true){
                    Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                    startActivity(intent);
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                    startActivity(intent);
            }
        });

        aceite.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // Oculta el teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private boolean validar() {

        boolean valor=false;

        String marc= marca.getText().toString().replaceAll("\\s","");
        String model=modelo.getText().toString().replaceAll("\\s","");
        String yyyy= year.getText().toString().replaceAll("\\s","");
        String ace= aceite.getText().toString().replaceAll("\\s","");

        if(marc.isEmpty()&&model.isEmpty()&&yyyy.isEmpty()&&ace.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (marc.isEmpty()) {
            marca.setError("No deje este campo vacio");
        } else if (model.isEmpty()) {
            modelo.setError("Debe llenar este campo");
        }
        else if (yyyy.isEmpty()) {
            year.setError("Debe llenar este campo");
        }
        else if (ace.isEmpty()) {
            aceite.setError("Debe llenar este campo");
        } else if (marca.length()>30) {
          marca.setError("Solo se acepta 30 caracteres");
        }
        else if (modelo.length()>30) {
            modelo.setError("Solo se acepta 30 caracteres");
        }
        else if (aceite.length()>30) {
            aceite.setError("Solo se acepta 30 caracteres");
        }
        else if (year.length()>4) {
            year.setError("Solo se acepta 4 caracteres");
        }
        else
        {
            valor=true;
        }
        return valor;
    }
}