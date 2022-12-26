package com.example.lpamvil.sprint0laura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;


//Esta clase realizará el cambio de datos de perfil del usaurio, accediendo a métodos del servidor.
public class EditUserActivity extends AppCompatActivity {

    EditText editnombre, editmail,edittelefono;

    String usuario;

    Button button3;

    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;

    ImageButton botonmenu_userarea;

    boolean menudesplegado=false;

    private ConstraintLayout layoutanimado;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);


        //ASIGNACIÓN DE ELEMENTOS DE LA VIEW
        editnombre = (EditText)findViewById(R.id.editnombre);
        editmail = (EditText)findViewById(R.id.editmail);
        edittelefono = (EditText)findViewById(R.id.edittelefono);

        botonmenu_userarea = findViewById(R.id.botonmenu_userarea);
        layoutanimado = (ConstraintLayout) findViewById(R.id.layoutanimado);

        button3 = findViewById(R.id.button3);

        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();



        //Mostrar en los TextView los valores actuales de datos del usuario
        usuario = preferencias.getString("usuario","prueba");
        editnombre.setText(preferencias.getString("nombre","prueba"));
        editmail.setText(preferencias.getString("mail","prueba"));
        edittelefono.setText(preferencias.getString("telefono","prueba"));

    }



    public void clickbotonmenu(View view)
    {
        if (!menudesplegado) //no está desplegado el menu
        {
            animar("aparecer");
            layoutanimado.setVisibility(View.VISIBLE);
            botonmenu_userarea.setImageResource(R.drawable.ic_baseline_close_40);

            button3.setVisibility(View.GONE);


            menudesplegado = true;
        }else { //está desplegado
            animar("desaparecer");
            layoutanimado.setVisibility(View.GONE);
            botonmenu_userarea.setImageResource(R.drawable.menu);
            button3.setVisibility(View.VISIBLE);

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


    /*
     * @brief Al pulsar este botón el usuario será enviado a la pantalla de cambiar contraseña
     *
     * View-> botoncambiarcontra()
     *
     */
    public void botoncambiarcontra(View view)
    {
        startActivity(new Intent(this, CambiarContra.class));

    }

    /*
    * @brief Con este botón se guardarán los cambios realizados. Accede al método del servidor que edita los datos del usuario.
    *
    * View-> botonguardar()
    *
    * @verbatim puede lanzar una excepcion de tipo Exception
    */
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

    /*
     * @brief Al pulsar este botón el usuario se desconectará de la sesión
     *
     * View-> logoutbutton()
     *
     */
    public void logoutbutton(View view) {

        editorpreferencias.putBoolean("sesionrecordada", false);
        editorpreferencias.putString("dispositivovinculado", "nohay");
        editorpreferencias.putString("idSensor", "");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class));
    }













}