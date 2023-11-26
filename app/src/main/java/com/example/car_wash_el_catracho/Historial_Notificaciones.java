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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Autos;
import com.example.car_wash_el_catracho.Config.Notificacions;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Historial_Notificaciones extends AppCompatActivity {

    Button botonatras;
    ListView lista;

    ArrayList<Notificacions> noti;

    ArrayList<String> listaD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_notificaciones);
        botonatras = (Button) findViewById(R.id.btnAtrasNoti);
        lista=(ListView) findViewById(R.id.listaNot);

        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Navegacion.class);
                startActivity(intent);
            }
        });
        Lista();
    }

    public  void Lista() {
        String ID = id.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettNOTFINAL +"?estado=0&id_cliente="+ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    Obtener(response);
                    ArrayAdapter<String> adap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaD);
                    lista.setAdapter(adap);

                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String idN = noti.get(position).getId();
                            Intent intent = new Intent(getApplicationContext(),Notificacion.class);
                            intent.putExtra("id",idN);
                            startActivity(intent);
                        }
                    });

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
         noti= new ArrayList<Notificacions>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String marca= jsonObject.getString("marca");
            String modelo =jsonObject.getString("modelo");
            String fecha=jsonObject.getString("fecha");
            String hora=jsonObject.getString("hora");
            String precio=jsonObject.getString("precio");
            noti.add(new Notificacions(id,"",marca,modelo,"","",fecha,hora,precio));
        }
        FillList();
    }
    private void FillList() {
        listaD = new ArrayList<String>();

        for (int i = 0 ; i<noti.size();i++){
            listaD.add("ervicio: Cambio de aceite\nAuto:     "+noti.get(i).getMarca()+" "+noti.get(i).getModelo()+"\n" +
                    "Fecha y hora de reserva: "+noti.get(i).getFecha()+" "+noti.get(i).getHora());
        }
    }
}