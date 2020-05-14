package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class golpes extends AppCompatActivity {

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golpes);

        Button backbtn = findViewById(R.id.Goback);
        Button crochetbtn = findViewById(R.id.Crochet);
        Button patadabtn = findViewById(R.id.Patada);

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
                    case "back":
                        myIntent = new Intent(getBaseContext(),   MainActivity.class);
                        break;
                    case "resultado":
                        System.out.println("OLEOLEOLEOLEOLEOLEOLE");
                        break;
                    case "clasificadores":
                        myIntent = new Intent(getBaseContext(),   clasificadores.class);
                        break;

                }
                startActivity(myIntent);
                return ;
            }
        });
        receive.start();
    }

}
