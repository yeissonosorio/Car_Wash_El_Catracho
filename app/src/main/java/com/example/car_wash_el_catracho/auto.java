package com.example.car_wash_el_catracho;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class auto extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root =  inflater.inflate(R.layout.fragment_auto, container, false);

        LinearLayout agre = root.findViewById(R.id.agc);
        LinearLayout LAT = root.findViewById(R.id.lA);

        agre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),Agregar_Auto.class);

                startActivity(intent);
            }
        });

        LAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Lista_Autos.class);
                startActivity(intent);
            }
        });

       return root;
    }
}