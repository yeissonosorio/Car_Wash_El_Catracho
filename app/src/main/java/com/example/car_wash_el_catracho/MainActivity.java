package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int salir=1;

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


        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // Oculta el teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    public void onBackPressed() {


        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    if(salir==1){
                        super.onBackPressed();
                    }

                })
                .show();
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