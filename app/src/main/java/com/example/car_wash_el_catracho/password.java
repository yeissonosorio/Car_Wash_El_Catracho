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

        verpassword.setOnEditorActionListener((v, actionId, event) -> {
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