package com.example.lpamvil.sprint0laura;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;


// En esta clase se va a vincular el dispositivo mediante un QR
public class VincularDispo extends AppCompatActivity {


    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;
    TextView iddispositivo;
    JSONObject sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_dispo);
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();
        iddispositivo = findViewById(R.id.iddispositivo);
    }


    /*
     * @brief Al pulsar este botón se abrirá el escaner QR
     *
     * View-> escanearqr()
     *
     */
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


    // @brief Al escanear el QR, si ha ido bien se añadirá el dispositivo vinculado al usuario
    //------------------------------------------------
    //  RequestCode: int, ResultCode: int, Data: Intent -->
    //  onActivityResult()
    //
    //------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        IntentResult resultado = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(resultado != null){
            if(resultado.getContents() == null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            } else {

                try {

                    sensor = new JSONObject(resultado.getContents());
                    Log.d("RESULTADOJSON",sensor.toString());
                    Log.d("RESULTADOJSON",sensor.getString("nombre"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ERROR QR",e.toString());

                    Log.d("ERROR","EL QR RECIBIDO NO ES UN JSON");

                }
                try {
                    iddispositivo.setText("Id del dispositivo: " + sensor.getString("nombre"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    editorpreferencias.putString("dispositivovinculado",sensor.getString("nombre"));
                    editorpreferencias.putString("tiposensor",sensor.getString("tipo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editorpreferencias.apply();


                addDispositivobbdd(preferencias.getString("dispositivovinculado",""),preferencias.getString("tiposensor",""));
                //AQUI DEBEMOS AÑADIR EL DISPOSITIVO A LA BBDD DEL USUARIO


            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /*
     * @brief Se añadirá el dispositivo a la bbdd del usuario
     *
     * String:nombredispo,String:tipodispo-> addDispositivobbdd()
     *
     * @param nombredispo Nombre del dispositivo que se va a añadir
     * @param tipodispo EL tipo de dispositivo que se va a añadir
     *
     */
    private void addDispositivobbdd(String nombredispo,String tipodispo)
    {
        //HACE EL INSERT EN LA BBDD
        Logica logica = new Logica();
        logica.insertarDispo(preferencias.getString("usuario",""),tipodispo,nombredispo);
    }


    /*
     * @brief Se accede al Area del usuario
     *
     * View:view -> guardardispositivovinculado()
     *
     *
     */
    public void guardardispositivovinculado(View view)
    {
        if(!preferencias.getString("dispositivovinculado", "nohay").equals("nohay")) //si ha encontrado algún dispositivo
        {
            Toast.makeText(this, "Dispositivo vinculado correctamente", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, UserArea.class));

        }else {
            Toast.makeText(this, "No se ha podido vincular el dispositivo", Toast.LENGTH_SHORT).show();

        }

    }


    /*
     * @brief Al pulsar este botón el usuario se desconectará de la sesión
     *
     * View-> logoutbutton()
     *
     */

    public void logoutbutton(View view)
    {

        editorpreferencias.putBoolean("sesionrecordada",false);
        editorpreferencias.putString("dispositivovinculado","nohay");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));


    }
}