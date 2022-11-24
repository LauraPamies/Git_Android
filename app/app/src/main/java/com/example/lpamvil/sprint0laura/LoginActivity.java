package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
    CheckBox recordarsesion;

    String ip = "172.20.10.2";

    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;


    private TextView _btn_link; //link
    String _url = "http://localhost:4000/#/recover_pass";//link


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
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();



        _btn_link = findViewById(R.id.btn_link);//link
        _btn_link.setOnClickListener(new View.OnClickListener() {//link
            @Override//link
            public void onClick(View v) {
                Uri _link = Uri.parse(_url);
                Intent i = new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });

        if (revisarSesion()) //si la sesion estaba recordada
        {
            startActivity(new Intent(this, UserArea.class));
        }


    }


    //REVISA SI LA SESIÓN ESTABA RECORDADA
    //------------------------------------------------
    //
    //  revisarSesion()
    //  --> boolean
    //------------------------------------------------
    private boolean revisarSesion() {
        return this.preferencias.getBoolean("sesionrecordada", false);
    }


    //------------------------------------------------
    //  Checked: boolean, Nombre: String, Telefono: String, Usuario: String, Mail: String -->
    //  guardarSesion()
    //
    //------------------------------------------------
    //Guarda los datos de la sesión actual (ya esté recordada o no)
    private void guardarSesion(boolean checked, String nombre, String telefono, String usuario, String mail) throws JSONException {
        editorpreferencias.putBoolean("sesionrecordada", checked);
        editorpreferencias.putString("nombre", nombre);
        editorpreferencias.putString("mail", mail);
        editorpreferencias.putString("telefono", telefono);
        editorpreferencias.putString("usuario", usuario);

        //aqui obtiene el dispositivo de la bbdd del usuario
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", usuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://" + ip + ":3000/get_one_sensor") //Poner aqui IP propia para enviar al servidor en local
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("RESPUESTAGETONESENSOR", "va");

                        try {
                            JSONObject respuesta = response.getJSONObject(0);
                            Log.d("VALOR DE OBJECT", respuesta.toString());

                            editorpreferencias.putString("tiposensor", respuesta.getString("Tipo"));
                            editorpreferencias.putString("idSensor", respuesta.getString("idSensor"));
                            editorpreferencias.putString("dispositivovinculado", respuesta.getString("Nombre"));
                            editorpreferencias.putString("idUsuario", respuesta.getString("idUsuario"));

                            editorpreferencias.apply();

                            Log.d("APLICA LAS PREFERENCIAS", "APLICA LAS PREFERENCIAS DEL SENSOR");

                            startActivity(new Intent(LoginActivity.this, UserArea.class));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTALOGINERROR", error.toString());

                        //si el usuario no tiene ningún dispositivo vinculado

                        editorpreferencias.putString("tiposensor", "");
                        editorpreferencias.putString("idSensor", "");
                        editorpreferencias.putString("dispositivovinculado", "nohay");
                        editorpreferencias.putString("idUsuario", "");

                        editorpreferencias.apply();
                        startActivity(new Intent(LoginActivity.this, AnyadirDispositivo.class));


                    }

                });


    }




    //------------------------------------------------
    //  View: view -->
    //  botonentrar()
    //
    //------------------------------------------------
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
            AndroidNetworking.post("http://" + ip + ":3000/login") //Poner aqui IP propia para enviar al servidor en local
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
                                guardarSesion(recordarsesion.isChecked(), respuesta.getString("Nombre"), respuesta.getString("Telefono"), respuesta.getString("Usuario"), respuesta.getString("mail"));



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
