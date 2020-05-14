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
        Button historialBtn = findViewById(R.id.Goback);


        linearBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("1");
                Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
                startActivity(myIntent);
                //recivir();
            }
        });

        logisticBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Connection.send("2");
                Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
                startActivity(myIntent);
                //recivir();
            }
        });

    }



}
