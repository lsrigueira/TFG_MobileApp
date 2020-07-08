package com.example.tfg;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class usuario {

    private static float pitch;
    private static float speed;
    private static String nombre;
    private static Boolean audioCommands;
    private static TextToSpeech mTTS;
    private static Handler mHandler = new Handler();
    public static void setPitch(float pitch){
        usuario.pitch = pitch;
    }

    public static void setSpeed(float speed){
        usuario.speed = speed;
    }

    public static void setNome(String nombre){
        usuario.nombre = nombre;
    }
    public static void setAudioCommands(Boolean voz){
        usuario.audioCommands = voz;
    }

    public static String getNome(){
        return usuario.nombre;
    }

    public static Boolean getAudioCommands(){
        return usuario.audioCommands;
    }

    public static float getPitch(){
        return usuario.pitch;
    }

    public static float getSpeed(){
        return usuario.speed;
    }

    public static void inicializar(Context contexto){
        mTTS = new TextToSpeech(contexto, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                        Locale locSpanish = new Locale("spa", "ESP");
                    int result = mTTS.setLanguage(locSpanish);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    public static void speak(String text) {
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               return;
            }
        }, 20000);
    }

}

