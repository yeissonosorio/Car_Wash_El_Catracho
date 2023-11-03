package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class password extends AppCompatActivity {

    Button cambiar;

    EditText password,verpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        cambiar = (Button) findViewById(R.id.btnCambiarContra);
        password = (EditText) findViewById(R.id.txtpassNew);
        verpassword = (EditText) findViewById(R.id.txtpassVeriNew);

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()==true) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validar() {
        boolean valor=false;

        String pass = password.getText().toString().replaceAll("\\s","");
        String val = verpassword.getText().toString().replaceAll("\\s","");

        if(val.isEmpty()&&pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (pass.isEmpty()) {
           password.setError("Debe llenar este campo");
        } else if (val.isEmpty()) {
            verpassword.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }
}