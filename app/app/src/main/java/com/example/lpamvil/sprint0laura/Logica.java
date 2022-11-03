package com.example.lpamvil.sprint0laura;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Logica {

    Date fechaactual = new Date();

    static JSONObject jobject = new JSONObject();

    public void enviardatosreal(int valorbeacon)
    {

//pueba comentario
        //Se crea una clase medida donde se insertan los atributos del valor de beacon, fecha, latitud y longitud
        Medida medida = new Medida(valorbeacon,fechaactual.toString(),38.99694087643454,-0.1650828343732021);
       


        //Se crea un objeto Json para meter los valores de la medida y posteriormente enviarlos en el post
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", null); //se envia id null ya que en la base de datos la id es autoincrementable
            jsonObject.put("valor", medida.getValor());
            jsonObject.put("fecha", medida.getFecha());
            jsonObject.put("latitud", medida.getLatitud());
            jsonObject.put("longitud", medida.getLongitud());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //El método de la api para hacer el post
        AndroidNetworking.post("http://172.20.10.2:8080/altaMedicion")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error


                    }
                });

    }


    public JSONObject login(String usuario,String contrasena)
    {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("username",usuario);
            jsonObject.put("password",contrasena);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://172.20.10.2:3000/login") //Poner aqui IP propia para enviar al servidor en local
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
                            JSONObject object = response.getJSONObject(0);
                            jobject = object;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTALOGIN", error.toString());

                    }

                });

        return jobject;
    }



    public JSONObject editPerfil(String usuario,String contrasena)
    {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("username",usuario);
            jsonObject.put("password",contrasena);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://172.20.10.2:3000/login") //Poner aqui IP propia para enviar al servidor en local
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
                            JSONObject object = response.getJSONObject(0);
                            jobject = object;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTALOGIN", error.toString());

                    }

                });

        return jobject;
    }
}
