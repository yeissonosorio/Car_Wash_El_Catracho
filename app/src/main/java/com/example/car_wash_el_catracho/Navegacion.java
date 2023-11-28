package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.horaser;
import com.example.car_wash_el_catracho.Config.id;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Navegacion extends AppCompatActivity {

    int salir=0;
    ArrayList<String> listaD;
    ImageView outnoti,user,botnoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion);
        user = (ImageView) findViewById(R.id.btnusuario);
        botnoti = (ImageView) findViewById(R.id.btnnoti);
        outnoti = (ImageView) findViewById(R.id.outnoti);
        outnoti.setVisibility(View.INVISIBLE);
        outnoti.setVisibility(View.INVISIBLE);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_User.class);
                startActivity(intent);
            }
        });

        botnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Historial_Notificaciones.class);
                intent.putExtra("valor","Crear");
                startActivity(intent);
            }
        });
        boton();
        entrar();
        revelar();
    }

    private void boton() {

        BottomNavigationView Navegacion = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostfragmet);
        NavigationUI.setupWithNavController(Navegacion,navHostFragment.getNavController());
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Seguro que quieres salir de la aplicación?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    finishAffinity();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    if(salir==1){
                        super.onBackPressed();
                    }

                })
                .show();
    }

    public Bitmap revelar(){
        byte[] bytes = Base64.decode(id.getFoto(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        user.setImageBitmap(bitmap);
        return bitmap;
    }

    public  void entrar(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GettNotiNumeor+"?id="+id.getId(), new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }


            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Obtener(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        listaD = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String can = jsonObject.getString("numero");
            listaD.add(can);
        }
        int numero = Integer.parseInt(listaD.get(0).toString());
        if(numero>0){
            botnoti.setVisibility(View.VISIBLE);
            outnoti.setVisibility(View.VISIBLE);
        }
        else {
            botnoti.setVisibility(View.INVISIBLE);
            outnoti.setVisibility(View.INVISIBLE);
        }
    }
}