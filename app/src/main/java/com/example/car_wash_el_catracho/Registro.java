package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Context;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Clientes;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Registro extends AppCompatActivity {

    static final int peticion_acceso_camara = 101;
    static final int peticion_toma_fotografica = 102;

    private RequestQueue requestQueue;

    ImageView imageView;

    Button crear;

    String idv="";

    ImageButton imagen;

    Spinner pais;

    EditText nombre,apellido,correo,contra,vericontra;

    Bitmap image;

    String imagenconver="";


    ArrayList<String> listaD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        crear = (Button) findViewById(R.id.btnagregarVehiculo);

        nombre = findViewById(R.id.txtnombre);
        pais=(Spinner) findViewById(R.id.spinerpais);
        apellido = findViewById(R.id.txtapelllido);
        correo = findViewById(R.id.txtcorreo);
        contra = findViewById(R.id.txtpassword);
        vericontra = findViewById(R.id.txtpassveri);
        imagen = (ImageButton) findViewById(R.id.btnagraimagenR);
        imageView = (ImageView) findViewById(R.id.imageView2);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this, R.array.pais, android.R.layout.simple_dropdown_item_1line);
        pais.setAdapter(adp);


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(veri()==true) {
                    validar(correo.getText().toString());
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

    private void SendData() {
        requestQueue = Volley.newRequestQueue(this);
        Clientes clientes = new Clientes();
        clientes.setNombre(nombre.getText().toString());
        clientes.setApellido(apellido.getText().toString());
        clientes.setCorreo(correo.getText().toString());
        clientes.setPais(pais.getSelectedItemId() + "");
        clientes.setPassword(contra.getText().toString());
        clientes.setFoto(imagenconver);

        JSONObject jsonCliente = new JSONObject();

        try {
            jsonCliente.put("nombre", clientes.getNombre());
            jsonCliente.put("apellido", clientes.getApellido());
            jsonCliente.put("correo", clientes.getCorreo());
            jsonCliente.put("pais", clientes.getPais());
            jsonCliente.put("password", clientes.getPassword());
            jsonCliente.put("foto", clientes.getFoto());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.EndpoitpostCliente, jsonCliente, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getApplicationContext(), "Usuario Guardado", Toast.LENGTH_LONG).show();
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
            image = (Bitmap) extras.get("data");
            imageView.setImageBitmap(image);
        } else if (resultCode==RESULT_OK) {
            Uri path=data.getData();
            Context context=getApplicationContext();
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(path);
                image = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imagearray = byteArrayOutputStream.toByteArray();
        imagenconver = Base64.encodeToString(imagearray, Base64.DEFAULT);
    }



    private boolean veri() {
        boolean valor=false;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String nom= nombre.getText().toString().replaceAll("\\s","");
        String apell =apellido.getText().toString().replaceAll("\\s","");
        String cor = correo.getText().toString().replaceAll("\\s","");
        String cont = contra.getText().toString().replaceAll("\\s","");
        String vercont = vericontra.getText().toString().replaceAll("\\s","");

        if(nom.isEmpty()&&apell.isEmpty()&&cor.isEmpty()&&cont.isEmpty()&&vercont.isEmpty()&&pais.getSelectedItemId()==0){
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
        else if (pais.getSelectedItemId()==0){
            Toast.makeText(getApplicationContext(),"Selccione un país",Toast.LENGTH_LONG).show();
        }  else if (imagenconver.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Selccione una imagen",Toast.LENGTH_LONG).show();
        } else if (nombre.length()>15) {
            nombre.setError("El nombre no debe se mayor a 15 caracters");
        }
        else if (apellido.length()>15) {
            apellido.setError("El Apellido no debe se mayor a 15 caracters");
        }
        else if (correo.length()>50) {
            correo.setError("El correo no debe se mayor a 50 caracters");
        }
        else if (contra.length()<10) {
            contra.setError("La contraseña debe llevar al menos 10 caracters");
        }
        else if (contra.length()>30) {
            nombre.setError("La contraseña no debe se mayor a 30 caracters");
        }
        else {
            if (!correo.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(correo.getText().toString()).matches()) {
                if(cont.equals(vercont)) {
                    valor = true;
                }else {
                    vericontra.setError("La contraseña no coincide");
                }
            }
            else {
                correo.setError("No es un correo");
            }

        }
        return valor;
    }

    public void validar(String correo){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GetVerficacion+"?correo="+correo, new Response.Listener<String>() {
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
                    SendData();
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
            String id = jsonObject.getString("id");
            listaD.add(id);
        }



        if(listaD.get(0).toString().equals("")){

        }
        else {
            correo.setError("Este correo ya existe");
        }

    }

}