package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.CotNot;
import com.example.car_wash_el_catracho.Config.Lavado_UB;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.Servicios;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONObject;

public class Informe_admin_cot extends AppCompatActivity {

    TextView idcot,correo,fecha, marca,Modelo,Auto,aceite;
    String  id,idcli,correoD,idve,marcad,modelod,anio,acieted,fechad,horad;

    Button atras,enviar,rechazar;

    EditText precio;

    int salir=0;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_admin_cot);
        idcot = (TextView) findViewById(R.id.idCot);
        precio=(EditText) findViewById(R.id.txtprecioCot);
        correo = (TextView) findViewById(R.id.NombreclientCot);
        fecha = (TextView) findViewById(R.id.txtfechaH);
        marca = (TextView) findViewById(R.id.txtmarcaCot);
        Modelo = (TextView) findViewById(R.id.txtModeloCot);
        Auto = (TextView) findViewById(R.id.year);
        aceite = (TextView) findViewById(R.id.txtAceite);

        atras =(Button)findViewById(R.id.btnAtrascot);
        rechazar=(Button)findViewById(R.id.btnRechas);
        enviar=(Button)findViewById(R.id.btnEnviarCot);

        id= getIntent().getStringExtra("id");
        idcli= getIntent().getStringExtra("idc");
        correoD= getIntent().getStringExtra("correo");
        idve= getIntent().getStringExtra("idv");
        marcad= getIntent().getStringExtra("marca");
        modelod= getIntent().getStringExtra("modelo");
        anio= getIntent().getStringExtra("anio");
        acieted= getIntent().getStringExtra("aceite");
        fechad= getIntent().getStringExtra("fecha");
        horad= getIntent().getStringExtra("hora");

        idcot.setText("Id de cotización: "+id);
        correo.setText("Correo: "+correoD);
        fecha.setText("Fecha y hora de Reserva: "+fechad+" "+horad);
        marca.setText("Marca: "+marcad);
        Modelo.setText("Modelo: "+modelod);
        Auto.setText("Año: "+anio);
        aceite.setText("Tipo de Aceite: "+acieted);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Cotizacion.class);
                startActivity(intent);
                finish();
            }
        });

        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(precio.getText().toString().isEmpty()){
                    precio.setError("No deje este campo vacio");
                }
                else {
                    SendData();
                }
            }
        });
    }

    private void actualizar() {

        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonActualizar= new JSONObject();

        try {
            jsonActualizar.put("id",id);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ResapiMethod.PutendpointCotizacion, jsonActualizar, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String mensaje = response.getString("Coización Rechazada");
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

        Intent intent = new Intent(getApplicationContext(),Cotizacion.class);
        startActivity(intent);
        finish();
    }

    private void SendData() {
        requestQueue = Volley.newRequestQueue(this);

        CotNot cotAd= new CotNot();

        cotAd.setId_cot(id);
        cotAd.setPrecio(precio.getText().toString());
        cotAd.setId_cliente(idcli);
        cotAd.setId_auto(idve);

        JSONObject jsoncot = new JSONObject();

        try {
            jsoncot.put("id_cotizacion", cotAd.getId_cot());
            jsoncot.put("precio",cotAd.getPrecio());
            jsoncot.put("estado","0");
            jsoncot.put("id_cliente", cotAd.getId_cliente());
            jsoncot.put("id_auto", cotAd.getId_auto());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.Endpoitpostnot, jsoncot, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "Respuesta enviada", Toast.LENGTH_LONG).show();
                    actualizar();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Falllo",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);


    }

    public void onBackPressed() {

                    if(salir==1){
                        super.onBackPressed();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(),Cotizacion.class);
                        startActivity(intent);
                        finish();
                    }

    }
}