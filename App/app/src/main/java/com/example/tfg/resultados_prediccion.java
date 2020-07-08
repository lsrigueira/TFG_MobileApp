package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class resultados_prediccion extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados_prediccion);
        //PETA PORQUE ME DA ESTO COMO NULL
        Button Tomenu = findViewById(R.id.ToMenu);
        recivir();

        findViewById(R.id.left).setBackgroundResource(R.drawable.rectanglered);
        findViewById(R.id.right).setBackgroundResource(R.drawable.rectanglered);
        findViewById(R.id.up).setBackgroundResource(R.drawable.rectanglered);
        Tomenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
                myIntent.putExtra("textoaviso","Desexa seguir clasificando ?");
                myIntent.putExtra("nextyes","clasificadores");
                myIntent.putExtra("tipoResultado","prediccion");
                myIntent.putExtra("nextno","MenuPrincipal");
                startActivity(myIntent);
                //r
            }
        });

    }


    public void  recivir(){

        final Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                String respuesta = Connection.receive();
                System.out.println("************NEW VALUE*****************");
                System.out.println(respuesta);
                //This is just to synchronize
                if (respuesta.equals("clasificadores")){
                    respuesta = Connection.receive();
                }
                System.out.println("***********END VALUE******************");
                //Potencia, PorcentaxeGolpe, Porcentaxe etiqueta, etiqueta
                final String[] alldata = respuesta.split(",");
                System.out.println(alldata[alldata.length-1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.potenciavalue)).setText(alldata[0]);
                        ((TextView)findViewById(R.id.potgolpvalue)).setText(alldata[1]);
                        ((TextView)findViewById(R.id.pottagvalue)).setText(alldata[2]);
                        if(usuario.getAudioCommands()){
                            usuario.speak("Potencia Bruta . . "+alldata[0]+". . .Porcentage de potencia segun el golpe . ."+alldata[1]+". . . Porcentage de potencia segun la etiqueta . ."+alldata[2]+". . Diga continuar para continuar");
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listen();
                                }
                            },3000);
                        }
                    }
                });
                if(alldata[alldata.length-1].equals("Esquerda")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.left).setBackgroundResource(R.drawable.rectangle);
                        }
                    });

                }else if( alldata[alldata.length-1].equals("Dereita")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.right).setBackgroundResource(R.drawable.rectangle);
                        }
                    });
                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.up).setBackgroundResource(R.drawable.rectangle);
                        }
                    });
                }
            }
        });
        receive.start();
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
                    Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
                    myIntent.putExtra("textoaviso","Desexa seguir clasificando ?");
                    myIntent.putExtra("nextyes","clasificadores");
                    myIntent.putExtra("tipoResultado","prediccion");
                    myIntent.putExtra("nextno","MenuPrincipal");
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String resultado = result.get(0).toLowerCase();
                    System.out.println(resultado);
                    if(resultado.equals("continuar")){
                        Connection.send("1");
                        startActivity(myIntent);
                    }else{
                        usuario.speak("Repita por favor");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listen();
                            }
                        },4000);
                    }
                }
            }
        }
    }


}
