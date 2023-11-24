package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Autos;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lista_Autos extends AppCompatActivity {

    Button btnvol,elimianar;

    ListView listaautos;

    ArrayList<Autos> autos;

    ArrayList<String> listaD;

    String idA;

    int toque=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_autos);

        listaautos=(ListView) findViewById(R.id.misautos);

        btnvol =(Button) findViewById(R.id.btnvolverL);

        elimianar =(Button) findViewById(R.id.btneliminarCarro);

        elimianar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toque==0){
                    Toast.makeText(getApplicationContext(),"Seleccione un vehiculo",Toast.LENGTH_LONG).show();
                }
                else {
                    Alerta();
                }
            }
        });
        btnvol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                startActivity(intent);
            }
        });

        combo();

    }

    public void Alerta() {
        Delete(idA);
    }

    public  void combo() {
        String ID = id.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettAutocliente + "?id=" + ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    Obtener(response);
                    ArrayAdapter<String> adap = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listaD);
                    listaautos.setAdapter(adap);

                    listaautos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            idA = autos.get(position).getId();
                            toque = 1;
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
                    Toast.makeText(getApplicationContext(), "No hubo respuesta", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void Obtener(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        autos = new ArrayList<Autos>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String marca= jsonObject.getString("marca");
            String modelo =jsonObject.getString("modelo");
            String year= jsonObject.getString("year");
            String aceite=jsonObject.getString("aceite");
            autos.add(new Autos(id,marca,modelo,year,aceite));
        }
        FillList();
    }
    private void FillList() {
        listaD = new ArrayList<String>();

        for (int i = 0 ; i<autos.size();i++){
            listaD.add((i+1)+") Marca: "+autos.get(i).getMarca()+" Modelo: "+autos.get(i).getModelo()+"\n   " +
                    "  Año: "+autos.get(i).getYear()+" Aceite: "+autos.get(i).getAcite());
        }
    }

    public  void Delete(String id){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, ResapiMethod.DeltAutocliente+"?id="+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String mensaje = null;
                try {
                    mensaje = response.getString("eliminado");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        combo();
    }
}