package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OlvidadoContrasenya extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidado_correo);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button enviar_correo = (Button) findViewById(R.id.button_send_email);
        enviar_correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OlvidadoContrasenya.CorreoSegundoPlano().execute(); //Arrancamos el AsyncTask. el método "execute" envía datos directamente a doInBackground()
            }
        });
    }

    /*
     * Recoge los datos introducidos por el usuario y los manda por correo desde un hilo en segundo plano
     */
    private class CorreoSegundoPlano extends AsyncTask<String, Float, Integer> {

        @Override
        protected Integer doInBackground(String... variableNoUsada) {
            Mail m = new Mail("trackerpollutiongti@gmail.com", "trackerpollution000");





            EditText campo_correo = (EditText) findViewById(R.id.correo);
            EditText campo_asunto = (EditText) findViewById(R.id.asunto);
            EditText campo_motivo = (EditText) findViewById(R.id.motivo);
            String correo = campo_correo.getText().toString();
            String motivo = campo_motivo.getText().toString();
            String asunto = campo_asunto.getText().toString();

            String[] toArr = {"alvaromusica09@gmail.com"};
            m.setTo(toArr);
            m.setFrom("trackerpollutiongti@gmail.com");
            m.setSubject(asunto);
            m.setBody(correo, motivo);//meto la contraseña
            Log.d("MailApp", "Estoy dentro del mensaje");

            try {
                Log.d("MailApp", "Estoy dentro del try");
                if(m.send()) {
                    OlvidadoContrasenya.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(OlvidadoContrasenya.this, "El correo ha sido enviado satisfactoreamente", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    OlvidadoContrasenya.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(OlvidadoContrasenya.this, "Error al mandar el correo: hay campos vacíos", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch(Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, FragmentMainActivity.class);
        startActivity(intent);
        finish();
    }

}