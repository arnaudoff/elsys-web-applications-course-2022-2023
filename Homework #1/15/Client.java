package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{

    private Socket client;
    private BufferedReader in;
    private BufferedReader in_conn;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run() {
        try{
            in_conn = new BufferedReader(new InputStreamReader(System.in));
            String message = in_conn.readLine();
            if(message.startsWith("/connect ")){
                String[] messageSplit = message.split(" ", 3);
                if(messageSplit.length == 3){
                    client = new Socket(messageSplit[1], Integer.valueOf(messageSplit[2]));
                    System.out.println("Connected!");
                }
            }

            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while((inMessage = in.readLine()) != null){
                System.out.println(inMessage);
            }
        }
        catch(IOException e){
            shutdown();
        }
    }

    public void shutdown(){
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
        }
        catch(IOException e){
            //ignore
        }
    }

    class InputHandler implements Runnable{

        @Override
        public void run() {
            try{
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String message = inReader.readLine();
                    if(message.equals("/quit")){
                        out.println(message);
                        inReader.close();
                        shutdown();
                    }
                    else{
                        out.println(message);
                    }
                }
            }
            catch(IOException e){
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
