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

//Esta clase creará las conexiones de métodos de android con el servidor
public class Logica {

    String ip = "192.168.1.133";
    Date fechaactual = new Date();

    static JSONObject jobject = new JSONObject();


    boolean sesionactiva;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;


    /*
     * @brief Crea la conexión con el método del servidor para insertar datos
     * int:valorbeacon -> enviardatosreal()
     *
     * @param valorbeacon valor del beacon que será enviado al servidor
     */
    public void enviardatosreal(double valorbeacon)
    {
        Log.d("RESPUESTAEnviarrrrrr1", "va enviarr ");

        //Se crea un objeto Json para meter los valores de la medida y posteriormente enviarlos en el post
        JSONObject jsonObject = new JSONObject();
        //Se crea una clase medida donde se insertan los atributos del valor de beacon, fecha, latitud y longitud
        Medida medida = new Medida(valorbeacon,1,fechaactual.toString(),38.99694087643454,-0.1650828343732021);



        try {
            Log.d("RESPUESTAEnviarrrrrr2", "va enviarr ");

            jsonObject.put("idsensor", medida.getIdsensor()); //   idSensor
            jsonObject.put("valor", medida.getValor());
            jsonObject.put("fecha", medida.getFecha());
            jsonObject.put("latitud", medida.getLatitud());
            jsonObject.put("longitud", medida.getLongitud());


        } catch (JSONException e) {
            e.printStackTrace();

        }


        //El método de la api para hacer el post
        AndroidNetworking.post("http://" + ip + ":3000/insert_measure1")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.d("RESPUESTAEnviarrrrrr3", "va enviarr ");

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTAEnviarrrrrr4", "no va enviarr ");



                    }
                });

    }


    /*
     * @brief Crea la conexión con el método del servidor para editar el perfil
     *
     * String:usuario,String:nombre,String:mail,String:telefono -> editPerfil()
     *
     * @param usuario El usuario que actualiza el perfil
     * @param nombre El nombre del usuario que se va a actualizar en el perfil
     * @param mail El mail del usuario que se va a actualizar en el perfil
     * @param nombre El telefono del usuario que se va a actualizar en el perfil
     */
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


    /*
     * @brief Crea la conexión con el método del servidor para insertar dispositivo
     *
     * String:usuario,String:tiposensor,String:nombresensor -> insertarDispo()
     *
     * @param usuario El usuario al cual se le inserta el sensor
     * @param tiposensor El tipo de sensor que se va a insertar
     * @param nombresensor El nombre del sensor que se va a insertar
     */
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




