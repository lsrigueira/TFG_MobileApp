package com.example.tfg;

import android.content.Intent;
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
                recivir();
            }
        });
    }

    public void  recivir(){

        final Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
                String respuesta = Connection.receive();
                if(respuesta.equals("ok")){
                    System.out.println("ACEPTADO");
                    startActivity(myIntent);
                }else{
                    System.out.println("Recivimos outra cousa");
                }
                return ;
            }
        });
        receive.start();
    }

}