package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AnyadirDispositivo extends AppCompatActivity {

    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_dispositivo);
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();
    }

    public void anyadirdispo(View view){
        startActivity(new Intent(AnyadirDispositivo.this, VincularDispo.class));

    }

    public void logoutbutton(View view)
    {

        editorpreferencias.putBoolean("sesionrecordada",false);
        editorpreferencias.putString("dispositivovinculado","nohay");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));


    }
}