package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Esta clase realizará el cambio de contraseña, accediendo a métodos del servidor.
public class CambiarContra extends AppCompatActivity {

    String ip = "192.168.1.136";

    Button button3;

    EditText editcontraantigua, editcontranueva, editcontranuevarepetir;

    String oldpass, newpass, newpass2;

    ImageButton botonmenu_userarea;

    boolean menudesplegado=false;

    private ConstraintLayout layoutanimado;



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

        button3 = findViewById(R.id.button3);

        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();

        botonmenu_userarea = findViewById(R.id.botonmenu_userarea);
        layoutanimado = (ConstraintLayout) findViewById(R.id.layoutanimado);




    }



    public void clickbotonmenu(View view)
    {
        if (!menudesplegado) //no está desplegado el menu
        {
            animar("aparecer");
            layoutanimado.setVisibility(View.VISIBLE);
            botonmenu_userarea.setImageResource(R.drawable.ic_baseline_close_40);

            button3.setVisibility(View.GONE);


            menudesplegado = true;
        }else { //está desplegado
            animar("desaparecer");
            layoutanimado.setVisibility(View.GONE);
            botonmenu_userarea.setImageResource(R.drawable.menu);

            button3.setVisibility(View.VISIBLE);


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


    public void botoncontactanos(View view)
    {
        startActivity(new Intent(this, Contactar.class));

    }

    public void botonuserarea(View view)
    {
        startActivity(new Intent(this, UserArea.class));

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



   /*
     * @brief Al pulsar este botón el usuario se desconectará de la sesión
     *
     * View-> logoutbutton()
     *
     */
    public void logoutbutton(View view)
    {
        editorpreferencias.putBoolean("sesionrecordada",false);
        editorpreferencias.putString("dispositivovinculado","nohay");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();


        startActivity(new Intent(this, LoginActivity.class));


    }



    /*
     * @brief Con este botón se guardarán los cambios realizados. Accede al método del servidor que guarda la contraseña.
           Si va bien la contraseña se cambiará, si hay algún fallo será notificado al usuario mediante un Toast
     *
     * View-> botonguardar()
     *
     * @verbatim puede lanzar una excepcion de tipo Exception
     */
    public void botonguardar(View view)throws JSONException
    {
        oldpass = editcontraantigua.getText().toString();
        newpass = editcontranueva.getText().toString();
        newpass2 = editcontranuevarepetir.getText().toString();

        Log.d("OLDPASS", "botonguardar: " + oldpass + " " + newpass + " " + newpass2);
        if (newpass.equals(newpass2))   //SI AMBAS CONTRASEÑAS NUEVAS SON IGUALES
        {


            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("username",preferencias.getString("usuario","prueba"));
                jsonObject.put("oldpass",oldpass);
                jsonObject.put("newpass",newpass);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post("http://" + ip + ":3000/update_password")
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
                                    startActivity(new Intent(CambiarContra.this, EditUserActivity.class));

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





        }
        else {
            Toast.makeText(this, "Las contraseñas nuevas no son iguales", Toast.LENGTH_SHORT).show();

        }






    }
}