package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class resultados_prediccion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados_prediccion);
        //PETA PORQUE ME DA ESTO COMO NULL
        Button Tomenu = findViewById(R.id.ToMenu);
        recivir();


        Tomenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
                myIntent.putExtra("textoaviso","Desexa volver a golpear ?");
                myIntent.putExtra("nextyes","resultados");
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
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                System.out.println(respuesta);
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            }
        });
        receive.start();
    }

}
