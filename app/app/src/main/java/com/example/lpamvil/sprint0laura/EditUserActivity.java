package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditUserActivity extends AppCompatActivity {

    EditText editnombre, editmail,edittelefono;
    String nombre,mail,telefono;


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
        nombre = editnombre.getText().toString();
        mail = editmail.getText().toString();
        telefono = edittelefono.getText().toString();
    }

    public void botonguardar(View view)
    {
        //el botón de guardar actualiza la información y a la vez retrocede hacia UserArea
        Intent i = new Intent(EditUserActivity.this, UserArea.class);
        startActivity(i);
    }
}