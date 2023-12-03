package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Recuperar extends AppCompatActivity {
    Button btncorreo;
    EditText txtcorreo;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                    enviarmensaje();
                }
            }
        });

        txtcorreo.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private void enviarmensaje() {
        if(txtcorreo.getText().toString().equals("carwashelcatracho@gmail.com")){

        }else{
            mAuth.sendPasswordResetEmail(txtcorreo.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Correo de restablecimiento enviado correctamente
                                Toast.makeText(getApplicationContext(),
                                        "Hemos enviado un correo de restablecimiento de contraseña a tu correo",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Manejo de errores
                                Toast.makeText(getApplicationContext(),
                                        "Correo no registrado",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
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