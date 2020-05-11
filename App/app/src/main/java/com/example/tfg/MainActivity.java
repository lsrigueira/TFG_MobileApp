package com.example.tfg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
      //private  Connection con = new Connection("192.168.0.4",8888);

    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button TSNEBtn = findViewById(R.id.TSNE);
        Button TrainMeBtn = findViewById(R.id.LogOut);
        Button historialBtn = findViewById(R.id.Historial);
        Button clasifiersBtn = findViewById(R.id.Clasificadores);
        Button logOutBtn = findViewById(R.id.LogOut);


        TrainMeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                conn.execute("2");
            }
        });

        clasifiersBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                conn.execute("5");
            }
        });

        TrainMeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                probar();
            }
        });

    }

    public void probar(){
        //private TextToSpeech mtts = new TextToSpeech();
        //mtts.set
        System.out.println("HERE");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"hi speak something");
        //Discovery service = new Discovery("2020-05-03");
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }catch (Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("RESULT RESULT RESULT");
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0));
                }
            }
        }
    }
}
