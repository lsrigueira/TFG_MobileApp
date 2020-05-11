package com.example.tfg;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class logIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Thread init = new Thread() {
            @Override
            public void run() {
                try {
                    Connection.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        init.start();

        Button ToMyUser = findViewById(R.id.loginbtn);
        ToMyUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Connection.send("1");
                Connection.send("pruebajavier");

                setContentView(R.layout.activity_main);
            }
        });

    }



}
