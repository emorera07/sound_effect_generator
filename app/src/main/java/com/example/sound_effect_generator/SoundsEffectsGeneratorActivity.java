package com.example.sound_effect_generator;

import androidx.appcompat.app.AppCompatActivity;

/*Enviroment y context*/
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;

/*Audio Framework*/
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;

/*Audio effect y procesado*/
import android.media.audiofx.AudioEffect;
import android.media.audiofx.AudioEffect.Descriptor;
import android.media.audiofx.EnvironmentalReverb;
import android.media.audiofx.PresetReverb;


import java.io.IOException;

public class SoundsEffectsGeneratorActivity extends AppCompatActivity {

    /*Para el SoundPool*/
    SoundPool sp;
    String archivoSalida;
    int sonido_ID, rec;

    /*Para el effect*/
    AudioEffect mReverb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds_effects_generator);


    /*instanciando el audioPool*/
    sp = new SoundPool(5, AudioManager.STREAM_MUSIC,1);
    sonido_ID = sp.load(this,R.raw.start,1);
    archivoSalida = getFilesDir().getAbsolutePath()+"/Grabacion.mp3";
    rec = sp.load(archivoSalida,1);
    }

    /*Metodo de botón de retorno*/
    public void MainActivity(View view){
        Intent BeforeActivity = new Intent(this, MainActivity.class);
        startActivity(BeforeActivity);
    }


    /*Metodo para reproducir el sonido con efecto ballena*/
    public void playOriginal(View view){
        sp.autoPause();
        sp.play(rec,1,1,1,0,1.0f);
        Toast.makeText(getApplicationContext(),"Reproduciendo Original",Toast.LENGTH_SHORT).show();         // toast para indicar que esta en reproduccion

    }

    /*Metodo para reproducir el sonido con efecto ballena*/
    public void playBallena(View view){
        sp.autoPause();
        sp.play(rec,1,1,1,0,0.50f);
        Toast.makeText(getApplicationContext(),"Ballena!",Toast.LENGTH_SHORT).show();         // toast para indicar que esta en reproduccion

    }

    /*Metodo para reproducir el sonido con efecto Ardilla*/
    public void playArdilla(View view){
        sp.autoPause();
        sp.play(rec,1,1,1,0,1.5f);
        Toast.makeText(getApplicationContext(),"Ardilla!",Toast.LENGTH_SHORT).show();          // toast para indicar que esta en reproduccion

    }


    /*Metodo para efecto Reverb*/
    public void reverb_effect(View view){
        if (archivoSalida == null) {
                Toast.makeText(getApplicationContext(), "No se ha grabado nada aun!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
            } else {
                MediaPlayer mediaPlayer = new MediaPlayer();                // instancia del mediaplayer
                mediaPlayer.setAudioSessionId(1212);
                PresetReverb presetReverb = new PresetReverb(1, mediaPlayer.getAudioSessionId());
                presetReverb.setPreset(presetReverb.PRESET_SMALLROOM);
                presetReverb.setEnabled(true);

                //mediaPlayer.setAuxEffectSendLevel(1.0f);
                mReverb = presetReverb;

                try {
                    mediaPlayer.setDataSource(archivoSalida);               // se busca el archivo a reproducir usando la ruta o path
                    mediaPlayer.attachAuxEffect(presetReverb.getId());
                    mediaPlayer.setAuxEffectSendLevel(1.0f);
                    mediaPlayer.prepare();
                                                      // se prepara
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "No se encontró el archivo Audio Reverb!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
                }
                mediaPlayer.start();                                        // se inicia la reproduccion
                Toast.makeText(getApplicationContext(), "Reproduciendo Audio Reverb!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
            }
        }



    /*Metodo para efecto Eco*/
    public void Eco_effect(View view){
        if (archivoSalida == null) {
            Toast.makeText(getApplicationContext(), "No se ha grabado nada aun!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
        } else {
            MediaPlayer mediaPlayer = new MediaPlayer();                // instancia del mediaplayer
            mediaPlayer.setAudioSessionId(1212);
            PresetReverb presetReverb = new PresetReverb(1, mediaPlayer.getAudioSessionId());
            presetReverb.setPreset(presetReverb.PRESET_LARGEHALL);
            presetReverb.setEnabled(true);

            //mediaPlayer.setAuxEffectSendLevel(1.0f);
            mReverb = presetReverb;

            try {
                mediaPlayer.setDataSource(archivoSalida);               // se busca el archivo a reproducir usando la ruta o path
                mediaPlayer.attachAuxEffect(presetReverb.getId());
                mediaPlayer.setAuxEffectSendLevel(1.0f);
                mediaPlayer.prepare();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "No se encontró el archivo Audio Echo!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
            }
            mediaPlayer.start();                                        // se inicia la reproduccion
            Toast.makeText(getApplicationContext(), "Reproduciendo Audio Echo!", Toast.LENGTH_SHORT).show();       // se muestra toast para indicar que esta reproduciendo
        }
    }
}