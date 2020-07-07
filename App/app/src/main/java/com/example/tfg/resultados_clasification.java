package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;


public class resultados_clasification extends  AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados_prediccion);
        //PETA PORQUE ME DA ESTO COMO NULL
        Button Tomenu = findViewById(R.id.ToMenuclass);
        recivir();

        Tomenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
                Connection.send("2");
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
                //Potencia, PorcentaxeGolpe, Porcentaxe etiqueta, etiqueta
                final String[] alldata = respuesta.split(",");
                System.out.println(alldata[alldata.length-1]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.fitTimeavalue)).setText(alldata[0]);
                        ((TextView)findViewById(R.id.ScoreTimeValue)).setText(alldata[1]);
                        ((TextView)findViewById(R.id.Accuracyvalue)).setText(alldata[2]);
                    }
                });
            }
        });
        receive.start();
    }



}
