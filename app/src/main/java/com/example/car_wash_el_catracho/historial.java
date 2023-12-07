package com.example.car_wash_el_catracho;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Historial;
import com.example.car_wash_el_catracho.Config.Notificacions;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class historial extends Fragment {

    private ListView list;

    private RequestQueue requestQueue;
    ArrayList<Historial> his;

    private ArrayAdapter<String> posadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_historial, container, false);

        list =(ListView) root.findViewById(R.id.Historial);

        posadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1);

        list.setAdapter(posadapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(root.getContext(),Dato_Historial.class);
                intent.putExtra("servis",his.get(position).getServicio());
                intent.putExtra("fecha",his.get(position).getFecha());
                intent.putExtra("h",his.get(position).getHora());
                intent.putExtra("lat",his.get(position).getLatitud());
                intent.putExtra("lon",his.get(position).getLongitud());
                intent.putExtra("precio",his.get(position).getToal());
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ResapiMethod.GettHistorialF+"?id_cliente="+ id.getId(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                his=new ArrayList<Historial>();
                for(int i=0; i<response.length(); i++ ){
                    try {
                        JSONObject jsonObjectRequest = response.getJSONObject(i);
                        String Servicio = jsonObjectRequest.getString("servicio");
                        String fecha = jsonObjectRequest.getString("fecha");
                        String hora = jsonObjectRequest.getString("hora");
                        String lat = jsonObjectRequest.getString("latitud");
                        String lon=  jsonObjectRequest.getString("longitud");
                        String total = jsonObjectRequest.getString("total");
                        his.add(new Historial(Servicio,fecha,hora,lat,lon,total));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                FillList();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);

        return root;
    }

    private void FillList() {

        for (int i = 0 ; i<his.size();i++){

            posadapter.add("Servicio: "+his.get(i).getServicio()+"\nFecha: "+date(his.get(i).getFecha())+" "+his.get(i).getHora());
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
}