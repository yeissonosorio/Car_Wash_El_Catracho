package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navegacion extends AppCompatActivity {

    ImageButton user,botnoti;

    int salir=0;

    ImageView outnoti;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion);

        user = (ImageButton) findViewById(R.id.btnusuario);
        botnoti = (ImageButton) findViewById(R.id.btnnoti);
        outnoti = (ImageView) findViewById(R.id.outnoti);

        outnoti.setVisibility(View.VISIBLE);


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_User.class);
                intent.putExtra("valor","Crear");
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

}