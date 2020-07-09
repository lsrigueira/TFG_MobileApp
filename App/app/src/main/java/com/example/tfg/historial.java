package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class historial extends AppCompatActivity  {

    String code = "<html><head><style> table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}"
            +"td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}"
            +"tr:nth-child(even){background-color: #dddddd;}"
            +"</style></head><body><h2>HTML Table</h2><table><tr><th>Hora</th><th>Nombre</th><th>Potencia</th><th>Etiqueta</th></tr>";

    String backupcode = "<html><head><style> table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}"
            +"td, th {border: 1px solid #dddddd;text-align: left;padding: 8px;}"
            +"tr:nth-child(even){background-color: #dddddd;}"
            +"</style></head><body><h2>HTML Table</h2><table><tr><th>Hora</th><th>Nombre</th><th>Potencia</th><th>Etiqueta</th></tr>"
            +"<tr><td>Alfreds Futterkiste</td><td>Maria Anders</td><td>Germany</td></tr><tr><td>Centro comercial Moctezuma</td><td>Francisco Chang</td><td>Mexico</td></tr>"
            +"<tr><td>Ernst Handel</td><td>Roland Mendel</td><td>Austria</td></tr><tr><td>Island Trading</td><td>Helen Bennett</td><td>UK</td></tr>"
            +"<tr><td>Laughing Bacchus Winecellars</td><td>Yoshi Tannamuri</td><td>Canada</td></tr>"
            +"<tr><td>Magazzini Alimentari Riuniti</td><td>Giovanni Rovelli</td><td>Italy</td></tr></table></body></html>";

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);

        Button Tomenu = findViewById(R.id.ToMenufromHistorial);
        Tomenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
                Connection.send("2");
                startActivity(myIntent);
                //r
            }
        });
        recivir();

    }

    public void  recivir(){

        final Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("*******************************************");
                System.out.println("*******************************************");
                System.out.println("*******************************************");

                String respuesta = Connection.receive();
                String[] porfilas = respuesta.split(";");
                System.out.println(porfilas.length);
                //restamoslle un porque o ultimo ven vacio en teoria
                for(int i = 0; i < porfilas.length; i++){
                    System.out.println(porfilas[i]);
                    //Aqui temos cada unha das filas
                    String[] campo = porfilas[i].split(",");
                    code = code +"<tr>";
                    for (int j = 0; j< campo.length; j++){
                        System.out.println(campo[j]);
                        code = code +"<td>"+campo[j]+"</td>";
                    }
                    code = code +"</tr>";
                }
                code = code + "</table></body></html>";

                final WebView myWebView = findViewById(R.id.webview);
                myWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        myWebView.loadDataWithBaseURL(null,code,"text/html", "utf-8",null);
                    }
                });
            }
        });
        receive.start();
    }



}
