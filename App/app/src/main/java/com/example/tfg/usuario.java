package com.example.tfg;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class usuario {

    private static float pitch;
    private static float speed;
    private static String nombre;
    private static Boolean audioCommands;

    public static void setPitch(float pitch){
        usuario.pitch = pitch;
    }

    public static void setSpeed(float speed){
        usuario.speed = speed;
    }

    public static void setNome(String nombre){
        usuario.nombre = nombre;
    }
    public static void setGetVoz(Boolean voz){
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

}

