package com.example.tfg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
      //private  Connection con = new Connection("192.168.0.4",8888);

    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuario.inicializar(this);
        setContentView(R.layout.activity_main);
        Button trainAlgorithm = findViewById(R.id.Train_algorithm);
        Button TSNEBtn = findViewById(R.id.TSNE);
        Button TrainMeBtn = findViewById(R.id.Train_myself);
        Button historialBtn = findViewById(R.id.Historial);
        Button clasifiersBtn = findViewById(R.id.Clasificadores);
        Button logOutBtn = findViewById(R.id.LogOut);

        trainAlgorithm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                recivir("TrainAlgorithm");
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);
            }
        });

        TSNEBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                usuario.setSpeed(2);
                usuario.speak("COME ON");
            }
        });

        TrainMeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                recivir("TrainMe");
            }
        });

        clasifiersBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("5");
                recivir("Clasificadores");
            }
        });

        historialBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                probar();
            }
        });
    }

    public void probar(){
        //private TextToSpeech mtts = new TextToSpeech();
        //mtts.set
        System.out.println("HERE");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"hi speak something");
        //Discovery service = new Discovery("2020-05-03");
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RESULT RESULT RESULT");
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0));
                }
            }
        }
    }


    public void  recivir(final String GoTo){

        final Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = null;
                switch (GoTo.toLowerCase()){
                    case "trainalgorithm": //same as clasificadores
                        System.out.println("NEXT SCREEN GOLPES");
                        myIntent = new Intent(getBaseContext(),   golpes.class);
                        myIntent.putExtra("tipoResultado","trainAlgorithm");
                        break;
                    case "trainme":
                        myIntent = new Intent(getBaseContext(),   golpes.class);
                        myIntent.putExtra("tipoResultado","prediccion");

                        break;
                    case "clasificadores":
                        System.out.println("OLAAA");
                        myIntent = new Intent(getBaseContext(),   golpes.class);
                        myIntent.putExtra("tipoResultado","clasificacion");
                        break;
                    default:
                        System.out.println("NEXT SCREEN golpes");
                        myIntent = new Intent(getBaseContext(),   golpes.class);
                        break;

                }
                String respuesta = Connection.receive();
                if(respuesta.equals("ok")) {
                    System.out.println("ACEPTADO");
                    startActivity(myIntent);
                }
                return ;
            }
        });


        receive.start();
    }



}
