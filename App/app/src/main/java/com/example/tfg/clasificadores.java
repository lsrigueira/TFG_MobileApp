package com.example.tfg;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class clasificadores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasificadores);

        Button TSNEBtn = findViewById(R.id.LinearSVC);
        Button TrainMeBtn = findViewById(R.id.Logigistic);
        Button historialBtn = findViewById(R.id.Goback);




    }



}
