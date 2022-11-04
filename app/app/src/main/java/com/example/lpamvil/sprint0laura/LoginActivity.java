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
    String nombreUs = "";
    String usuarioNom = "";

    JSONObject object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());
        user = (EditText) findViewById(R.id.edittextuser);
        pass = (EditText) findViewById(R.id.edittextpass);
        Logica logica = new Logica();

    }
    //---------------------------------------------------------------------------------------
    //(View view)----->botonentrar()
    //---------------------------------------------------------------------------------------
    public void botonentrar(View view) throws JSONException {
        String u = user.getText().toString();
        String p = pass.getText().toString();


        //comprobamos si las casillas estan vacias
        if (u.equals("") && p.equals("")) {
            Toast.makeText(this, "ERROR: Campos vacíos", Toast.LENGTH_LONG).show();
        } else if (u.equals("")) {
            Toast.makeText(this, "ERROR: Campo usuario vacío", Toast.LENGTH_LONG).show();
        } else if (p.equals("")) {
            Toast.makeText(this, "ERROR: Campo contraseña vacío", Toast.LENGTH_LONG).show();
        } else {
            //intetnamos hacer llamar a la logica y llamar a login
            Log.d("RESPUESTALOGIN ", "entra al login");

            Logica logica = new Logica();

            object = logica.login(u,p);//lo metemos en una varieable global y llamamos a la funcion login




            try {
                Log.d("RESPUESTALOGIN ", object.getString("nombre"));
                nombreUs = object.getString("nombre");
                usuarioNom = object.getString("usuario");


                //si el objeto que ha devuelto la lógica no esta vacio, significa que esta en la
                // bbdd el usuario
                if(object.toString()!= null){

                    Intent i = new Intent(LoginActivity.this, UserArea.class);//SIGUIENTE PAGINAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    i.putExtra("pasarDato", nombreUs);
                    i.putExtra("pasarDato2", usuarioNom);

                    startActivity(i);
                    finish();
                }

            } catch (JSONException e) {

                e.printStackTrace();

            }





        }




    }
}