package com.example.lpamvil.sprint0laura;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectSensor extends AppCompatActivity {
    private ArrayList<Sensor> sensores;
    private RecyclerView recyclerView;

    ImageButton botonmenu_userarea;

    boolean menudesplegado=false;

    private ConstraintLayout layoutanimado;

    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;
    String ip = "192.168.100.119";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sensor);
        recyclerView = findViewById(R.id.recyclersensores);
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();

        botonmenu_userarea = findViewById(R.id.botonmenu_userarea);
        layoutanimado = (ConstraintLayout) findViewById(R.id.layoutanimado);

        sensores = new ArrayList<>();

        //aqui obtiene el dispositivo de la bbdd del usuario
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", preferencias.getString("usuario",""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                            //JSONObject respuesta = response.getJSONObject(0);
                            Log.d("LOS SENSORES DEL USer:", response.toString());

                            for (int i=0;i<response.length();i++){

                                sensores.add(new Sensor(response.getJSONObject(i).getString("Tipo"),response.getJSONObject(i).getString("Nombre"),response.getJSONObject(i).getString("idSensor"),response.getJSONObject(i).getString("idUsuario")));

                            }

                            AdaptadorSensor adapter = new AdaptadorSensor(sensores);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);

                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    editorpreferencias.putString("tiposensor", sensores.get(recyclerView.getChildAdapterPosition(view)).getTipo());
                                    editorpreferencias.putString("idSensor", sensores.get(recyclerView.getChildAdapterPosition(view)).getIdSensor());
                                    editorpreferencias.putString("dispositivovinculado", sensores.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                                    editorpreferencias.putString("idUsuario", sensores.get(recyclerView.getChildAdapterPosition(view)).getIdUsuario());

                                    editorpreferencias.apply();

                                    startActivity(new Intent(SelectSensor.this, UserArea.class));

                                }
                            });






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RESPUESTAGETONESENSOR", error.toString());



                    }

                });









    }


    public void botoncontactanos(View view)
    {
        startActivity(new Intent(this, Contactar.class));

    }

    public void editarperfil(View view)
    {
        startActivity(new Intent(this, EditUserActivity.class));

    }



    public void vinculardispo(View view)
    {
        startActivity(new Intent(this, VincularDispo.class));

    }

    public void gasesnocivos(View view)
    {
        startActivity(new Intent(this, activity_gases.class));

    }

    public void clickbotonmenu(View view)
    {
        if (!menudesplegado) //no está desplegado el menu
        {
            animar("aparecer");
            layoutanimado.setVisibility(View.VISIBLE);
            botonmenu_userarea.setImageResource(R.drawable.ic_baseline_close_40);
            recyclerView.setVisibility(View.GONE);

            menudesplegado = true;
        }else { //está desplegado
            animar("desaparecer");
            layoutanimado.setVisibility(View.GONE);
            botonmenu_userarea.setImageResource(R.drawable.menu);
            recyclerView.setVisibility(View.VISIBLE);

            menudesplegado = false;
        }
    }

    private void animar (String mostrar)
    {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar.equals("aparecer"))
        {
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f
            );
        }
        if (mostrar.equals("desaparecer"))
        {
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,0.1f,
                    Animation.RELATIVE_TO_SELF,0.1f,
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f
            );
        }

        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set,0.20f);
        layoutanimado.setLayoutAnimation(controller);
        layoutanimado.startAnimation(animation);
    }

    public void logoutbutton(View view) {

        editorpreferencias.putBoolean("sesionrecordada", false);
        editorpreferencias.putString("dispositivovinculado", "nohay");
        editorpreferencias.putString("idSensor", "");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class));
    }


}

