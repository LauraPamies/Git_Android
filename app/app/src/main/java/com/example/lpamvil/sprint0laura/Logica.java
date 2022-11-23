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

    String ip = "192.168.1.65";
    Date fechaactual = new Date();

    static JSONObject jobject = new JSONObject();


    boolean sesionactiva;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;

    //------------------------------------------------
    //  valorbeacon: int -->
    //  editPerfil()
    //
    //------------------------------------------------
    public void enviardatosreal(int valorbeacon)
    {


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



    //------------------------------------------------
    //  usuario: String, nombre: String, mail: String, telefono: String -->
    //  editPerfil()
    //  --> JSONObject
    //------------------------------------------------
    public void editPerfil(String usuario,String nombre, String mail, String telefono)
    {
        Log.d("HAENTRADO", "HA ENTRADO");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("username",usuario);
            jsonObject.put("name",nombre);
            jsonObject.put("mail",mail);
            jsonObject.put("phone",telefono);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://" + ip + ":3000/update_profile") //Poner aqui IP propia para enviar al servidor en local
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("RESPUESTAEDIT", "va editar");

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTAEDIT", error.toString());

                    }

                });


    }


    public void insertarDispo(String usuario,String tiposensor, String nombresensor)
    {
        Log.d("HAENTRADO", "HA ENTRADO");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("username",usuario);
            jsonObject.put("type",tiposensor);
            jsonObject.put("name",nombresensor);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post
        AndroidNetworking.post("http://" + ip +":3000/insert_sensor") //Poner aqui IP propia para enviar al servidor en local
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("RESPUESTAInsertSensor", "va insertar sensor");

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTAEDIT", error.toString());

                    }

                });

    }


    }




