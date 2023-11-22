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

import com.example.car_wash_el_catracho.Config.Lavado_UB;
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
    double latitud, longitud;

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

        Toast.makeText(getApplicationContext(),Lavado_UB.getHora()+"",Toast.LENGTH_LONG).show();

        btnEnviarcordenadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longitud == 0&&latitud==0) {
                    Toast.makeText(getApplicationContext(), "Precione su Ubicacion", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(),tipo_Lavado.class);
                    intent.putExtra("tipo",servicio);
                    intent.putExtra("op",posicion);
                    Lavado_UB.setLatitud(latitud+"");
                    Lavado_UB.setLongitud(longitud+"");
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
        double lat=Double.parseDouble(getIntent().getStringExtra("lat"));
        double logt=Double.parseDouble(getIntent().getStringExtra("lon"));
        latitud=(double) lat;
        longitud=(double) logt;
        LatLng latLng = new LatLng(lat,logt);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        latitud= (double) latLng.latitude;
        longitud= (double) latLng.longitude;

        mMap.clear();
        LatLng newposition = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(newposition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newposition));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        latitud= (double) latLng.latitude;
        longitud= (double) latLng.longitude;

        mMap.clear();
        LatLng newposition = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(newposition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newposition));
    }
}