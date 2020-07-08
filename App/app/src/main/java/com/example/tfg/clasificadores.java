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

public class clasificadores extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasificadores);

        Button linearBtn = findViewById(R.id.LinearSVC);
        Button logisticBtn = findViewById(R.id.Logigistic);
        Button rfbutton = findViewById(R.id.RandomForest);
        Button knnbutton = findViewById(R.id.KNN);

        final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);

        myIntent.putExtra("textoaviso","\"Desexa eliminar o Overfittin (Recomendado)?\"");
        Bundle b = getIntent().getExtras();
        myIntent.putExtra("nextyes","resultados");
        myIntent.putExtra("nextno","resultados");
        myIntent.putExtra("tipoResultado",b.getString("tipoResultado"));

        if (usuario.getAudioCommands()){
            usuario.speak("1 Linear . . 2 Regresor Logistico . . 3 Random forest . . 4 CA ene ene");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            },7000);
        }



        linearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                startActivity(myIntent);
            }
        });

        logisticBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(myIntent);
            }
        });

        rfbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("3");
                startActivity(myIntent);
            }
        });

        knnbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("4");
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

                    final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
                    myIntent.putExtra("textoaviso","\"Desexa eliminar o Overfittin (Recomendado)?\"");
                    Bundle b = getIntent().getExtras();
                    myIntent.putExtra("nextyes","resultados");
                    myIntent.putExtra("nextno","resultados");
                    myIntent.putExtra("tipoResultado",b.getString("tipoResultado"));
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultado = result.get(0).toLowerCase();
                    System.out.println(resultado);
                    if(resultado.equals("uno") || resultado.equals("1") ){
                        Connection.send("1");

                    }else if(resultado.equals("dos") || resultado.equals("2")){
                        Connection.send("2");
                    }else if(resultado.equals("tres") || resultado.equals("3")){
                        Connection.send("3");
                    }else if(resultado.equals("cuatro") || resultado.equals("4")){
                        Connection.send("4");
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
