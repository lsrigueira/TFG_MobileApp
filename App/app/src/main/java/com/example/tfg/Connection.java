package com.example.tfg;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public abstract class Connection extends AsyncTask<String,String,String> {
    static Socket socket;
    static Thread sent;
    static Thread receive;
    static PrintWriter out;
    static BufferedReader in;

    public static void start(){
        try {
            System.out.println("EXECUTANDO ESTOOOOOOOOOOOO");
            socket = new Socket(InetAddress.getByName("192.168.0.5"),8888);
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
