package com.example.lpamvil.sprint0laura;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/*
 * Llama a la tarea del envío de correo que se ejecuta en el hilo en segundo plano desde el hilo principal
 */
public class Contactar extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button enviar_correo = (Button) findViewById(R.id.button_send_email);
        enviar_correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CorreoSegundoPlano().execute(); //Arrancamos el AsyncTask. el método "execute" envía datos directamente a doInBackground()
            }
        });
    }

    /*
     * Recoge los datos introducidos por el usuario y los manda por correo desde un hilo en segundo plano
     */
    private class CorreoSegundoPlano extends AsyncTask <String, Float, Integer> {

        @Override
        protected Integer doInBackground(String... variableNoUsada) {
            Mail m = new Mail("ivandiscobolo@gmail.com", "");

            EditText campo_correo = (EditText) findViewById(R.id.correo);
            EditText campo_asunto = (EditText) findViewById(R.id.asunto);
            EditText campo_motivo = (EditText) findViewById(R.id.motivo);
            String correo = campo_correo.getText().toString();
            String motivo = campo_motivo.getText().toString();
            String asunto = campo_asunto.getText().toString();

            String[] toArr = {"ivandiscobolo@gmail.com"};
            m.setTo(toArr);
            m.setFrom("ivandiscobolo@gmail.com");
            m.setSubject(asunto);
            m.setBody(correo, motivo);
            Log.d("MailApp", "Estoy dentro del mensaje");

            try {
                Log.d("MailApp", "Estoy dentro del try");
                if(m.send()) {
                    Contactar.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Contactar.this, "El correo ha sido enviado satisfactoreamente", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Contactar.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Contactar.this, "Error al mandar el correo: hay campos vacíos", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch(Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }




    }


}


