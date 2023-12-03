package com.example.car_wash_el_catracho;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    int salir=0;

    private SharedPreferences sharedPreferences;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    Button btnregis,btnentrar;

    EditText password,email;
    TextView txtolvi;
    String token;

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
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Inicia sesion
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        if (user != null && user.isEmailVerified()) {
                                            entrar();
                                        } else {
                                            // El correo electrónico no está verificado
                                            Toast.makeText(MainActivity.this, "Por favor, verifica tu correo electrónico", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // El inicio de sesión falló, manejar el error aquí
                                        Toast.makeText(MainActivity.this, "Correo o contraseña no valido", Toast.LENGTH_SHORT).show();
                                        Log.e("LoginError", task.getException().getMessage());
                                    }
                                }
                            });
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
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast

                        Log.d("token", token);
                    }
                });

        checkAndShowNotificationSettings();

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
                "?correo="+email.getText(), new Response.Listener<String>() {
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
            if(listaD.get(2).toString().equals("carwashelcatracho@gmail.com")){
                sharedPreferences = getSharedPreferences("Usuario",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("correo",listaD.get(2).toString());
                editor.apply();
                Intent intent = new Intent(getApplicationContext(),Cotizacion.class);
                startActivity(intent);
            }else {
                id.setId(listaD.get(0).toString());
                id.setNombre(listaD.get(1).toString());
                id.setCorreo(listaD.get(2).toString());
                id.setPais(listaD.get(3).toString());
                id.setFoto(listaD.get(4).toString());
                id.setToken(token);

                sharedPreferences = getSharedPreferences("Usuario",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",id.getId());
                editor.putString("nombre",id.getNombre());
                editor.putString("correo",id.getCorreo());
                editor.putString("pais",id.getPais());
                editor.putString("foto",id.getFoto());
                editor.putString("estado","0");
                editor.putString("token",token);
                editor.apply();

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

    private boolean areNotificationsEnabled() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Para Android Oreo y superior, verificamos la configuración de los canales de notificación
            return notificationManager.getImportance() != NotificationManager.IMPORTANCE_NONE;
        } else {
            // Para versiones anteriores a Oreo, verificamos si están habilitadas las notificaciones
            return Settings.Secure.getInt(
                    getContentResolver(),
                    "notification_enabled",
                    0
            ) == 1;
        }
    }

    // Esta función muestra la ventana emergente para activar las notificaciones
    private void showNotificationSettings() {
        if (!areNotificationsEnabled()) {
            Toast.makeText(this, "Las notificaciones están desactivadas", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            } else {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "Las notificaciones están activadas", Toast.LENGTH_SHORT).show();
        }
    }

    // Llamar a esta función para mostrar la ventana emergente de configuración de notificaciones
    private void checkAndShowNotificationSettings() {
        showNotificationSettings();
    }


}