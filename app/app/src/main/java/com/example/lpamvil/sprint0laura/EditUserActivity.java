package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

public class EditUserActivity extends AppCompatActivity {

    EditText editnombre, editmail,edittelefono;

    String usuario;

    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);


        //ASIGNACIÓN DE ELEMENTOS DE LA VIEW
        editnombre = (EditText)findViewById(R.id.editnombre);
        editmail = (EditText)findViewById(R.id.editmail);
        edittelefono = (EditText)findViewById(R.id.edittelefono);


        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();



        //Mostrar en los TextView los valores actuales de datos del usuario
        usuario = preferencias.getString("usuario","prueba");
        editnombre.setText(preferencias.getString("nombre","prueba"));
        editmail.setText(preferencias.getString("mail","prueba"));
        edittelefono.setText(preferencias.getString("telefono","prueba"));

    }


    //------------------------------------------------
    //  View: view -->
    //  botoncambiarcontra()
    //
    //------------------------------------------------
    public void botoncambiarcontra(View view)
    {
        startActivity(new Intent(this, CambiarContra.class));

    }

    //------------------------------------------------
    //  View: view -->
    //  botonguardar()
    //
    //------------------------------------------------
    public void botonguardar(View view)throws JSONException
    {


        Logica logica = new Logica();
        logica.editPerfil(usuario,editnombre.getText().toString(),editmail.getText().toString(),edittelefono.getText().toString());

        //Guardamos las preferencias con los nuevos cambios y aplicamos.
        editorpreferencias.putString("nombre",editnombre.getText().toString());
        editorpreferencias.putString("mail",editmail.getText().toString());
        editorpreferencias.putString("telefono",edittelefono.getText().toString());
        editorpreferencias.putString("usuario",usuario);
        editorpreferencias.apply();

        Toast.makeText(EditUserActivity.this, "Perfil editado correctamente", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, UserArea.class));


    }
}