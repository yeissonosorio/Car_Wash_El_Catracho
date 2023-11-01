package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Agregarveichulo extends AppCompatActivity {
    Button btnvol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarveichulo);

        btnvol =(Button) findViewById(R.id.btnmenuV);

        btnvol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                startActivity(intent);
            }
        });
    }
}