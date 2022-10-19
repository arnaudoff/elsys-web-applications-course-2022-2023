package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    private int port;
    private String IP;


    @Override
    public void run() {
        while(true){
            try{

                if (!getDataConnection()){
                    throw new IOException("Wrong entry");

                }
                client = new Socket(IP, port);

                out = new PrintWriter( client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                InputHandler inputHandler = new InputHandler();
                Thread t = new Thread(inputHandler);
                t.start();

                String inputMessage;
                while((inputMessage = in.readLine())!= null){
                    System.out.println(inputMessage);
                }

            } catch (IOException e) {
                shutdown();
                break;
            }
        }

    }

    private boolean getDataConnection() throws IOException{
        String command;
        in = new BufferedReader(new InputStreamReader(System.in));
        command = in.readLine();
        if (command.startsWith("/connect")){
            String[] splitData =command.split(" ", 3);
            if (splitData.length == 3){
                IP = splitData[1];
                port = Integer.parseInt(splitData[2]);
                return true;

            }
            else throw new IOException("Wrong Entry");
        }
        return false;
    }


    public void shutdown(){
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class InputHandler implements Runnable{

        @Override
        public void run(){
            try{
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String message = inReader.readLine();
                    if (message.equals("/quit")){
                        out.println(message);
                        inReader.close();
                        shutdown();
                    }else{
                        out.println(message);
                    }

            } catch (Exception e) {
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
         Client client = new Client();
         client.run();
    }
}
