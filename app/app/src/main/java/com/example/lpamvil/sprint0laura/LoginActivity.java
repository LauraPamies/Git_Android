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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());
        user = (EditText) findViewById(R.id.edittextuser);
        pass = (EditText) findViewById(R.id.edittextpass);

    }

    public void botonentrar(View view) {
        String u = user.getText().toString();
        String p = pass.getText().toString();
        if (u.equals("") && p.equals("")) {
            Toast.makeText(this, "ERROR: Campos vacíos", Toast.LENGTH_LONG).show();
        } else if (u.equals("")) {
            Toast.makeText(this, "ERROR: Campo usuario vacío", Toast.LENGTH_LONG).show();
        } else if (p.equals("")) {
            Toast.makeText(this, "ERROR: Campo contraseña vacío", Toast.LENGTH_LONG).show();
        } else {

            Log.d("RESPUESTALOGIN", "entra al login");

            Logica logica = new Logica();
            JSONObject object = logica.login(u,p);

            Toast.makeText(this, object.toString(), Toast.LENGTH_LONG).show();

            try {
                Log.d("RESPUESTALOGIN", object.getString("nombre"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //ha esto habrá que ponerle un if para ir solo si la cuenta está bien
        Intent i = new Intent(LoginActivity.this, UserArea.class);
        startActivity(i);
    }
}