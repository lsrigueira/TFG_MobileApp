package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

public class resultados_prediccion extends AppCompatActivity {

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
                System.out.println(respuesta);
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            }
        });
        receive.start();
    }

}
