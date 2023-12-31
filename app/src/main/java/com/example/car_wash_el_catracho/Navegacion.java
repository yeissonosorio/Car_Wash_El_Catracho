package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
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
    //variables
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


        //los lleva a la acividad con la informacion de los usarios
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_User.class);
                startActivity(intent);
            }
        });

        //los lleva al historial de notificaciones
        botnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Historial_Notificaciones.class);
                intent.putExtra("valor","Crear");
                startActivity(intent);
            }
        });
        //se usas para desplazar los fragments
        boton();
        //Muestar la imagen del usuario
        revelar();
    }

    private void boton() {
        //Utilizamoes este codigo para hacer que nuestro activity al precionar los botones de navegacion valla caviando los framents
        BottomNavigationView Navegacion = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_hostfragmet);
        NavigationUI.setupWithNavController(Navegacion,navHostFragment.getNavController());
    }
    //funcion que si sepreciona el boton de atras hace que se cierre la app
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
        //convierte la imagen del usario Bitmap
        byte[] bytes = Base64.decode(id.getFoto(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        user.setImageBitmap(bitmap);
        return bitmap;
    }
}