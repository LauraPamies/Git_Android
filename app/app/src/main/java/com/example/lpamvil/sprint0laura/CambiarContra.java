package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CambiarContra extends AppCompatActivity {


    EditText editcontraantigua, editcontranueva, editcontranuevarepetir;

    String oldpass, newpass, newpass2;


    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contra);

        Log.d("CAMBIAR CONTRA","ENTRA EN CAMBIAR CONTRA");
        //ASIGNACIÓN DE ELEMENTOS DE LA VIEW
        editcontraantigua = (EditText)findViewById(R.id.editantiguacontra);
        editcontranueva = (EditText)findViewById(R.id.editnuevacontra);
        editcontranuevarepetir = (EditText)findViewById(R.id.editnuevacontra2);


        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();



    }


    public void botonguardar(View view)throws JSONException
    {
        oldpass = editcontraantigua.getText().toString();
        newpass = editcontranueva.getText().toString();
        newpass2 = editcontranuevarepetir.getText().toString();

        Log.d("OLDPASS", "botonguardar: " + oldpass + " " + newpass + " " + newpass2);
        if (newpass.equals(newpass2))   //SI AMBAS CONTRASEÑAS NUEVAS SON IGUALES
        {
            //Logica logica = new Logica();

            //JSONObject result = logica.cambiarcontra(oldpass,newpass,preferencias.getString("usuario","prueba"));

            //Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();

            //Toast.makeText(this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();

            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("usuario",preferencias.getString("usuario","prueba"));
                jsonObject.put("oldpass",oldpass);
                jsonObject.put("newpass",newpass);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post("http://192.168.0.14:3000/change_password")
                    .addJSONObjectBody(jsonObject) // posting json
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response

                            Log.d("RESPUESTAEDIT", "va editar");
                            Log.d("RESPUESTA ONRESPONSE: ", response.toString());
                            //Toast.makeText(CambiarContra.this, response.toString(), Toast.LENGTH_LONG).show();

                            try {
                                if (response.getString("code").equals("666")) //contraseña incorrecta
                                {
                                    Log.d("RESULTADOCAMBIARCONTRA" , "codigo : " + response.getString("code"));
                                    Toast.makeText(CambiarContra.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                                }
                                else if (response.getString("code").equals("504"))   //contraseñas iguales
                                {
                                    Log.d("RESULTADOCAMBIARCONTRA" , "codigo : " + response.getString("code"));
                                    Toast.makeText(CambiarContra.this, "La contraseña nueva no puede ser igual que la anterior", Toast.LENGTH_SHORT).show();
                                }
                                else if (response.getString("code").equals("200")){
                                    Toast.makeText(CambiarContra.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(CambiarContra.this, UserArea.class));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.d("RESPUESTA ONERROR", error.toString());

                            Log.d("RESPUESTA ONERROR", "onError errorCode : " + error.getErrorCode());
                            Log.d("RESPUESTA ONERROR", "onError errorBody : " + error.getErrorBody());
                            Log.d("RESPUESTA ONERROR", "onError errorDetail : " + error.getErrorDetail());

                        }
                    });

 /*
                            try {
                                if (response.getString("code").equals("666")) //contraseña incorrecta
                                {
                                    try {
                                        Log.d("RESULTADOCAMBIARCONTRA" , "codigo : " + response.getString("code"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(CambiarContra.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                                }else {
                                    try {
                                        if (response.getString("code").equals("504"))   //contraseñas iguales
                                        {
                                            try {
                                                Log.d("RESULTADOCAMBIARCONTRA" , "codigo : " + response.getString("code"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            Toast.makeText(CambiarContra.this, "La contraseña nueva no puede ser igual que la anterior", Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(CambiarContra.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(CambiarContra.this, UserArea.class));

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                                 */


/*
            if (result.getString("code").equals("666")) //contraseña incorrecta
            {
                Log.d("RESULTADOCAMBIARCONTRA" , "codigo : " + result.getString("code"));

                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

            }else if (result.getString("code").equals("504"))   //contraseñas iguales
            {
                Log.d("RESULTADOCAMBIARCONTRA" , "codigo : " + result.getString("code"));

                Toast.makeText(this, "La contraseña nueva no puede ser igual que la anterior", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, UserArea.class));

            }

 */
        }
        else {
            Toast.makeText(this, "Las contraseñas nuevas no son iguales", Toast.LENGTH_SHORT).show();

        }






    }
}