package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class tipo_Lavado extends AppCompatActivity {
    Button btnvol;

    Spinner lugar,hora;

    TextView tipo,titul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_lavado);

        tipo=(TextView) findViewById(R.id.tvprecio);
        titul = (TextView) findViewById(R.id.tituloL);
        lugar = (Spinner) findViewById(R.id.tipoL);
        hora = (Spinner) findViewById(R.id.HoraL);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,R.array.combo_opción, android.R.layout.simple_expandable_list_item_1);
        lugar.setAdapter(adp);

        ArrayAdapter<CharSequence> adpH = ArrayAdapter.createFromResource(this,R.array.Hora, android.R.layout.simple_expandable_list_item_1);
        hora.setAdapter(adpH);

        String servicio = getIntent().getStringExtra("tipo");


        if (servicio.equals("Completo")){
            titul.setText("Lavado Completo");
        } else if (servicio.equals("fuera")) {
            titul.setText("Lavado por fuera");
        }

        lugar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (servicio.equals("fuera")){
                    if(lugar.getSelectedItemId()==1) {
                        tipo.setText("Precio: L.100");
                    } else if (lugar.getSelectedItemId()==2) {
                        tipo.setText("Precio: L.150");
                    }
                    else{
                        tipo.setText("");
                    }
                } else if (servicio.equals("Completo")) {
                    if(lugar.getSelectedItemId()==1) {
                        tipo.setText("Precio: L.150");
                    } else if (lugar.getSelectedItemId()==2) {
                        tipo.setText("Precio: L.200");
                    }
                    else {
                        tipo.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnvol =(Button) findViewById(R.id.btnAtras);

        btnvol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                startActivity(intent);
            }
        });

    }
}