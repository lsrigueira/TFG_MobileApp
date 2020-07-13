package com.example.tfg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;


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
                Connection.send("3");
                recivir("historial");
            }
        });
    }

    public void gosettings(View view){
        Intent myintent = new Intent(getBaseContext(), settings.class);
        startActivity(myintent);
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
                    case "historial":
                        myIntent = new Intent(getBaseContext(),   historial.class);
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
