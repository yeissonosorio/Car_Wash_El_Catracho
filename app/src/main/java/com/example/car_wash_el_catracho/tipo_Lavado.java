package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class tipo_Lavado extends AppCompatActivity{
    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    String longi,lat,servicio,l,lon;


    Button btnvol;

    CalendarView calendario;

    ImageButton btngps;

    Spinner lugar, hora;

    TextView tipo, titul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_lavado);

        tipo = (TextView) findViewById(R.id.tvprecio);
        titul = (TextView) findViewById(R.id.tituloL);
        lugar = (Spinner) findViewById(R.id.tipoL);
        hora = (Spinner) findViewById(R.id.HoraL);
        btngps = (ImageButton) findViewById(R.id.btnubicacion);

        calendario = (CalendarView) findViewById(R.id.calendarioL);

        long currentDate = System.currentTimeMillis();

        calendario.setMinDate(currentDate);

        btngps.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this, R.array.combo_opción, android.R.layout.simple_expandable_list_item_1);
        lugar.setAdapter(adp);

        String op = getIntent().getStringExtra("op");

        if(op!=null) {
            lugar.setSelection(Integer.parseInt(op));
        }
        else{
            lugar.setSelection(0);
        }
        ArrayAdapter<CharSequence> adpH = ArrayAdapter.createFromResource(this, R.array.Hora, android.R.layout.simple_expandable_list_item_1);
        hora.setAdapter(adpH);


        servicio = getIntent().getStringExtra("tipo");



        if (servicio.equals("Completo")) {
            titul.setText("Lavado Completo");
        } else if (servicio.equals("fuera")) {
            titul.setText("Lavado por fuera");
        }

        lugar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (servicio.equals("fuera")) {
                    if (lugar.getSelectedItemId() == 1) {
                        tipo.setText("Precio: L.100");
                        btngps.setVisibility(View.INVISIBLE);
                    } else if (lugar.getSelectedItemId() == 2) {
                        tipo.setText("Precio: L.150");
                        btngps.setVisibility(View.VISIBLE);
                    } else {
                        tipo.setText("");
                        btngps.setVisibility(View.INVISIBLE);
                    }
                } else if (servicio.equals("Completo")) {
                    if (lugar.getSelectedItemId() == 1) {
                        btngps.setVisibility(View.INVISIBLE);
                        tipo.setText("Precio: L.150");
                    } else if (lugar.getSelectedItemId() == 2) {
                        btngps.setVisibility(View.VISIBLE);
                        tipo.setText("Precio: L.200");
                    } else {
                        btngps.setVisibility(View.INVISIBLE);
                        tipo.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnvol = (Button) findViewById(R.id.btnAtras);

        btnvol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Navegacion.class);
                startActivity(intent);
            }
        });

        btngps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLocationEnabled()) {
                    Toast.makeText(getApplicationContext(), "La configuración de ubicación está desactivada, por favor actívala.", Toast.LENGTH_SHORT).show();
                } else {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                    checkLocationPermission();

                }
            }
        });
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
        } else {

            getLocation();
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {

            }
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            lat=""+latitude;
                            longi=""+longitude;

                            Toast.makeText(tipo_Lavado.this, "Abriendo Mapa",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), mapa.class);
                            intent.putExtra("lavado",servicio);
                            intent.putExtra("lat",lat);
                            intent.putExtra("lon",longi);
                            intent.putExtra("opcion",""+lugar.getSelectedItemId());
                            startActivity(intent);
                        }
                    }
                });
    }

}