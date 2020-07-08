package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

import java.util.ArrayList;
import java.util.Locale;

public class etiquetargolpe extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etiquetargolpe);

        Button derecha = findViewById(R.id.Derecha);
        Button frontal = findViewById(R.id.Frontal);
        Button izquierda = findViewById(R.id.Izquierda);
        Bundle b = getIntent().getExtras();
        final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
        myIntent.putExtra("textoaviso","\"Desexa seguir clasificando??\"");
        myIntent.putExtra("nextyes","etiquetar");
        myIntent.putExtra("nextno","MenuPrincipal");
        myIntent.putExtra("contextoetiquetar",b.getString("golpe"));

        if(usuario.getAudioCommands()){
            if (b.getString("golpe").equals("patada")){
                usuario.speak("¿Como fue el golpe? . . . 1 derecha . . 2 izquierda . . 3 frontal");
            }else{
                usuario.speak("¿Como fue el golpe? . . . 1 derecha . . 2 izquierda");
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            },6000);;
        }
        if (!b.getString("golpe").equals("patada")){
            frontal.setText("No disponible");
            frontal.setEnabled(false);
        }
        derecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                startActivity(myIntent);
            }
        });

        izquierda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(myIntent);
            }
        });

        frontal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("3");
                startActivity(myIntent);
            }
        });
    }

    public void listen(){
        //private TextToSpeech mtts = new TextToSpeech();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Welcome to the TFG App");
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RESULT RESULT RESULT");
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null!=data){
                    Bundle b = getIntent().getExtras();
                    final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
                    myIntent.putExtra("textoaviso","\"Desexa seguir clasificando??\"");
                    myIntent.putExtra("nextyes","etiquetar");
                    myIntent.putExtra("nextno","MenuPrincipal");
                    myIntent.putExtra("contextoetiquetar",b.getString("golpe"));
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultado = result.get(0).toLowerCase();
                    System.out.println(resultado);
                    if(resultado.equals("uno") || resultado.equals("1") ){
                        Connection.send("1");
                        ;
                    }else if(resultado.equals("dos") || resultado.equals("2")){
                        Connection.send("2");

                    }else if(resultado.equals("tres") || resultado.equals("3")){
                        Connection.send("3");
                        ;
                    }else{
                        usuario.speak("Repita por favor");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listen();
                            }
                        },3000);
                    }
                    startActivity(myIntent);
                }
            }
        }
    }

}