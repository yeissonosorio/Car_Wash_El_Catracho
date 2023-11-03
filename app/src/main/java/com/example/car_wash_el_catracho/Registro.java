package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    Button crear;

    EditText nombre,apellido,correo,contra,vericontra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        crear = (Button) findViewById(R.id.btnagregarVehiculo);

        nombre = findViewById(R.id.txtnombre);
        apellido = findViewById(R.id.txtapelllido);
        correo = findViewById(R.id.txtcorreo);
        contra = findViewById(R.id.txtpassword);
        vericontra = findViewById(R.id.txtpassveri);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(veri()==true) {
                    Intent intent = new Intent(getApplicationContext(), Verificacion.class);
                    intent.putExtra("valor", "Crear");
                    startActivity(intent);
                }
            }
        });
    }

    private boolean veri() {
        boolean valor=false;

        String nom= nombre.getText().toString().replaceAll("\\s","");
        String apell =apellido.getText().toString().replaceAll("\\s","");
        String cor = correo.getText().toString().replaceAll("\\s","");
        String cont = contra.getText().toString().replaceAll("\\s","");
        String vercont = vericontra.getText().toString().replaceAll("\\s","");

        if(nom.isEmpty()&&apell.isEmpty()&&cor.isEmpty()&&cont.isEmpty()&&vercont.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (nom.isEmpty()) {
           nombre.setError("Debe llenar este campo");
        } else if (apell.isEmpty()) {
            apellido.setError("Debe llenar este campo");
        }
        else if (cor.isEmpty()) {
            correo.setError("Debe llenar este campo");
        }
        else if (cont.isEmpty()) {
            contra.setError("Debe llenar este campo");
        }
        else if (vercont.isEmpty()) {
            vericontra.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }
}