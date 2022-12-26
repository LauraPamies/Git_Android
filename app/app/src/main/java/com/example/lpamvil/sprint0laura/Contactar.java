package com.example.lpamvil.sprint0laura;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import android.os.Bundle;
import android.widget.ImageButton;
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

    ImageButton botonmenu_userarea;

    boolean menudesplegado=false;

    Button button_send_email;
    private ConstraintLayout layoutanimado;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;



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

        botonmenu_userarea = findViewById(R.id.botonmenu_userarea);
        layoutanimado = (ConstraintLayout) findViewById(R.id.layoutanimado);

        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();

        button_send_email = findViewById(R.id.button_send_email);

    }


    public void clickbotonmenu(View view)
    {
        if (!menudesplegado) //no está desplegado el menu
        {
            animar("aparecer");
            layoutanimado.setVisibility(View.VISIBLE);
            botonmenu_userarea.setImageResource(R.drawable.ic_baseline_close_40);

            button_send_email.setVisibility(View.GONE);


            menudesplegado = true;
        }else { //está desplegado
            animar("desaparecer");
            layoutanimado.setVisibility(View.GONE);
            botonmenu_userarea.setImageResource(R.drawable.menu);
            button_send_email.setVisibility(View.VISIBLE);


            menudesplegado = false;
        }
    }

    private void animar (String mostrar)
    {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (mostrar.equals("aparecer"))
        {
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f
            );
        }
        if (mostrar.equals("desaparecer"))
        {
            animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,0.1f,
                    Animation.RELATIVE_TO_SELF,0.1f,
                    Animation.RELATIVE_TO_SELF,0.0f,
                    Animation.RELATIVE_TO_SELF,0.0f
            );
        }

        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set,0.20f);
        layoutanimado.setLayoutAnimation(controller);
        layoutanimado.startAnimation(animation);
    }


    public void botoncontactanos(View view)
    {
        startActivity(new Intent(this, Contactar.class));

    }

    public void botonuserarea(View view)
    {
        startActivity(new Intent(this, UserArea.class));

    }

    public void editarperfil(View view)
    {
        startActivity(new Intent(this, EditUserActivity.class));

    }

    public void mis_sensores(View view)
    {
        startActivity(new Intent(this, SelectSensor.class));

    }

    public void gasesnocivos(View view)
    {
        startActivity(new Intent(this, activity_gases.class));

    }


    public void logoutbutton(View view) {

        editorpreferencias.putBoolean("sesionrecordada", false);
        editorpreferencias.putString("dispositivovinculado", "nohay");
        editorpreferencias.putString("idSensor", "");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class));
    }
    /*
     * Recoge los datos introducidos por el usuario y los manda por correo desde un hilo en segundo plano
     */
    private class CorreoSegundoPlano extends AsyncTask <String, Float, Integer> {

        @Override
        protected Integer doInBackground(String... variableNoUsada) {
            Mail m = new Mail("trackerpollutiongti@gmail.com", "liba fqga fujp kfaj");

            EditText campo_correo = (EditText) findViewById(R.id.correo);
            EditText campo_asunto = (EditText) findViewById(R.id.asunto);
            EditText campo_motivo = (EditText) findViewById(R.id.motivo);
            String correo = campo_correo.getText().toString();
            String motivo = campo_motivo.getText().toString();
            String asunto = campo_asunto.getText().toString();

            String[] toArr = {"trackerpollutiongti@gmail.com"};
            m.setTo(toArr);
            m.setFrom("trackerpollutiongti@gmail.com");
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


