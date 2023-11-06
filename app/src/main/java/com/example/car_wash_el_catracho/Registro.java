package com.example.car_wash_el_catracho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class Registro extends AppCompatActivity {

    static final int peticion_acceso_camara = 101;
    static final int peticion_toma_fotografica = 102;



    Bitmap imagenb;

    ImageView imageView;

    Button crear;

    ImageButton imagen;

    EditText nombre,apellido,correo,contra,vericontra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        crear = (Button) findViewById(R.id.btnagregarVehiculo);

        nombre = findViewById(R.id.txtnombre);
        apellido = findViewById(R.id.txtapelllido);
        correo = findViewById(R.id.txtcorreo);
        contra = findViewById(R.id.txtpassword);
        vericontra = findViewById(R.id.txtpassveri);
        imagen = (ImageButton) findViewById(R.id.btnagraimagenR);
        imageView = (ImageView) findViewById(R.id.imageView2);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(veri()==true) {
                    Intent intent = new Intent(getApplicationContext(), Verificacion.class);
                    intent.putExtra("valor", "Crear");
                    startActivity(intent);
                }
            }
        });

        vericontra.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // Oculta el teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opciones();
            }
        });
    }

    private void opciones() {
        final CharSequence[] opciones = {"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(Registro.this);
        alertDialog.setTitle("Seleccione un Opcion");
        alertDialog.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Tomar Foto")){
                    permisos();
                } else if (opciones[which].equals("Cargar Imagen")) {
                    CargarImagen();
                }
                else {

                }
            }
        });

        alertDialog.show();
    }

    private void CargarImagen() {
        Intent  intent =new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    private void permisos() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},peticion_acceso_camara);
        }
        else
        {
            TomarFoto();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                TomarFoto();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void TomarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intent, peticion_toma_fotografica);
        }
    }

    protected void onActivityResult(int requescode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requescode, resultCode, data);

        if (requescode==peticion_toma_fotografica && resultCode== RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            imageView.setImageBitmap(image);
        } else if (resultCode==RESULT_OK) {
            Uri path=data.getData();
            imageView.setImageURI(path);
        }
    }


    private boolean veri() {
        boolean valor=false;

        String nom= nombre.getText().toString().replaceAll("\\s","");
        String apell =apellido.getText().toString().replaceAll("\\s","");
        String cor = correo.getText().toString().replaceAll("\\s","");
        String cont = contra.getText().toString().replaceAll("\\s","");
        String vercont = vericontra.getText().toString().replaceAll("\\s","");

        if(nom.isEmpty()&&apell.isEmpty()&&cor.isEmpty()&&cont.isEmpty()&&vercont.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (nom.isEmpty()) {
           nombre.setError("Debe llenar este campo");
        } else if (apell.isEmpty()) {
            apellido.setError("Debe llenar este campo");
        }
        else if (cor.isEmpty()) {
            correo.setError("Debe llenar este campo");
        }
        else if (cont.isEmpty()) {
            contra.setError("Debe llenar este campo");
        }
        else if (vercont.isEmpty()) {
            vericontra.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }
    
}