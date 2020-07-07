package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

        import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class etiquetargolpe extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.etiquetargolpe);

        Button derecha = findViewById(R.id.Derecha);
        Button frontal = findViewById(R.id.Frontal);
        Button izquierda = findViewById(R.id.Izquierda);
        Bundle b = getIntent().getExtras();
        final Intent myIntent = new Intent(getBaseContext(),   Decision_si_no.class);
        myIntent.putExtra("textoaviso","\"Desexa seguir clasificando??\"");
        myIntent.putExtra("nextyes","etiquetar");
        myIntent.putExtra("nextno","MenuPrincipal");
        myIntent.putExtra("contextoetiquetar",b.getString("golpe"));

        if(usuario.getAudioCommands()){
            if (!b.getString("golpe").equals("patada")){
                usuario.speak("¿Como fue el golpe? . . . 1 derecha . . 2 izquierda . . 3 frontal");
            }else{
                usuario.speak("¿Como fue el golpe? . . . 1 derecha . . 2 izquierda");
            }
        }
        if (!b.getString("golpe").equals("patada")){
            frontal.setText("No disponible");
            frontal.setEnabled(false);
        }
        derecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                startActivity(myIntent);
            }
        });

        izquierda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                startActivity(myIntent);
            }
        });

        frontal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("3");
                startActivity(myIntent);
            }
        });

    }

}