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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Lavado_UB;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.Servicios;
import com.example.car_wash_el_catracho.Config.horaser;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Motor extends Fragment {

    //variables a usar
    CalendarView calendarView;
    Spinner Hora;

    int years,mes,dia;
    String fecha,h;
    ArrayList<String> listaD;

    Button rerva;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_motor, container, false);
        Hora = (Spinner) root.findViewById(R.id.HoraM);
        calendarView=(CalendarView) root.findViewById(R.id.calendarioL);
        rerva=(Button)root.findViewById(R.id.btnReservaM);
        //obtiene hora
        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.Hora, android.R.layout.simple_dropdown_item_1line);
        Hora.setAdapter(adp);
        //obtener obtiene la fehca actual
        LocalDate fech = LocalDate.now();
        years = fech.getYear();
        mes = fech.getMonthValue();
        dia = fech.getDayOfMonth();
        fecha=String.format(years + "/" + (mes) + "/" + dia);

        //obtiene la fecha de calendario
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha = String.format(year + "/" + (month + 1) + "/" + dayOfMonth);
                years=year;
                mes=(month+1);
                dia=dayOfMonth;
            }
        });

        rerva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificar la fecha y hora
                if(Hora.getSelectedItemId()!=0){
                    //verifica cuantas reservas hay en los registros
                    entrar(root);
                }else {
                    Toast.makeText(root.getContext(),"Seleccione una hora",Toast.LENGTH_LONG).show();
                }

            }
        });



        return root;
    }

    public  void entrar(View root){
        //obtiene la hora
        h=Hora.getSelectedItem().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(root.getContext());

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.Gettreservavalida+
                "?servi="+4+"&fecha="+fecha+"&hora="+h, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    Obtener(response,root);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Respuesta", error.toString());
                if (isNetworkAvailable(root.getContext())) {

                } else {
                    Toast.makeText(root.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Obtener(String response, View root) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        listaD = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String can = jsonObject.getString("cantidad");
            listaD.add(can);
        }
        int numero = Integer.parseInt(listaD.get(0).toString());
        if(numero>=4){
            Toast.makeText(root.getContext(),"Reservaciones agotadas para este horario",Toast.LENGTH_LONG).show();
        }
        else {
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            horaser OB = new horaser();
            int n= OB.hora((int) Hora.getSelectedItemId());
            LocalDateTime fechaHoraComparacion = LocalDateTime.of(years, mes, dia,n, 0);

            if (fechaHoraActual.isBefore(fechaHoraComparacion)) {
                SendData(root);
            } else {
                Toast.makeText(root.getContext(),"Ya paso la hora de reservación",Toast.LENGTH_LONG).show();
            }

        }
    }
    private void SendData(View root) {
        RequestQueue requestQueue;
        h=Hora.getSelectedItem().toString();
        requestQueue = Volley.newRequestQueue(root.getContext());
        Servicios servi = new Servicios();
        servi.setId_servicio("4");
        servi.setId_cliente(id.getId());
        servi.setFecha(fecha);
        servi.setHora(h);
        servi.setLatitud("0");
        servi.setLongitud("0");
        servi.setTotal("400");

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
                    Toast.makeText(root.getContext(), "Reservación exitosa", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(root.getContext(),"Falllo",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isNetworkAvailable(root.getContext())) {
                    Toast.makeText(root.getContext(),"Envio fallido",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(root.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        requestQueue.add(request);

        Hora.setSelection(0);
    }
}