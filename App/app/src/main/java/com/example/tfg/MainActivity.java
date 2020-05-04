package com.example.tfg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ibm.watson.text_to_speech.v1.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private class Watson extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.Train_myself);
        btn.setOnClickListener(new View.OnClickListener() {
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
