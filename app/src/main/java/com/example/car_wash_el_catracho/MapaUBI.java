package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.car_wash_el_catracho.Config.Lavado_UB;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaUBI extends AppCompatActivity implements OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationClient;
    GoogleMap mMap;
    double lat,logt;
    int salir=0;
    Button sal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ubi);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaUI);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sal=(Button)findViewById(R.id.btnVolverMapa);
        mapFragment.getMapAsync(this);

        sal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        lat=Double.parseDouble(getIntent().getStringExtra("lati"));
        logt=Double.parseDouble(getIntent().getStringExtra("loni"));
        LatLng latLng = new LatLng(lat,logt);
        mMap.addMarker(new MarkerOptions().position(latLng).title(getIntent().getStringExtra("nombre")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
    public void onBackPressed() {
            super.onBackPressed();
    }
}