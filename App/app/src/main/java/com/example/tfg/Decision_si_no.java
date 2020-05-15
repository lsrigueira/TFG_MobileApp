package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Decision_si_no  extends AppCompatActivity {

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
                //recivir();
            }
        });

        NoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(getNetxScreen(nextNo));
                //recivir();
            }
        });

    }

    public Intent getNetxScreen(String nextScreen){

        Intent myIntent = null;
        switch (nextScreen){
            case "resultados":

                myIntent = new Intent(getBaseContext(),   resultados_prediccion.class);
                break;
            case "MenuPrincipal":

                System.out.println("Seleccionando ir ó menu principal");
                myIntent = new Intent(getBaseContext(), MainActivity.class);
                break;
        }

        return myIntent;
    }

    public void  recivir(){

        final Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                String respuesta = Connection.receive();
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                System.out.println(respuesta);
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            }
        });
        receive.start();
    }
}

