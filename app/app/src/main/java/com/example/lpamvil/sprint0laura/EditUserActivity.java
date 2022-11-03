package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class EditUserActivity extends AppCompatActivity {

    EditText editnombre, editmail,edittelefono;

    String usuario, nombre,mail, telefono;

    JSONObject object2;
    //Bundle datosUsuario;
    String nombreUs2 = "";




    //String datosUsuariosString = datosUsuario.getString("pasarDato2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        //datosUsuario = getIntent().getExtras();

        //AL ENTRAR EN ESTA PANTALLA DEBEN APARECER LOS DATOS DEL USUARIO DESDE LA BBDD EN LOS TEXTOS DEL EDIT TEXT PARA ASI PODER MODIFICAR SABIENDO LA INFORMACIÓN ANTERIOR

        //ASIGNACIÓN DE ELEMENTOS DE LA VIEW
        editnombre = (EditText)findViewById(R.id.editnombre);
        editmail = (EditText)findViewById(R.id.editmail);
        edittelefono = (EditText)findViewById(R.id.edittelefono);
        object2 = null;
        //Los textos escritos en el editText

    }

    public void botonguardar(View view)throws JSONException
    {
        /*
         usuario = "eustaquio";
        nombre = editnombre.getText().toString();
        mail = editmail.getText().toString();
        telefono = edittelefono.getText().toString();
        */
        //usuario = datosUsuario.getString("pasarDato2");

        // = "eustaquio";
        nombreUs2 = editnombre.getText().toString();


        /*
        usuario = "eustaquio";
        nombre = "alvero";
        mail = "alvaro@gmail.com";
        telefono = "888888888";
        */

        //Log.d("RESPUESTAEDIT", "datosUsuariosString"+ datosUsuariosString);


        Logica logica = new Logica();
        object2 = logica.editPerfil(usuario,editnombre.getText().toString(),editmail.getText().toString(),edittelefono.getText().toString());
        Toast.makeText(this, "EDITttPARTE"+object2.toString(), Toast.LENGTH_LONG).show();


        //Log.d("RESPUESTALOGIN ", object2.getString("nombre"));
        //nombreUs = object.getString("nombre");
        //usuario.setNombre(object.getString("nombre"));
        //Toast.makeText(this, "RESPUESTAUSUARIOusuariogetNombre" + usuario.getNombre(), Toast.LENGTH_LONG).show();ç
        //Usuario usuario = new Usuario();
        //usuario.setNombre(object.getString("nombre"));

        //Toast.makeText(this, "RESPUESTA usuario.getNombre " + usuario.getNombre(), Toast.LENGTH_LONG).show();

        //Toast.makeText(this, "RESPUESTAUSUARInombreUs" + nombreUs, Toast.LENGTH_LONG).show();

        if(object2.toString()!= null){
            Intent i = new Intent(EditUserActivity.this, UserArea.class);//SIGUIENTE PAGINAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
          //i.putExtra("pasarDato", nombreUs);
            // i.putExtra("pasarDato", nombreUs);
            i.putExtra("pasarDato", nombreUs2);

            startActivity(i);
        }

    }
}