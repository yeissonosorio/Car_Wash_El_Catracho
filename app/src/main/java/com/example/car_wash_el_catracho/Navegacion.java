package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navegacion extends AppCompatActivity {

    ImageButton user;


    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion);

        user = (ImageButton) findViewById(R.id.btnusuario);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_User.class);
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

}