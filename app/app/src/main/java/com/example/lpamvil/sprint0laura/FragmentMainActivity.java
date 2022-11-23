package com.example.lpamvil.sprint0laura;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lpamvil.sprint0laura.databinding.ActivityFragmentMainBinding;

import org.json.JSONException;

public class FragmentMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityFragmentMainBinding binding;


    //------------------------------------------------EDITAR---------------------------------------------------
/*    EditText editnombre, editmail,edittelefono;
    String usuario;
    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;*/


    //------------------------------------------------EDITAR---------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFragmentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarFragmentMain.toolbar);//eso en rojo a veces

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_editar, R.id.nav_contactanos, R.id.nav_vincular, R.id.nav_gases)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_fragment_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

/*
        //------------------------------------------------EDITAR---------------------------------------------------


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
        */
        //------------------------------------------------EDITAR---------------------------------------------------


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_fragment_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //------------------------------------------------IR A SITIOS---------------------------------------------------

    public void botonIrContactanosActivity(View view){
        Intent i = new Intent(this, Contactar.class);
        startActivity(i);
    }

    public void botonIrEditarActivity(View view){
        Intent i = new Intent(this, EditUserActivity.class);
        startActivity(i);
    }

    public void botonIrVincularActivity(View view){
        Intent i = new Intent(this, VincularDispo.class);
        startActivity(i);
    }

    public void botonIrGasesActivity(View view){
        Intent i = new Intent(this, EditUserActivity.class);
        startActivity(i);
    }
/*    public void botonIrLogout(View view){
        Intent i = new Intent(this, EditUserActivity.class);
        startActivity(i);
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, FragmentMainActivity.class);
        startActivity(intent);
        finish();
    }
//------------------------------------------------IR A SITIOS---------------------------------------------------

    //------------------------------------------------EDITAR---------------------------------------------------


    //------------------------------------------------
    //  View: view -->
    //  botonguardar()
    //
    //------------------------------------------------

    //------------------------------------------------EDITAR---------------------------------------------------

    //------------------------------------------------LOGOUT---------------------------------------------------


    //------------------------------------------------LOGOUT---------------------------------------------------

}