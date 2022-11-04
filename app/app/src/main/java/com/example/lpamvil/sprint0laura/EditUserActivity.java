package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class EditUserActivity extends AppCompatActivity {

    EditText editnombre, editmail,edittelefono;

    String usuario;

    String nombre,mail, telefono;

    JSONObject object2;


    String nombreUs2 = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //AL ENTRAR EN ESTA PANTALLA DEBEN APARECER LOS DATOS DEL USUARIO DESDE LA BBDD EN LOS TEXTOS DEL EDIT TEXT PARA ASI PODER MODIFICAR SABIENDO LA INFORMACIÓN ANTERIOR

        //ASIGNACIÓN DE ELEMENTOS DE LA VIEW
        editnombre = (EditText)findViewById(R.id.editnombre);
        editmail = (EditText)findViewById(R.id.editmail);
        edittelefono = (EditText)findViewById(R.id.edittelefono);
        //Los textos escritos en el editText

    }


    //---------------------------------------------------------------------------------------
    //(View view)----->botonguardar()
    //---------------------------------------------------------------------------------------
    public void botonguardar(View view)throws JSONException
    {

         usuario = "eustaquio";
        nombreUs2 = editnombre.getText().toString();//pasar el nombre a otra actividad


        //editamos perfil
        Logica logica = new Logica();
        object2 = logica.editPerfil(usuario,editnombre.getText().toString(),editmail.getText().toString(),edittelefono.getText().toString());


        if(object2.toString()!= null){
            Intent i = new Intent(EditUserActivity.this, UserArea.class);//SIGUIENTE PAGINAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA


            i.putExtra("pasarDato", nombreUs2);//pasar el nombre a otra actividad

            startActivity(i);
        }

    }
}