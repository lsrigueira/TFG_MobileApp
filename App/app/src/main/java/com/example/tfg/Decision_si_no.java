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
        final String nextScreen = b.getString("nextscreen");

        Button NoBtn = findViewById(R.id.Decision_No);
        Button SiBtn = findViewById(R.id.Decision_Si);
        TextView titulo = findViewById(R.id.Texto_title);
        titulo.setText(textoaviso);

        SiBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                startActivity(getNetxScreen(nextScreen));
                //recivir();
            }
        });

        NoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                Intent myIntent = new Intent(getBaseContext(),   resultados_prediccion.class);
                startActivity(myIntent);
                //recivir();
            }
        });

    }

    public Intent getNetxScreen(String nextscreen){

        Intent myIntent = null;
        switch (nextscreen){
            case "resultados":
                myIntent = new Intent(getBaseContext(),   resultados_prediccion.class);
        }

        return myIntent;
    }
}

