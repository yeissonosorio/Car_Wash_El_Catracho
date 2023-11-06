package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapa extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private FusedLocationProviderClient fusedLocationClient;
    long latitud, longitud;

    GoogleMap mMap;

    Button btnEnviarcordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment.getMapAsync(this);

        btnEnviarcordenadas = (Button) findViewById(R.id.btnEnviarcordenadas);

        String servicio = getIntent().getStringExtra("lavado");
        String posicion = getIntent().getStringExtra("opcion");


        btnEnviarcordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longitud == 0&&latitud==0) {
                    Toast.makeText(getApplicationContext(), "Precione su Ubicacion", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(),tipo_Lavado.class);
                    intent.putExtra("tipo",servicio);
                    intent.putExtra("op",posicion);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);
        LatLng latLng = new LatLng(0,0);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        latitud= (long) latLng.latitude;
        longitud= (long) latLng.longitude;

        mMap.clear();
        LatLng newposition = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(newposition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newposition));
    }
    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        latitud= (long) latLng.latitude;
        longitud= (long) latLng.longitude;

        mMap.clear();
        LatLng newposition = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(newposition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newposition));
    }
}