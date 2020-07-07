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
            }
        });

        NoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(getNetxScreen(nextNo));
            }
        });
        if (usuario.getAudioCommands()){
            usuario.speak(textoaviso);
            usuario.speak("1 si . . 2 no");
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


}

