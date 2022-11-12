package com.example.lpamvil.sprint0laura;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Logica {

    Date fechaactual = new Date();

    static JSONObject jobject = new JSONObject();


    boolean sesionactiva;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;


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
        AndroidNetworking.post("http://192.168.0.14:8080/altaMedicion")
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
                            jobject = response.getJSONObject(0);

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

    public JSONObject editPerfil(String usuario,String nombre, String mail, String telefono)
    {
        Log.d("HAENTRADO", "HA ENTRADO");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("usuario",usuario);
            jsonObject.put("nombre",nombre);
            jsonObject.put("mail",mail);
            jsonObject.put("telefono",telefono);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://192.168.43.79:3000/update_profile") //Poner aqui IP propia para enviar al servidor en local
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("RESPUESTAEDIT", "va editar");
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
                        Log.d("RESPUESTAEDIT", error.toString());

                    }

                });

        return jobject;
    }

    /*
    public JSONObject cambiarcontra(String contraantigua,String contranueva, String usuario) {

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("usuario",usuario);
            jsonObject.put("oldpass",contraantigua);
            jsonObject.put("newpass",contranueva);

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

                        jobject = response;

                        Log.d("RESPUESTA ONRESPONSE: ", response.toString());

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

        return jobject;


*/
        /*

        Log.d("HAENTRADO", "HA ENTRADO");

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("usuario",usuario);
            jsonObject.put("oldpass",contraantigua);
            jsonObject.put("newpass",contranueva);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://192.168.0.14:3000/change_password") //Poner aqui IP propia para enviar al servidor en local
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("RESPUESTAEDIT", "va editar");
                        try {
                            JSONObject object = response.getJSONObject(0);
                            Log.d("CAMBIARCONTRARESPONSE", response.toString());

                            Log.d("CAMBIARCONTRA", object.toString());

                            jobject = object;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("EXCEPCIONJSON","hay una excepcion");
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("ERROR EN CAMBIAR CONTRA", error.toString());
                        Log.d("RESPUESTA ONERROR", "onError errorCode : " + error.getErrorCode());
                        Log.d("RESPUESTA ONERROR", "onError errorBody : " + error.getErrorBody());
                        Log.d("RESPUESTA ONERROR", "onError errorDetail : " + error.getErrorDetail());

                    }

                });

        return jobject;
        */



    }




