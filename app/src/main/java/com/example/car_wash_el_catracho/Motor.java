package com.example.car_wash_el_catracho;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;

public class Motor extends Fragment {

    CalendarView calendarView;
    Spinner Hora;

    int years,mes,dia;
    String fecha;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_motor, container, false);
        Hora = (Spinner) root.findViewById(R.id.HoraM);
        calendarView=(CalendarView) root.findViewById(R.id.calendarioL);

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

        return root;
    }
}