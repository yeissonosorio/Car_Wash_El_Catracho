package com.example.car_wash_el_catracho;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    int salir=0;

    Button btnregis,btnentrar;

    EditText password,email;
    TextView txtolvi;

    ArrayList<String> listaD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregis = (Button) findViewById(R.id.BtnRegistrar);
        txtolvi = (TextView) findViewById(R.id.txtolvi);
        btnentrar=(Button) findViewById(R.id.btnLoging);
        password =(EditText) findViewById(R.id.txtpasswordEn);
        email = (EditText) findViewById(R.id.txtcorreoEn);

        txtolvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recuperar.class);
                startActivity(intent);
            }
        });



        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Registro.class);
                startActivity(intent);
            }
        });

        btnentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (validar()!=false) {
                        entrar();
                    }
            }
        });


        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // Oculta el teclado
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
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

    public  void entrar(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, ResapiMethod.GetClienteF+
                "?correo="+email.getText()+"&pass="+password.getText(), new Response.Listener<String>() {
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
                String id = jsonObject.getString("id");
                String nombres= jsonObject.getString("nombre");
                String apellidos =jsonObject.getString("apellido");
                String correos= jsonObject.getString("correo");
                String Pais =jsonObject.getString("pais");
                String fot= jsonObject.getString("foto");
                listaD.add(id);
                listaD.add(nombres+" "+apellidos);
                listaD.add(correos);
                listaD.add(Pais);
                listaD.add(fot);
            }
            if(listaD.get(2).toString().equals("AdminCarwash@gmail.com")){
                Intent intent = new Intent(getApplicationContext(),Cotizacion.class);
                startActivity(intent);
            }else {
                id.setId(listaD.get(0).toString());
                id.setNombre(listaD.get(1).toString());
                id.setCorreo(listaD.get(2).toString());
                id.setPais(listaD.get(3).toString());
                id.setFoto(listaD.get(4).toString());
                Intent intent = new Intent(getApplicationContext(), Navegacion.class);
                startActivity(intent);
            }


    }

    private boolean validar() {
        boolean valor=false;

        String pass = password.getText().toString().replaceAll("\\s","");
        String em = email.getText().toString().replaceAll("\\s","");

        if(em.isEmpty()&&pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (em.isEmpty()) {
            email.setError("Debe llenar este campo");
        } else if (pass.isEmpty()) {
            password.setError("Debe llenar este campo");
        }
        else {
            valor=true;
        }
        return valor;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }

}