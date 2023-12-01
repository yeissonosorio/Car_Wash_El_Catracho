package com.example.car_wash_el_catracho;

import static com.example.car_wash_el_catracho.MainActivity.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.car_wash_el_catracho.Config.Autos;
import com.example.car_wash_el_catracho.Config.Clientes;
import com.example.car_wash_el_catracho.Config.ResapiMethod;
import com.example.car_wash_el_catracho.Config.id;

import org.json.JSONObject;

public class Agregar_Auto extends AppCompatActivity {
    private RequestQueue requestQueue;
    EditText marca,modelo,year,aceite;

    Button agregar,atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);
        marca=(EditText) findViewById(R.id.txtmarca);
        modelo=(EditText) findViewById(R.id.txtmodelo);
        year=(EditText) findViewById(R.id.txtyea);
        aceite=(EditText) findViewById(R.id.txtaceite);

        agregar = (Button) findViewById(R.id.btnagregar);
        atras = (Button) findViewById(R.id.btnatraAgre);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()==true){
                    SendData();
                }
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),Navegacion.class);
                    startActivity(intent);
            }
        });

        aceite.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    private boolean validar() {

        boolean valor=false;

        String marc= marca.getText().toString().replaceAll("\\s","");
        String model=modelo.getText().toString().replaceAll("\\s","");
        String yyyy= year.getText().toString().replaceAll("\\s","");
        String ace= aceite.getText().toString().replaceAll("\\s","");

        if(marc.isEmpty()&&model.isEmpty()&&yyyy.isEmpty()&&ace.isEmpty()){
            Toast.makeText(getApplicationContext(),"LLene todos los campos",Toast.LENGTH_LONG).show();
        } else if (marc.isEmpty()) {
            marca.setError("No deje este campo vacio");
        } else if (model.isEmpty()) {
            modelo.setError("Debe llenar este campo");
        }
        else if (yyyy.isEmpty()) {
            year.setError("Debe llenar este campo");
        }
        else if (ace.isEmpty()) {
            aceite.setError("Debe llenar este campo");
        } else if (marca.length()>30) {
          marca.setError("Solo se acepta 30 caracteres");
        }
        else if (modelo.length()>30) {
            modelo.setError("Solo se acepta 30 caracteres");
        }
        else if (aceite.length()>30) {
            aceite.setError("Solo se acepta 30 caracteres");
        }
        else if (year.length()>4) {
            year.setError("Solo se acepta 4 caracteres");
        }
        else
        {
            valor=true;
        }
        return valor;
    }

    private void SendData() {
        requestQueue = Volley.newRequestQueue(this);
        Autos auto = new Autos();
        auto.setId(id.getId());
        auto.setMarca(marca.getText().toString());
        auto.setModelo(modelo.getText().toString());
        auto.setYear(year.getText().toString());
        auto.setAcite(aceite.getText().toString());

        JSONObject jsonAuto = new JSONObject();

        try {
            jsonAuto.put("marca", auto.getMarca());
            jsonAuto.put("modelo", auto.getModelo());
            jsonAuto.put("year", auto.getYear());
            jsonAuto.put("aceite", auto.getAcite());
            jsonAuto.put("id_cliente", auto.getId());
            jsonAuto.put("estado","0");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ResapiMethod.EndpoitpostAuto, jsonAuto, new Response.Listener<JSONObject>() {
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
                if (isNetworkAvailable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(),"Envio fallido",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(request);

        Intent intent = new Intent(getApplicationContext(),Navegacion.class);
        startActivity(intent);
    }
}