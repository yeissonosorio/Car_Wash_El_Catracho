package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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
import com.example.car_wash_el_catracho.Config.horaser;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Notificacion extends AppCompatActivity {
    //variables que se usaran en este activity
    String idt,idA,fetch,clien,hours,tot;

    TextView fecha,marca,modelo,year,precio,aceite;
    private RequestQueue requestQueue;
    int salir=0,dd,mm,yyyy,h;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                //verificar que la fecha y hora  sean menores que la fecha de reserva
                LocalDateTime fechaHoraActual = LocalDateTime.now();
                horaser OB = new horaser();
                LocalDateTime fechaHoraComparacion = LocalDateTime.of(yyyy, mm, dd,h, 0);

                if (fechaHoraActual.isBefore(fechaHoraComparacion)) {
                    //funcion de enviar el registro
                    SendData();
                } else {
                    Toast.makeText(getApplicationContext(),"Ya paso la hora de reservación",Toast.LENGTH_LONG).show();
                }
            }
        });

        rez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //funccion para actualizar el dato de la notificacion para que no aparesca
                actualizar();
            }
        });
        //Funcion para obtener la notificaciones
        Recibido();
    }
    public  void Recibido() {
        //Request para la notificacion
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettNotificaiconId+"?id="+idt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    //obtine la informacion de la base de datos
                    Obtener(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Respuesta", error.toString());
                //veririficar que halla internet
                if (isNetworkAvailable(getApplicationContext())) {
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void Obtener(String response) throws JSONException {
        //obtiene la informacion de la notificacion y la muestra en los texview
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
            fecha.setText("Fecha y hora de reserva: "+date(fechah)+" "+hora);
            marca.setText("Marca de auto: "+marcad);
            modelo.setText("Modelo de auto: "+modelos);
            year.setText("año del auto: "+anio);
            precio.setText("Precio del servicio: L."+precioa);
            aceite.setText("Aceite del auto: "+aceit);
        }
    }

    private void actualizar() {
        //request para actualizar el estado de la notificacion
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonActualizar= new JSONObject();

        try {
            //mandamos el id de la notificacion
            jsonActualizar.put("id",idA);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.PutendpointNOti, jsonActualizar, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //hace el request
        requestQueue.add(request);
        //vuelve al historial de notificaciones
        Intent intent = new Intent(getApplicationContext(), Historial_Notificaciones.class);
        startActivity(intent);
        finish();
    }

    private void SendData() {
        //enviamoe el registro con los datos
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
                    //se hace el registro y se manda a llamar la actualizacion para que cambie el estado.
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

    public String date(String fechaEnFormatoYMD){
        //cambiamos el formato de la fecha

        String[] partesFecha = fechaEnFormatoYMD.split("-");
        // Obtener año, mes y día por separado
        String ani = partesFecha[0];
        String ms = partesFecha[1];
        String dia = partesFecha[2];
        String newfech= dia+"/"+ms+"/"+ani;
        //datos usados para la comparacion de la fecha
        dd=Integer.parseInt(dia);
        mm=Integer.parseInt(ms);
        yyyy=Integer.parseInt(ani);
        String[] newhour = hours.split(":");
        h = Integer.parseInt(newhour[0]);


        return newfech;
    }
    //Si se precioan la tecla de volver atra lo que va hacer es volver a la actividad de historial de navegacion y no hace la accion del boton
    // atras

    public void onBackPressed() {
        if(salir==1){
            super.onBackPressed();
        }else{
            finish();
            Intent intent = new Intent(getApplicationContext(), Historial_Notificaciones.class);
            startActivity(intent);
        }
    }

}