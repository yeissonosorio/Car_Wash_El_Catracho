package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Notificacions;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.Servicios;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notificacion extends AppCompatActivity {
    String idt,idA,fetch,clien,hours,tot;

    TextView fecha,marca,modelo,year,precio,aceite;
    private RequestQueue requestQueue;

    Button acp,rez;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);
        fecha=(TextView)findViewById(R.id.txtfechaCotN);
        marca=(TextView)findViewById(R.id.txtMarcacotN);
        modelo=(TextView)findViewById(R.id.txtxModeloCotN);
        year=(TextView)findViewById(R.id.anioCotN);
        aceite=(TextView)findViewById(R.id.txtaceiteN);
        precio=(TextView)findViewById(R.id.PrecioN);
        idt=getIntent().getStringExtra("id");

        acp=(Button) findViewById(R.id.btnaAceptarC);
        rez=(Button) findViewById(R.id.btnaRechazarC);

        acp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendData();
            }
        });

        rez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        Recibido();
    }
    public  void Recibido() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettNotificaiconId+"?id="+idt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    Obtener(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Respuesta", error.toString());
                if (isNetworkAvailable(getApplicationContext())) {
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void Obtener(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String cliente = jsonObject.getString("id_cliente");
            String marcad = jsonObject.getString("marca");
            String modelos = jsonObject.getString("modelo");
            String anio = jsonObject.getString("anio");
            String aceit = jsonObject.getString("aceite");
            String fechah = jsonObject.getString("fecha");
            String hora = jsonObject.getString("hora");
            String precioa = jsonObject.getString("precio");
            idA=id;
            fetch=fechah;
            hours=hora;
            clien=cliente;
            tot=precioa;
            fecha.setText("Fecha y hora de reserva: "+fechah+" "+hora);
            marca.setText("Marca de auto: "+marcad);
            modelo.setText("Modelo de auto: "+modelos);
            year.setText("año del auto: "+anio);
            precio.setText("Precio del servicio: "+precioa);
            aceite.setText("Aceite del auto: L."+aceit);
        }
    }

    private void actualizar() {
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonActualizar= new JSONObject();

        try {
            jsonActualizar.put("id",idA);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, ResapiMethod.PutendpointNOti, jsonActualizar, new Response.Listener<JSONObject>() {
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
        Intent intent = new Intent(getApplicationContext(), Historial_Notificaciones.class);
        startActivity(intent);
        finish();
    }

    private void SendData() {
        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Servicios servi = new Servicios();
        servi.setId_servicio("3");
        servi.setId_cliente(clien);
        servi.setFecha(fetch);
        servi.setHora(hours);
        servi.setLatitud("0");
        servi.setLongitud("0");
        servi.setTotal(tot);

        JSONObject jsonservi = new JSONObject();

        try {
            jsonservi.put("id_cliente", servi.getId_cliente());
            jsonservi.put("id_servicio",servi.getId_servicio() );
            jsonservi.put("fecha",servi.getFecha() );
            jsonservi.put("hora", servi.getHora());
            jsonservi.put("latitud", servi.getLatitud());
            jsonservi.put("longitud", servi.getLatitud());
            jsonservi.put("total",servi.getTotal());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.EndpoitServcio, jsonservi, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Toast.makeText(getApplicationContext(), "Reservación exitosa", Toast.LENGTH_LONG).show();
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



}