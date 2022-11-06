package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

public class CambiarContra extends AppCompatActivity {


    EditText editcontraantigua, editcontranueva;



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


        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();
    }


    public void botonguardar(View view)throws JSONException
    {


        Logica logica = new Logica();

        //logica.cambiarcontra(editcontraantigua.getText().toString(),editcontranueva.getText().toString(),preferencias.getString("usuario","prueba"));

        startActivity(new Intent(this, UserArea.class));


    }
}