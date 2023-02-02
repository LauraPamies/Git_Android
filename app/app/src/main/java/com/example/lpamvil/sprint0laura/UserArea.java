package com.example.lpamvil.sprint0laura;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


//En esta clase estará la zona del usuario.

public class UserArea extends AppCompatActivity {
    // Variables related to notifications
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    // It allows to prevent the notification from being created more than once when it's already created
    boolean notificacion_unica = false;
    boolean detectado = false;
    long tiempo_inicial = 0;
    ImageButton botonmenu_userarea;
    Button prueba;

    boolean menudesplegado=false;

    private ConstraintLayout layoutanimado;

    String nombredispo;

    TextView iddispositivouser, textocalidadaire;
    TextView textodispoconectado , tiposensor, colorestimacionaire;

//------------------------------------------------------------------------------------------VALOR MEDIDO BEACON
    public String valorbeacon = null;
    public String valorbeaconDoubleMotrar = null;
    public double valorbeacondouble;
    public double valorbeacondoubleAux;
//------------------------------------------------------------------------------------------VALOR MEDIDO BEACON


//------------------------------------------------------------------------------------------VALOR Location

    private LocationManager locManager;
    public Location loc;

    double longitud;
    double latitud;
//------------------------------------------------------------------------------------------VALOR Location

    private EditText valormedicion;
    private EditText valorid;

    String ip = Utilidades.ip;


    SharedPreferences preferencias;
    SharedPreferences.Editor editorpreferencias;

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private static final int CODIGO_PETICION_PERMISOS = 11223344;

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    private BluetoothLeScanner elEscanner;

    private ScanCallback callbackDelEscaneo = null;


