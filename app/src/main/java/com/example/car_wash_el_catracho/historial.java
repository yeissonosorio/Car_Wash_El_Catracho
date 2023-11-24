package com.example.car_wash_el_catracho;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONObject;


public class historial extends Fragment {

    private ListView list;

    private RequestQueue requestQueue;

    private ArrayAdapter<String> posadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_historial, container, false);

        list =(ListView) root.findViewById(R.id.Historial);

        posadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1);

        list.setAdapter(posadapter);

        requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ResapiMethod.GettHistorialF+"?id_cliente="+ id.getId(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++ ){
                    try {
                        JSONObject jsonObjectRequest = response.getJSONObject(i);
                        String Servicio = jsonObjectRequest.getString("servicio");
                        String fecha = jsonObjectRequest.getString("fecha");
                        String hora = jsonObjectRequest.getString("hora");
                        String total = jsonObjectRequest.getString("total");
                        posadapter.add("Servicio: "+Servicio+"\nFecha: "+fecha+" "+hora);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);

        return root;
    }
}