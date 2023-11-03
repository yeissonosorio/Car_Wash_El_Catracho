package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btnregis,btnentrar;

    EditText password,email;
    TextView txtolvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregis = (Button) findViewById(R.id.BtnRegistrar);
        txtolvi = (TextView) findViewById(R.id.txtolvi);
        btnentrar=(Button) findViewById(R.id.btnLoging);
        password =(EditText) findViewById(R.id.txtpasswordEn);
        email = (EditText) findViewById(R.id.txtcorreoEn);

        txtolvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recuperar.class);
                startActivity(intent);
            }
        });



        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registro.class);
                startActivity(intent);
            }
        });

        btnentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()==true) {
                    if (email.getText().toString().equals("admin")) {
                        Intent intent = new Intent(getApplicationContext(), Cotizacion.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Navegacion.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean validar() {
        boolean valor=false;

        String pass = password.getText().toString().replaceAll("\\s","");
        String em = email.getText().toString().replaceAll("\\s","");

        if(em.isEmpty()&&pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (em.isEmpty()) {
            email.setError("Debe llenar este campo");
        } else if (pass.isEmpty()) {
            password.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }
}