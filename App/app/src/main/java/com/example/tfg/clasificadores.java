package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class clasificadores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasificadores);

        Button linearBtn = findViewById(R.id.LinearSVC);
        Button logisticBtn = findViewById(R.id.Logigistic);
        Button rfbutton = findViewById(R.id.RandomForest);
        Button knnbutton = findViewById(R.id.KNN);

        final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);

        myIntent.putExtra("textoaviso","\"Desexa eliminar o Overfittin (Recomendado)?\"");
        Bundle b = getIntent().getExtras();
        myIntent.putExtra("nextyes","resultados");
        myIntent.putExtra("nextno","resultados");
        myIntent.putExtra("tipoResultado",b.getString("tipoResultado"));

        if (usuario.getAudioCommands()){
            usuario.speak("1 Linear . . 2 Regresor Logistico . . 3 Random forest . . 4 CA ene ene");
        }

        linearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                startActivity(myIntent);
            }
        });

        logisticBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(myIntent);
            }
        });

        rfbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("3");
                startActivity(myIntent);
            }
        });

        knnbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("4");
                startActivity(myIntent);
            }
        });

    }



}
