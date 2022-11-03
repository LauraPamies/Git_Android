package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText user, pass;
    //String nombreUs = "";
    JSONObject object;
    Logica logica = new Logica();
    String u;
    String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());
        user = (EditText) findViewById(R.id.edittextuser);
        object =null;
        pass = (EditText) findViewById(R.id.edittextpass);


    }

    public void botonentrar(View view) throws JSONException {
        //Log.d("RESPUESTALOGINIVAN ", object.toString());


        u = user.getText().toString();
        p = pass.getText().toString();



        Toast.makeText(this, "IVANCAMPO"+p, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "IVANCAMPO"+u, Toast.LENGTH_LONG).show();


        if (u.equals("") && p.equals("")) {
            Toast.makeText(this, "ERROR: Campos vacíos", Toast.LENGTH_LONG).show();

        } else if (u.equals("")) {
            Toast.makeText(this, "ERROR: Campo usuario vacío", Toast.LENGTH_LONG).show();

        } else if (p.equals("")) {
            Toast.makeText(this, "ERROR: Campo contraseña vacío", Toast.LENGTH_LONG).show();

        } else {

            Log.d("RESPUESTALOGIN ", "entra al login");
           // Logica logica = new Logica();
            //JSONObject object;
            Toast.makeText(this, "objectIVAN1"+object, Toast.LENGTH_LONG).show();

            object = logica.login(u,p);//lo metemos en una varieable global y llamamos a la funcion login

            Toast.makeText(this, "objectIVAN2"+object, Toast.LENGTH_LONG).show();

            //nombreUs = object.getString("nombre");



            Toast.makeText(this, object.toString(), Toast.LENGTH_LONG).show();

            try {
                Log.d("RESPUESTALOGIN ", object.getString("nombre"));
                String nombreUs = "";

                nombreUs = object.getString("nombre");
                //usuario.setNombre(object.getString("nombre"));
                //Toast.makeText(this, "RESPUESTAUSUARIOusuariogetNombre" + usuario.getNombre(), Toast.LENGTH_LONG).show();ç
                //Usuario usuario = new Usuario();
                //usuario.setNombre(object.getString("nombre"));

                //Toast.makeText(this, "RESPUESTA usuario.getNombre " + usuario.getNombre(), Toast.LENGTH_LONG).show();

                Toast.makeText(this, "RESPUESTAUSUARInombreUs" + nombreUs, Toast.LENGTH_LONG).show();

                if(object.toString()!= null){
                    Intent i = new Intent(LoginActivity.this, UserArea.class);//SIGUIENTE PAGINAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    i.putExtra("pasarDato", nombreUs);
                    Log.d("RESPUESTALOGINIVAN ", object.toString());

                    startActivity(i);

                }

            } catch (JSONException e) {

                e.printStackTrace();
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();

            }





        }

        //if(la conexion es = 1 entocnes)----------------------------------------------------------------------------------------------------------------
        //ha esto habrá que ponerle un if para ir solo si la cuenta está bien


    }
}