package com.example.sound_effect_generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /*Definiendo instancias*/
    private MediaRecorder grabacion;                // Instancia de la clase mediaRecorder para obtener audio
    private String archivoSalida = null;            // string que mantiene direcicon y nombre dle archivo de salida grabado
    private Button btn_record,btn_play;             // instancias de botones


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_record = findViewById(R.id.btn_rec);  // asignando boton rec
        btn_play = findViewById(R.id.btn_play);       // asignando boton play

        /*Se verifican permisos necesarios dentro del archivo manifest y se solicitan*/
        if (    ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

    // Metodo para llamar al activity que genera los efectos de sonido
    public void SoundsEffectsGeneratorActivity(View view){
       Intent NextActivity = new Intent(this, SoundsEffectsGeneratorActivity.class);
       startActivity(NextActivity);
    }


    /*Metodos comunes*/
    // Metodo para grabar audio
    public void recorder(View view){
        if(grabacion==null){   //si no se esta grabando audio, se procede a inicializar y grabar
            archivoSalida = getFilesDir().getAbsolutePath()+"/Grabacion.mp3";  // se setea el path y nombre de archivo de salida
            grabacion = new MediaRecorder();                                    // se inicializa la instancia MediaRecorder para usar el MIC
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);            // data fuente desde el microfono
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);    // Se setea el formato de salida 3gpp
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);       // Se setea el encoder con AMR, salida a mp3
            grabacion.setOutputFile(archivoSalida);                             // Se setea a la instancia del recorder el nombre y direccion de salida

            try {
                grabacion.prepare();                                            // se prepara
                grabacion.start();                                              // y se inicia

            }catch (IOException e ){
            }

            btn_record.setBackgroundResource(R.drawable.rec);      // se cambia el fondo del boton para mostrar que esta grabando
            Toast.makeText(getApplicationContext(),archivoSalida+" Grabando! ",Toast.LENGTH_SHORT).show();          // toast para indicar que esta en grabacion
        } else if(grabacion !=null){                                            // si, si se esta grabando y se apreto el boton, entonces se detiene la grabacion
            grabacion.stop();                                                   // se detiene la instancia dle recorder
            grabacion.release();                                                // y se libera su memoria usada de instancia
            grabacion = null;                                                   // Se coloca como null
            btn_record.setBackgroundResource(R.drawable.stop_rec);              // se devuelve el fondo del boton al estado inicial, no grabando
            Toast.makeText(getApplicationContext(),"Grabacion Terminada!",Toast.LENGTH_SHORT).show();   // se muestra toas de grabacion finalizada
        }
    }

    /*Metodo para reproducir el archivo grabado*/
    public void reproducir(View view) {

        if (archivoSalida == null) {
            Toast.makeText(getApplicationContext(), "No se ha grabado nada aun!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
        } else {
            MediaPlayer mediaPlayer = new MediaPlayer();                // instancia del mediaplayer
            try {
                mediaPlayer.setDataSource(archivoSalida);               // se busca el archivo a reproducir usando la ruta o path
                mediaPlayer.prepare();                                  // se prepara
            } catch (IOException e) {
            }
            mediaPlayer.start();                                        // se inicia la reproduccion
            Toast.makeText(getApplicationContext(), "Reproduciendo Audio!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
        }
    }
}