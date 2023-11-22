package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.car_wash_el_catracho.Config.id;

public class Activity_User extends AppCompatActivity {
    ImageView imagen;
    TextView Nombre,correo,pais;
    Button cerrrar,atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Nombre=(TextView) findViewById(R.id.txtnombrecliente);
        correo=(TextView) findViewById(R.id.txtcorreocliente);
        pais=(TextView) findViewById(R.id.txtPaisCliente);
        imagen=(ImageView)findViewById(R.id.imagenCliente);
        cerrrar = (Button) findViewById(R.id.btncerrar);
        atras = (Button) findViewById(R.id.btnaAceptar);

        Nombre.setText("Nombre: "+id.getNombre());
        correo.setText("Correo: "+id.getCorreo());
        pais.setText("Pais: "+id.getPais());

        cerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                startActivity(intent);
            }
        });
        revelar();
    }
    public Bitmap revelar(){
        byte[] bytes = Base64.decode(id.getFoto(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imagen.setImageBitmap(bitmap);
        return bitmap;
    }
}