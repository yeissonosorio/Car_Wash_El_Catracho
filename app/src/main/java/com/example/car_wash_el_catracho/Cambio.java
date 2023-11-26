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
import com.example.car_wash_el_catracho.Config.Autos;
import com.example.car_wash_el_catracho.Config.Cotizacion;
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

public class Cambio extends Fragment {
        CalendarView calendarView;
        Spinner Hora,carro;
        String idvehiculo;
        ArrayList<Autos> autos;

        ArrayList<String> listaD;
        int years,mes,dia;

        Button Reserva;

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
            Reserva=(Button) root.findViewById(R.id.btnReserva);

            LocalDate fech = LocalDate.now();
            years = fech.getYear();
            mes = fech.getMonthValue();
            dia = fech.getDayOfMonth();
            fecha=String.format(years + "/" + (mes) + "/" + dia);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha = String.format(year + "/" + (month + 1) + "/" + dayOfMonth);
                years=year;
                mes=(month+1);
                dia=dayOfMonth;
            }
        });

            ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.Hora, android.R.layout.simple_dropdown_item_1line);
            Hora.setAdapter(adp);

            combo(root);

        Reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Hora.getSelectedItemId()!=0) {
                    LocalDateTime fechaHoraActual = LocalDateTime.now();
                    horaser OB = new horaser();
                    int n = OB.hora((int) Hora.getSelectedItemId());
                    LocalDateTime fechaHoraComparacion = LocalDateTime.of(years, mes, dia, n, 0);

                    if (fechaHoraActual.isBefore(fechaHoraComparacion)) {
                        SendData(root);
                    } else {
                        Toast.makeText(root.getContext(), "Ya paso la hora de reservación", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(root.getContext(),"Seleccione una hora",Toast.LENGTH_LONG).show();
                }
            }
        });
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
                            idvehiculo = autos.get(position).getId().toString();
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
                    Toast.makeText(root.getContext(),"No hay autos registrados",Toast.LENGTH_LONG).show();
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

    private void SendData(View root) {
        RequestQueue requestQueue;
        h=Hora.getSelectedItem().toString();
        requestQueue = Volley.newRequestQueue(root.getContext());
        Cotizacion cot= new Cotizacion();
        cot.setId_cliente(id.getId());
        cot.setId_vehiculo(idvehiculo);
        cot.setFecha(fecha);
        cot.setHora(h);
        cot.setId_servicio("3");
        cot.setEstado("0");

        JSONObject jsonservi = new JSONObject();

        try {
            jsonservi.put("id_cliente", cot.getId_cliente());
            jsonservi.put("id_vehiculo",cot.getId_vehiculo());
            jsonservi.put("fecha",cot.getFecha());
            jsonservi.put("hora", cot.getHora());
            jsonservi.put("id_servicio", cot.getId_servicio());
            jsonservi.put("estado",cot.getEstado());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.EndpoitCotizacion, jsonservi, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(root.getContext(), "Cotización Enviada", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(root.getContext(),"Falllo",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(root.getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
        Hora.setSelection(0);
    }

}