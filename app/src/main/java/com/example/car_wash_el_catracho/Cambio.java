package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Autos;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cambio extends Fragment {
        CalendarView calendarView;
        Spinner Hora,carro;
        Autos auton;
        ArrayList<Autos> autos;

        ArrayList<String> listaD;
        int years,mes,dia;

        String fecha,h;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View root= inflater.inflate(R.layout.fragment_cambio, container, false);
            Hora = (Spinner) root.findViewById(R.id.HoraLC);
            calendarView=(CalendarView) root.findViewById(R.id.calendarioCam);
            carro=(Spinner) root.findViewById(R.id.CarroCam);
            LocalDate fech = LocalDate.now();
            years = fech.getYear();
            mes = fech.getMonthValue();
            dia = fech.getDayOfMonth();
            fecha=String.format(years + "/" + (mes) + "/" + dia);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha = String.format(year + "/" + (month + 1) + "/" + dayOfMonth);
                Toast.makeText(root.getContext(),fecha+"",Toast.LENGTH_LONG).show();
            }
        });

            ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.Hora, android.R.layout.simple_dropdown_item_1line);
            Hora.setAdapter(adp);

            combo(root);


        return root;
    }

    public  void combo(View root){
        String ID= id.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettAutocliente+"?id="+ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    Obtener(response);
                    ArrayAdapter<String>adap=new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_item,listaD);
                    carro.setAdapter(adap);

                    carro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(root.getContext(),autos.get(position).getId().toString()+"",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Respuesta", error.toString());
                if (isNetworkAvailable(root.getContext())) {
                    Toast.makeText(root.getContext(),"No hubo respuesta",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(root.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
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
            autos.add(new Autos(id,marca,modelo,"",""));
        }
        FillList();
    }
    private void FillList() {
        listaD = new ArrayList<String>();

        for (int i = 0 ; i<autos.size();i++){
            listaD.add(autos.get(i).getMarca()+" "+autos.get(i).getModelo());
        }
    }
}