package com.example.lpamvil.sprint0laura;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class VincularDispo extends AppCompatActivity {


    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;
    TextView iddispositivo;
    String nombredispo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_dispo);
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();
        iddispositivo = findViewById(R.id.iddispositivo);
    }


    public void escanearqr(View view)
    {

        IntentIntegrator integrator = new IntentIntegrator(VincularDispo.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        IntentResult resultado = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(resultado != null){
            if(resultado.getContents() == null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            } else {
                iddispositivo.setText("Id del dispositivo: " + resultado.getContents());
                nombredispo = resultado.getContents();

            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void guardardispositivovinculado(View view)
    {
        editorpreferencias.putString("dispositivovinculado",nombredispo);
        editorpreferencias.apply();
        startActivity(new Intent(this, UserArea.class));

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