package com.example.lpamvil.sprint0laura;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogicaFake {

    public void enviardatosfake()
    {
        Medida medida = new Medida(456,"07-10-2022",76,34);



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
}
