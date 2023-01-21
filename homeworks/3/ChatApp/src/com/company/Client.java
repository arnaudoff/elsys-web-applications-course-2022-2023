package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private String ip ;
    private int port ;
    private boolean done;

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;


    public void shutdown() {
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(){
        BufferedReader inReader = new BufferedReader (new InputStreamReader (System. in));

        String message = null;
        try {
            message = inReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(message.startsWith("/connect")){
            String[] command = message.substring("/connect ".length()).split(":");

            ip = command[0];

            port = Integer.parseInt(command[1]);

        }
    }



    @Override
    public void run() {
        try{
            connect();
            client = new Socket(ip,port);
            out = new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            Thread t = new Thread(inputHandler);
            t.start();

            String inMessage;
            while((inMessage = in.readLine()) != null){
                System.out.println(inMessage);
            }

        }catch (Exception e){
            shutdown();
        }

    }

    class InputHandler implements Runnable{

        @Override
        public void run() {
            try{
                BufferedReader inReader = new BufferedReader (new InputStreamReader (System. in));
                while(!done){
                    String message = inReader.readLine();



                    if(message.equals("/quit")){
                        out.println(message);
                        inReader.close();
                        shutdown();
                        break;
                    }else {
                        out.println(message);

                    }
                }

            }catch (Exception e){
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

}
