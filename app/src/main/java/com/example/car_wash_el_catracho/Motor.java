package com.example.car_wash_el_catracho;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Motor extends Fragment {

    Spinner Hora;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_motor, container, false);

        Hora = (Spinner) root.findViewById(R.id.HoraM);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.Hora, android.R.layout.simple_dropdown_item_1line);
        Hora.setAdapter(adp);

        return root;
    }
}