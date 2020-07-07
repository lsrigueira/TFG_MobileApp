package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class golpes extends AppCompatActivity {

    String tiporesult;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golpes);
        Bundle b = getIntent().getExtras();
        tiporesult= b.getString("tipoResultado");
        System.out.println(tiporesult);
        System.out.println(tiporesult);
        System.out.println(tiporesult);
        System.out.println(tiporesult);
        Button backbtn = findViewById(R.id.Goback);
        Button crochetbtn = findViewById(R.id.Crochet);
        Button patadabtn = findViewById(R.id.Patada);

        if (usuario.getAudioCommands()){
            usuario.speak("Seleccione el golpe . . 1 Crochet . . 2 patada");
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

}
