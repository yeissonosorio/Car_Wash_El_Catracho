package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.ResapiMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Motor extends Fragment {

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

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.Hora, android.R.layout.simple_dropdown_item_1line);
        Hora.setAdapter(adp);

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

        rerva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar(root);
            }
        });



        return root;
    }

    public  void entrar(View root){
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
                    Toast.makeText(root.getContext(),"Correo o Contraeña no valido",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(root.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void Obtener(String response,View root) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        listaD = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String can = jsonObject.getString("cantidad");
            listaD.add(can);
        }
        int numero = Integer.parseInt(listaD.get(0).toString());
        if(numero==4){
            Toast.makeText(root.getContext(),"Reservaciones agotadas para este horario",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(root.getContext(),numero+"",Toast.LENGTH_LONG).show();
        }
    }
}