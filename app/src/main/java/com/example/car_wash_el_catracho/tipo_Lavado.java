package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Autos;
import com.example.car_wash_el_catracho.Config.Lavado_UB;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.Servicios;
import com.example.car_wash_el_catracho.Config.id;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class tipo_Lavado extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    String longi,lat,servicio,l,lon;


    Button btnvol,btnreseva;

    CalendarView calendario;

    int years,mes,dia;

    ImageButton btngps;

    String fecha,h;

    Spinner lugar, hora;

    TextView tipo, titul;
    ArrayList<String> listaD;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_lavado);

        tipo = (TextView) findViewById(R.id.tvprecio);
        titul = (TextView) findViewById(R.id.tituloL);
        lugar = (Spinner) findViewById(R.id.tipoL);
        hora = (Spinner) findViewById(R.id.HoraL);
        btngps = (ImageButton) findViewById(R.id.btnubicacion);
        btnreseva =(Button) findViewById(R.id.btnReserva);
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


        lat=Lavado_UB.getLatitud();
        longi=Lavado_UB.getLongitud();

        ArrayAdapter<CharSequence> adpH = ArrayAdapter.createFromResource(this, R.array.Hora, android.R.layout.simple_expandable_list_item_1);
        hora.setAdapter(adpH);

        hora.setSelection(Lavado_UB.getHora());

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
                    Lavado_UB.setServi(1);
                    if (lugar.getSelectedItemId() == 1) {
                        lat="0";
                        lon="0";
                        Lavado_UB.setPrecio(100);
                        tipo.setText("Precio: L.100");
                        btngps.setVisibility(View.INVISIBLE);
                    } else if (lugar.getSelectedItemId() == 2) {
                        tipo.setText("Precio: L.150");
                        Lavado_UB.setPrecio(150);
                        Lavado_UB.setServi(1);
                        btngps.setVisibility(View.VISIBLE);
                    } else {
                        tipo.setText("");
                        btngps.setVisibility(View.INVISIBLE);
                    }
                } else if (servicio.equals("Completo")) {
                    Lavado_UB.setServi(2);
                    if (lugar.getSelectedItemId() == 1) {
                        lat="0";
                        lon="0";
                        Lavado_UB.setPrecio(150);
                        btngps.setVisibility(View.INVISIBLE);
                        tipo.setText("Precio: L.150");
                    } else if (lugar.getSelectedItemId() == 2) {
                        Lavado_UB.setServi(2);
                        Lavado_UB.setPrecio(200);
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

        LocalDate fech = LocalDate.now();
        years = fech.getYear();
        mes = fech.getMonthValue();
        dia = fech.getDayOfMonth();
        fecha=String.format(years + "/" + (mes) + "/" + dia);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha=String.format(year + "/" + (month + 1) + "/" + dayOfMonth);
                }
        });

        btnreseva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lugar.getSelectedItemId()!=0){
                    if(hora.getSelectedItemId()!=0){
                        entrar();
                    }else {
                        Toast.makeText(getApplicationContext(),"Seleccione una hora",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Seleccione donde se realizara el servicio",Toast.LENGTH_LONG).show();
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

    public  void entrar(){
        String ids=getIntent().getStringExtra("ids");
        h=hora.getSelectedItem().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.Gettreservavalida+
                "?servi="+ids+"&fecha="+fecha+"&hora="+h, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response.toString());
                try {
                    Obtener(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Respuesta", error.toString());
                if (isNetworkAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),"Correo o Contraeña no valido",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void Obtener(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        listaD = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String can = jsonObject.getString("cantidad");
            listaD.add(can);
        }
        int numero = Integer.parseInt(listaD.get(0).toString());
        if(numero>=4){
            Toast.makeText(getApplicationContext(),"Reservaciones agotadas para este horario",Toast.LENGTH_LONG).show();
        }
        else {
            SendData();
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

                            if(Lavado_UB.getLatitud()==null) {
                                lat=""+latitude;
                                longi=""+longitude;
                                Toast.makeText(tipo_Lavado.this, "Abriendo Mapa", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), mapa.class);
                                intent.putExtra("lavado", servicio);
                                intent.putExtra("lat", lat);
                                intent.putExtra("lon", longi);
                                intent.putExtra("opcion", "" + lugar.getSelectedItemId());
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(tipo_Lavado.this, "Abriendo Mapa", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), mapa.class);
                                intent.putExtra("lavado", servicio);
                                intent.putExtra("lat", lat);
                                intent.putExtra("lon", longi);
                                intent.putExtra("opcion", "" + lugar.getSelectedItemId());
                                startActivity(intent);
                            }
                            Lavado_UB.setHora(hora.getSelectedItemPosition());
                        }
                    }
                });
    }

    private void SendData() {
        RequestQueue requestQueue;
        h=hora.getSelectedItem().toString();
        requestQueue = Volley.newRequestQueue(this);
        Servicios servi = new Servicios();
        servi.setId_servicio(Lavado_UB.getServi()+"");
        servi.setId_cliente(id.getId());
        servi.setFecha(fecha);
        servi.setHora(h);
        servi.setLatitud(lat);
        servi.setLongitud(lon);
        servi.setTotal(Lavado_UB.getPrecio()+"");

        JSONObject jsonservi = new JSONObject();

        try {
            jsonservi.put("id_cliente", servi.getId_cliente());
            jsonservi.put("id_servicio",servi.getId_servicio() );
            jsonservi.put("fecha",servi.getFecha() );
            jsonservi.put("hora", servi.getHora());
            jsonservi.put("latitud", servi.getLatitud());
            jsonservi.put("longitud", servi.getLatitud());
            jsonservi.put("total",servi.getTotal());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.EndpoitServcio, jsonservi, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "Auto Registrado", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Falllo",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);

        Intent intent = new Intent(getApplicationContext(),Navegacion.class);
        startActivity(intent);
    }

}