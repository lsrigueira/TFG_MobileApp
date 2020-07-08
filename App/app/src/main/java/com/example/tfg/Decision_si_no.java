package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class Decision_si_no  extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decision_si_no);

        Bundle b = getIntent().getExtras();
        String textoaviso = b.getString("textoaviso");
        final String netxYes = b.getString("nextyes");
        final String nextNo = b.getString("nextno");

        Button NoBtn = findViewById(R.id.Decision_No);
        Button SiBtn = findViewById(R.id.Decision_Si);
        TextView titulo = findViewById(R.id.Texto_title);
        titulo.setText(textoaviso);

        SiBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                startActivity(getNetxScreen(netxYes));
            }
        });

        NoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(getNetxScreen(nextNo));
            }
        });
        if (usuario.getAudioCommands()){
            usuario.speak(textoaviso+". . 1 si . . 2 no");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listen();
                }
            },6000);
        }
    }

    public Intent getNetxScreen(String nextScreen){

        Intent myIntent = null;
        Bundle b = getIntent().getExtras();
        String tiporesult = b.getString("tipoResultado");
        switch (nextScreen){
            case "etiquetar":
                myIntent = new Intent(getBaseContext(),   etiquetargolpe.class);
                myIntent.putExtra("golpe",b.getString("contextoetiquetar"));
                break;
            case "clasificadores":
                myIntent = new Intent(getBaseContext(), clasificadores.class);
                myIntent.putExtra("tipoResultado",tiporesult);
                break;

            case "resultados":

                if (tiporesult.equals("clasificacion")){
                    myIntent = new Intent(getBaseContext(),   resultados_clasification.class);
                }else{
                    myIntent = new Intent(getBaseContext(),   resultados_prediccion.class);
                }
                myIntent.putExtra("tipoResultado",tiporesult);
                break;
            case "MenuPrincipal":

                System.out.println("Seleccionando ir ó menu principal");
                myIntent = new Intent(getBaseContext(), MainActivity.class);
                break;
        }

        return myIntent;
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
                    String textoaviso = b.getString("textoaviso");
                    final String netxYes = b.getString("nextyes");
                    final String nextNo = b.getString("nextno");
                    final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultado = result.get(0).toLowerCase();
                    System.out.println(resultado);
                    if(resultado.equals("uno") || resultado.equals("1") ){
                        Connection.send("1");
                        startActivity(getNetxScreen(netxYes));
                    }else if(resultado.equals("dos") || resultado.equals("2")){
                        Connection.send("2");
                        startActivity(getNetxScreen(nextNo));
                    }else{
                        usuario.speak("Repita por favor");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listen();
                            }
                        },6000);
                    }
                }
            }
        }
    }


}

