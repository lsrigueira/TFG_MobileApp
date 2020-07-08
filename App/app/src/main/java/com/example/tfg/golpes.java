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

import java.util.ArrayList;
import java.util.Locale;

public class golpes extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static Handler mHandler = new Handler();

    String tiporesult;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golpes);
        Bundle b = getIntent().getExtras();
        tiporesult= b.getString("tipoResultado");
        System.out.println(tiporesult);
        Button backbtn = findViewById(R.id.Goback);
        Button crochetbtn = findViewById(R.id.Crochet);
        Button patadabtn = findViewById(R.id.Patada);

        if (usuario.getAudioCommands()){
            usuario.speak("Seleccione el golpe . . 1 Crochet . . 2 patada");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            },4000);
        }

            crochetbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Connection.send("1");
                    recivir();
                }
            });

            patadabtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Connection.send("2");
                    recivir();
                }
            });

            backbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Connection.send("0");
                    recivir();
                }
            });

        }


    public void  recivir(){

        final Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                String respuesta = Connection.receive();
                Intent myIntent = null;
                switch (respuesta.toLowerCase()){
                    case "etiquetar-crochet":
                        myIntent = new Intent(getBaseContext(), etiquetargolpe.class);
                        myIntent.putExtra("golpe","crochet");
                        break;
                    case "etiquetar-patada":
                        myIntent = new Intent(getBaseContext(), etiquetargolpe.class);
                        myIntent.putExtra("golpe","patada");
                        break;
                    case "back":
                        myIntent = new Intent(getBaseContext(),   MainActivity.class);
                        break;
                    case "resultado":
                        System.out.println("OLEOLEOLEOLEOLEOLEOLE");
                        break;
                    case "clasificadores":
                        myIntent = new Intent(getBaseContext(),   clasificadores.class);
                        myIntent.putExtra("tipoResultado",tiporesult);
                        break;

                }
                startActivity(myIntent);
                return ;
            }
        });
        receive.start();
    }


    public void listen() {
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
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultado = result.get(0).toLowerCase();
                    System.out.println(resultado);
                    if(resultado.equals("uno") || resultado.equals("1")|| result.equals("crochet") ){
                        Connection.send("1");
                        recivir();
                    }else if(resultado.equals("dos") || resultado.equals("2") || result.equals("patada")){
                        Connection.send("2");
                        recivir();
                    }else{
                        usuario.speak("Repita por favor");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listen();
                            }
                        },3000);;
                    }
                }
            }
        }
    }

}
