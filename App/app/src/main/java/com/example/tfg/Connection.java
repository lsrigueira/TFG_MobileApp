package com.example.tfg;

import android.content.Context;
import android.os.AsyncTask;
import android.util.ArrayMap;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibm.cloud.sdk.core.http.HttpClientSingleton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.*;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public abstract class Connection extends AsyncTask<String,String,String> {
    static Socket socket;
    static Thread sent;
    static Thread receive;
    static PrintWriter out;
    static BufferedReader in;

    static RequestQueue queue;

    public static String getPublicIP() {
        String ip = "";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

             ip = in.readLine(); //you get the IP as a String
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("ip:"+ip);
        return ip;
    }

    public static void connectBD(Context contexto){

        System.out.println("Vamos a conectarnos o servidor estático");
        queue = Volley.newRequestQueue(contexto);
        String url = "http://192.168.0.5:8000";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Succesfull");
                        System.out.println(response);
                        Thread init = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Connection.start(response);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        init.start();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("ERROR O CONECTARSE POR GET O SERVIDOR ESTATICO");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("Content-Length","11");
                hashMap.put("Contentresponse-Type","application/x-www-form-urlencoded");
                hashMap.put("Accept","*/*");
                hashMap.put("IPPublica",Connection.getPublicIP());
                return hashMap;
            }
        };
// Add the request to the RequestQueue.
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 60000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queue.add(stringRequest);

    }

    public static void start(String ip){

        try {
            System.out.println("EXECUTANDO ESTOOOOOOOOOOOO");
            socket = new Socket(InetAddress.getByName(ip),8888);
            Connection.out = new PrintWriter(socket.getOutputStream(), true);
            in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public static String receive(){
        String retorno = null;
        try{
            System.out.println("Esperando input do socket....");
            retorno = Connection.in.readLine();
            //Thread.sleep(300);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Chegou: "+retorno);
        return retorno;
    }

    public static  void send(final String opcion){
        sent = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Connection.out.print(opcion);
                    Connection.out.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        sent.start();
    }

    protected String doInBackground(String... strings) {

        System.out.println("ESTAMOS IMPRIMINDO ESTO!!!!!");
        System.out.println("###################################");
        System.out.println(strings);
        System.out.println("###################################");
        for(String arg : strings){
            System.out.println(arg);
        }
        System.out.println("###################################");
        return "null";
    }
}
