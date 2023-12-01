package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.appcompat.app.AlertDialog;
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
import com.example.car_wash_el_catracho.Config.DatosCot;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cotizacion extends AppCompatActivity {
    private int salir=0;
    Button btncerrar;
    ListView lista;
    ArrayList<String> listaD;

    ArrayList<DatosCot> dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizacion);
        btncerrar = (Button) findViewById(R.id.btncerrarcot);
        lista =(ListView) findViewById(R.id.ListaCot);

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        lsita();
    }

    public  void lsita() {
        String ID = id.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettpointCotizacion, new Response.Listener<String>() {
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
                            Intent intent = new Intent(getApplicationContext(),Informe_admin_cot.class);
                            intent.putExtra("id",dato.get(position).getId());
                            intent.putExtra("idc",dato.get(position).getIdcliente());
                            intent.putExtra("correo",dato.get(position).getCorreo());
                            intent.putExtra("idv",dato.get(position).getIdvehiculo());
                            intent.putExtra("marca",dato.get(position).getMarca());
                            intent.putExtra("modelo",dato.get(position).getModelo());
                            intent.putExtra("anio",dato.get(position).getYear());
                            intent.putExtra("aceite",dato.get(position).getAceite());
                            intent.putExtra("fecha",dato.get(position).getFecha());
                            intent.putExtra("hora",dato.get(position).getHora());
                            startActivity(intent);
                            finish();
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
                    Toast.makeText(getApplicationContext(),"Envio fallido",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void Obtener(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        dato = new ArrayList<DatosCot>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String idcliente=jsonObject.getString("idcliente");
            String Correo =jsonObject.getString("Correo");
            String idvehiculo=jsonObject.getString("idvehiculo");
            String marca= jsonObject.getString("marca");
            String modelo =jsonObject.getString("modelo");
            String year= jsonObject.getString("anio");
            String aceite=jsonObject.getString("Aceite");
            String fecha=jsonObject.getString("fecha");
            String hora=jsonObject.getString("hora");
            String servi=jsonObject.getString("servicio");
            dato.add(new DatosCot(id,idcliente,Correo,idvehiculo,marca,modelo,year,aceite,fecha,hora,servi));
        }
        FillList();
    }
    private void FillList() {
        listaD = new ArrayList<String>();

        for (int i = 0 ; i<dato.size();i++){
            listaD.add("id: "+dato.get(i).getId()+" Correo: "+dato.get(i).getCorreo()+"\nfecha y hora de reserva: "+date(dato.get(i).getFecha())+" "+dato.get(i).getHora());
        }
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


        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    finishAffinity();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    if(salir==1){
                        super.onBackPressed();
                    }

                })
                .show();
    }
}