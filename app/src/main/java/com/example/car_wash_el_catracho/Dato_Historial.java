package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.car_wash_el_catracho.Config.id;

public class Dato_Historial extends AppCompatActivity {

    TextView servi,correo,fecha,lugar,precio;

    Button Regresar,btnubi;
    int salir=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dato_historial);
        servi=(TextView) findViewById(R.id.txtservicT);
        correo=(TextView) findViewById(R.id.NombreT);
        fecha=(TextView) findViewById(R.id.FechaT);
        lugar=(TextView) findViewById(R.id.LugarT);
        precio=(TextView) findViewById(R.id.PrecioT);

        Regresar=(Button) findViewById(R.id.btnRegreser);
        btnubi=(Button)findViewById(R.id.Btnubi);
        btnubi.setVisibility(View.INVISIBLE);
        lugar.setVisibility(View.INVISIBLE);

        servi.setText(getIntent().getStringExtra("servis"));
        correo.setText(id.getCorreo());
        fecha.setText(date(getIntent().getStringExtra("fecha"))+" "+getIntent().getStringExtra("h"));
        if(getIntent().getStringExtra("lat").equals("0")) {
            lugar.setVisibility(View.VISIBLE);
            lugar.setText("Agencia de car wash");
        }
        else {
            btnubi.setVisibility(View.VISIBLE);
        }
        precio.setText("L."+getIntent().getStringExtra("precio"));

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                startActivity(intent);
            }
        });

        btnubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), MapaUBI.class);
                intent.putExtra("lati",getIntent().getStringExtra("lat"));
                intent.putExtra("loni",getIntent().getStringExtra("lon"));
                intent.putExtra("fe",getIntent().getStringExtra("fecha"));
                intent.putExtra("ho",getIntent().getStringExtra("hora"));
                startActivity(intent);
            }
        });

    }
    public String date(String fechaEnFormatoYMD){
        String[] partesFecha = fechaEnFormatoYMD.split("-");
        // Obtener año, mes y día por separado
        String ani = partesFecha[0];
        String ms = partesFecha[1];
        String dia = partesFecha[2];
        String newfech= dia+"/"+ms+"/"+ani;

        return newfech;
    }
    public void onBackPressed() {
        if(salir==1){
            super.onBackPressed();
        }else{
            Intent intent = new Intent(getApplicationContext(),Navegacion.class);
            startActivity(intent);
        }
    }
}