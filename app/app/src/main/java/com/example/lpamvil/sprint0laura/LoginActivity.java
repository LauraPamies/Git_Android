package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    String nombreUs = "";
    String usuarioNom = "";

    CheckBox recordarsesion;

    JSONObject object;


    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());
        user = (EditText) findViewById(R.id.edittextuser);
        pass = (EditText) findViewById(R.id.edittextpass);
        recordarsesion = (CheckBox) findViewById(R.id.recordarsesioncheck);


        //Creación de preferencias
        //Guarda las preferencias compartidas en unas llamadas "sesiones"
        preferencias = this.getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();

        if (revisarSesion()) //si la sesion estaba recordada
        {
            startActivity(new Intent(this, UserArea.class));
        }


    }


    //REVISA SI LA SESIÓN ESTABA RECORDADA
    private boolean revisarSesion() {
        return this.preferencias.getBoolean("sesionrecordada", false);
    }


    //Guarda los datos de la sesión actual (ya esté recordada o no)
    private void guardarSesion(boolean checked,String nombre,String telefono,String usuario, String mail) {
        editorpreferencias.putBoolean("sesionrecordada", checked);
        editorpreferencias.putString("nombre",nombre);
        editorpreferencias.putString("mail",mail);
        editorpreferencias.putString("telefono",telefono);
        editorpreferencias.putString("usuario",usuario);
        editorpreferencias.apply();
    }

    public void botonentrar(View view) throws JSONException {
        String u = user.getText().toString();
        String p = pass.getText().toString();


        if (u.equals("") && p.equals("")) {
            Toast.makeText(this, "ERROR: Campos vacíos", Toast.LENGTH_LONG).show();
        } else if (u.equals("")) {
            Toast.makeText(this, "ERROR: Campo usuario vacío", Toast.LENGTH_LONG).show();
        } else if (p.equals("")) {
            Toast.makeText(this, "ERROR: Campo contraseña vacío", Toast.LENGTH_LONG).show();
        } else {


            Log.d("RESPUESTALOGIN ", "entra al userarea");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", u);
                jsonObject.put("password", p);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //El método de la api para hacer el post
            AndroidNetworking.post("http://192.168.0.14:3000/login") //Poner aqui IP propia para enviar al servidor en local
                    .addJSONObjectBody(jsonObject) // posting json
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // do anything with response
                            Log.d("RESPUESTALOGIN", "va");

                            try {
                                JSONObject respuesta = response.getJSONObject(0);
                                Log.d("VALOR DE OBJECT", respuesta.toString());

                                //guarda los datos de la sesión con los datos que recibe del post
                                guardarSesion(recordarsesion.isChecked(), respuesta.getString("nombre"), respuesta.getString("telefono"), respuesta.getString("usuario"), respuesta.getString("mail"));

                                startActivity(new Intent(LoginActivity.this, UserArea.class));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("RESPUESTALOGINERROR", error.toString());
                            Toast.makeText(LoginActivity.this, "ERROR: usuario/contraseña incorrectos", Toast.LENGTH_LONG).show();

                        }

                    });

        }
    }
}
