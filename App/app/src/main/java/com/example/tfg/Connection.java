package com.example.tfg;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public abstract class Connection extends AsyncTask<String,String,String> {
    static Thread sent;
    static Thread receive;
    static Socket socket;

    public static void start(){
        try {
            System.out.println("EXECUTANDO ESTOOOOOOOOOOOO");
            socket = new Socket(InetAddress.getByName("192.168.0.4"),8888);
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


    public static  void send(final String opcion){
        sent = new Thread(new Runnable() {
            Scanner sc = new Scanner(System.in);
            @Override
            public void run() {
                try {
                    //BufferedReader stdIn =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);;
                    out.print(opcion);
                    out.flush();
                } catch (IOException e) {
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
