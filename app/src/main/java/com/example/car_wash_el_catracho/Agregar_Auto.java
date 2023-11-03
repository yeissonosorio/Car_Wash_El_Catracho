package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class Agregar_Auto extends AppCompatActivity {

    EditText marca,modelo,year,aceite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);
        marca=(EditText) findViewById(R.id.txtmarca);
        modelo=(EditText) findViewById(R.id.txtmodelo);
        year=(EditText) findViewById(R.id.txtyea);
        aceite=(EditText) findViewById(R.id.txtaceite);

        validar();
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
        }
        {
            valor=true;
        }
        return valor;
    }
}