    /*
     * @brief Muestra la información del dispositivo escaneado
     * ScanResult:resultado -> mostrarInformacionDispositivoBTLE()
     *
     * @param resultado el resultado obtenido del escaner
     */
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado ){


        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();

        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ****************** ");
        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());


        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi );

        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        TramaIBeacon tib = new TramaIBeacon(bytes);

        valorbeacon= String.valueOf(Utilidades.bytesToInt(tib.getMinor()));


        valorbeacondouble = Integer.parseInt(valorbeacon);
        valorbeacondoubleAux = abs(valorbeacondouble/1000);

        //Si el valor de contaminación recibido supera el umbral, se genera una notificacion
        //if(valorbeacondoubleAux > 0.3){
            //createNotificationChannel();
            //createNotification("Límite de calidad del aire excedido en (" + latitud+ ", " + longitud + ")", 1);

        //}

        valorbeaconDoubleMotrar = String.valueOf(valorbeacondoubleAux);
        tiposensor.setText(preferencias.getString("tiposensor","") + ": " + valorbeaconDoubleMotrar);
        int idSensor = Integer.parseInt(preferencias.getString("idSensor","prueba"));


        Logica logica = new Logica();
        obtenerCoordenadas();

        logica.enviardatosreal(idSensor,valorbeacondoubleAux, latitud, longitud);

        Log.d(ETIQUETA_LOG, "          VALOR BEACON ENVIADO = " + String.valueOf(valorbeacondoubleAux));

        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
        Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
        Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
        Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
        Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
        Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                + tib.getiBeaconLength() + " ) ");
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ****************************************************");

    } // ()

    /*
     * @brief Busca un dispositivo BTLE por el nombre que se le pase como parámetro
     * String:dispositivoBuscado -> buscarEsteDispositivoBTLE()
     *
     * @param dispositivoBuscado nombre del dispositivo que tiene que buscar
     */
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado ) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");


        // super.onScanResult(ScanSettings.SCAN_MODE_LOW_LATENCY, result); para ahorro de energía

        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult( int callbackType, ScanResult resultado ) {
                super.onScanResult(callbackType, resultado);

                //BluetoothDevice bluetoothDevice2 = resultado.getDevice();

                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");
                //Log.d(ETIQUETA_LOG, " dispositivo detectado = " + bluetoothDevice2.getName());
                Log.d("DISPOSITIVO BUSCADO",dispositivoBuscado);
                //contador++;

                if(System.currentTimeMillis()/1000 - tiempo_inicial < 30){
                    if(resultado.getDevice().getName() != null && resultado.getDevice().getName().equals(dispositivoBuscado)){
                        mostrarInformacionDispositivoBTLE( resultado );
                        notificacion_unica = false;
                        textodispoconectado.setText("Dispositivo conectado correctamente\nya puedes comenzar a escanear");
                        String distancia = estimarDistancia(resultado);
                        TextView dist = findViewById(R.id.distancia);
                        detectado = true;
                        dist.setText("Distancia al sensor: " + distancia);
                    }
                } else{
                    if(!detectado && !notificacion_unica){
                        textodispoconectado.setText("Dispositivo no conectado");
                        Log.d(ETIQUETA_LOG, "No se ha encontrado el dispositivo");
                        createNotificationChannel();
                        createNotification("Desconexion del nodo", 0);
                        notificacion_unica = true;
                    }
                    detectado = false;
                    tiempo_inicial = System.currentTimeMillis()/1000;
                }

            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");

            }
        };

        ScanFilter sf = new ScanFilter.Builder().setDeviceName( dispositivoBuscado ).build();

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado );
        //Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado
        //      + " -> " + Utilidades.stringToUUID( dispositivoBuscado ) );

        this.elEscanner.startScan( this.callbackDelEscaneo );
    } // ()



    /*
     * @brief Detiene la búsqueda de los dispositivos BTLE
     *
     * detenerBusquedaDispositivosBTLE()
     *
     */
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    private void detenerBusquedaDispositivosBTLE() {

        if ( this.callbackDelEscaneo == null ) {
            return;
        }

        this.elEscanner.stopScan( this.callbackDelEscaneo );
        this.callbackDelEscaneo = null;

    } // ()



    //------------------------------------------------
    //
    //  inicializarBlueTooth()
    //
    //------------------------------------------------

    /*
     * @brief Inicializa el bluetooth en la app
     *
     * inicializarBlueTooth()
     *
     */
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");

        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");

        bta.enable();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        this.elEscanner = bta.getBluetoothLeScanner();

        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");

        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                    UserArea.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
        }
        else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }
    } // ()


    public void clickbotonmenu(View view)
    {
        if (!menudesplegado) //no está desplegado el menu
        {
            animar("aparecer");
            layoutanimado.setVisibility(View.VISIBLE);
            botonmenu_userarea.setImageResource(R.drawable.ic_baseline_close_40);
            menudesplegado = true;
        }else { //está desplegado
            animar("desaparecer");
            layoutanimado.setVisibility(View.GONE);
            botonmenu_userarea.setImageResource(R.drawable.menu);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button enviar_correo = (Button) findViewById(R.id.prueba);
        enviar_correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserArea.CorreoSegundoPlano().execute(); //Arrancamos el AsyncTask. el método "execute" envía datos directamente a doInBackground()
            }
        });


        Log.d("--", " onCreate(): empieza ");


        //Creación de preferencias
        preferencias = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editorpreferencias = preferencias.edit();


        Log.d("userarea", "entra en la sesion activa");

        textodispoconectado = findViewById(R.id.textodispoconectado);
        tiposensor = findViewById(R.id.tiposensor);

        botonmenu_userarea = findViewById(R.id.botonmenu_userarea);
        layoutanimado = (ConstraintLayout) findViewById(R.id.layoutanimado);

        iddispositivouser = findViewById(R.id.iddispositivouser);
        textocalidadaire = findViewById(R.id.textocalidadaire);
        nombredispo = preferencias.getString("dispositivovinculado", "nohay");
        iddispositivouser.setText("Id del dispositivo: " + nombredispo);

        colorestimacionaire = findViewById(R.id.colorestimacionaire);


        if (!nombredispo.equals("nohay")) {
            Log.d("NOMBRE DISPOSITIVO", nombredispo);
            tiempo_inicial = System.currentTimeMillis()/1000;
            inicializarBlueTooth();
            this.buscarEsteDispositivoBTLE(nombredispo);

        }

        Log.d("--", " onCreate(): termina ");


        String idSensor = preferencias.getString("idSensor", "");


        JSONObject jsonObject = new JSONObject();

        //
        // crea la fecha actual y la fecha de ayer
        LocalDate date = null;//                                                     laura fecha
        LocalDate DateAnterior = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();//                                                     laura fecha
            DateAnterior = date.plusDays(-1);
            editorpreferencias.putString("DateAnterior", DateAnterior.toString());
            editorpreferencias.apply();

        }


        Log.d("FECHA ACTUAL", date.toString());
        Log.d("FECHA DE AYER", DateAnterior.toString());


        try {
            jsonObject.put("date", DateAnterior);
            jsonObject.put("id_sensor", idSensor);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //El método de la api para hacer el post para hacer la media de las medidas
        AndroidNetworking.post("http://" + ip + ":3000/average_measurements") //Poner aqui IP propia para enviar al servidor en local
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("RESPUESTA AVERAGE", "va AVERAGE MEDICIONES");
                        Log.d("RESPUESTA AVERAGE", response.toString());
                        float f_response = 0;
                        float valoralto = 0;
                        DecimalFormat formato1 = new DecimalFormat("#.00");
                        try {

                            if (!response.getString("media").equals("null")) {

                                f_response = Float.parseFloat(response.getString("media"));
                                valoralto = Float.parseFloat(response.getString("valoralto"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String fecha = preferencias.getString("DateAnterior", "");

                        //HAY QUE PONER IFs PARA ASIGNAR TEXTOS DEPENDE DE LOS UMBRALES
                        if (0 < f_response && f_response <= 0.2) {

                            textocalidadaire.setText("La media de calidad del aire\nde ayer es buena: 0" + formato1.format(f_response) + "\nCon zonas de máxima de " + valoralto);
                            colorestimacionaire.setVisibility(View.VISIBLE);
                            colorestimacionaire.setBackgroundColor(Color.parseColor("#65DF48"));

                        } else if (f_response > 0.2 && f_response <= 0.3) {
                            textocalidadaire.setText("La media de calidad del aire\nde ayer es regular: 0" + formato1.format(f_response) + "\nCon zonas de máxima de " + valoralto);
                            colorestimacionaire.setVisibility(View.VISIBLE);
                            colorestimacionaire.setBackgroundColor(Color.parseColor("#EFA356"));


                        } else if (f_response > 0.3 && f_response <= 0.4) {

                            textocalidadaire.setText("La media de calidad del aire\nde ayer es mala: 0" + formato1.format(f_response) + "\nCon zonas de máxima de " + valoralto);
                            colorestimacionaire.setVisibility(View.VISIBLE);
                            colorestimacionaire.setBackgroundColor(Color.parseColor("#ED8128"));


                        } else if (f_response > 0.4) {
                            textocalidadaire.setText("La media de calidad del aire\nde ayer es muy mala: 0" + formato1.format(f_response) + "\nCon zonas de máxima de " + valoralto);
                            colorestimacionaire.setVisibility(View.VISIBLE);

                            colorestimacionaire.setBackgroundColor(Color.parseColor("#E21212"));


                        } else {

                            textocalidadaire.setText("No hay medidas registradas de ayer.\n Fecha: " + fecha);
                            colorestimacionaire.setVisibility(View.INVISIBLE);

                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }

                });


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
        editorpreferencias.putString("idSensor", "");
        editorpreferencias.apply();
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
        this.detenerBusquedaDispositivosBTLE();


        startActivity(new Intent(this, LoginActivity.class));


    }







    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    /*
     * Configures the notification channel
     * createNotificationChannel()
     *
     *
     */
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    /*
     * Configures the notification parameters
     * createNotification()
     *
     *
     */
    private void createNotification(String texto, int notification_id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.icono1);
        builder.setContentTitle("Pollution Tracker");
        builder.setContentText(texto);
        builder.setColor(Color.GREEN);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(notification_id, builder.build());
    }

    private String estimarDistancia(ScanResult resultado){
        int dist = resultado.getRssi();
        //Toast.makeText(this, String.valueOf(dist), Toast.LENGTH_SHORT).show();
        Log.d("DISTANCIA", String.valueOf(dist));
        if(dist > -73){
            return "Cerca";
        } else if(dist < -73 && dist > -90){
            return "Media distancia";
        } else{
            return "Lejos";
        }
    }


    /*
     * Método para obtener la latitud y la longitud
     *
     * @param No le pasamos nada
     *
     * @return No devuelve nada
     */
    // --------------------------------------------------------------
    public void obtenerCoordenadas() {

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc==null){
                //Location wasnt gathered
            }else{
                latitud = loc.getLatitude();
                //txtLatitud.setText(String.valueOf(latitud));
                // txtLongitud.setText(String.valueOf(longitud));
                longitud = loc.getLongitude();
            }
        }
    }

    /*
     * Recoge los datos introducidos por el usuario y los manda por correo desde un hilo en segundo plano
     */
    private class CorreoSegundoPlano extends AsyncTask<String, Float, Integer> {

        @Override
        protected Integer doInBackground(String... variableNoUsada) {
            //Si el valor de contaminación recibido supera el umbral, se genera una notificacion
            /*     if(valorbeacondoubleAux > 0.3){
                createNotificationChannel();

                createNotification("Límite de calidad del aire excedido en (" + latitud+ ", " + longitud + ")", 1);
            }*/

            Log.d("ivan", "pepinillo");
            int j = 0;
            while(j==0){

                Log.d("holaaa", String.valueOf(valorbeacondoubleAux));

            }

            /*Mail m = new Mail("trackerpollutiongti@gmail.com", "liba fqga fujp kfaj");

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
                    UserArea.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(UserArea.this, "El correo ha sido enviado satisfactoreamente", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    UserArea.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(UserArea.this, "Error al mandar el correo: hay campos vacíos", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch(Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }*/
            return null;
        }




    }


}


