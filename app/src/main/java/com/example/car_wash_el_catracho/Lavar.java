package com.example.car_wash_el_catracho;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Lavar extends Fragment {

    Button btnopcion1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_lavar, container, false);
        LinearLayout completo = root.findViewById(R.id.completo);
        LinearLayout fuera = root.findViewById(R.id.Fuera);

        completo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),tipo_Lavado.class);
                intent.putExtra("tipo","Completo");
                intent.putExtra("ids","2");
                startActivity(intent);
            }
        });

        fuera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),tipo_Lavado.class);
                intent.putExtra("tipo","fuera");
                intent.putExtra("ids","1");
                startActivity(intent);
            }
        });

        return root;



    }
}