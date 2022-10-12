package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class Client implements Runnable {
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private boolean done;

    @Override
    public void run() {
        try {
            do{
                try{
                    System.out.println("Use '/connect ip port' in order to connect to server!");
                    input = new BufferedReader(new InputStreamReader(System.in));
                    String message = input.readLine();
                    String[] messageSplit = message.split(" ");
                    if (messageSplit.length == 3 && messageSplit[0].equals("/connect")) {
                        client = new Socket(messageSplit[1], Integer.valueOf(messageSplit[2]));
                    }
                }catch(Exception e)
                {
                    //ignore
                }
            }while(client == null);
            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            Thread t = new Thread(inputHandler);
            t.start();

            String inputMessage;
            while((inputMessage = input.readLine()) != null){
                System.out.println(inputMessage);

            }
        } catch (IOException e) {
            //ignore
        }

    }

    public void shutdown() {
        done = true;
        try {
            input.close();
            output.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            //ignore
        }
    }

    class InputHandler implements Runnable {


        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inputReader.readLine();
                    if (message.equals("/quit")) {
                        output.println(message);
                        inputReader.close();
                        shutdown();
                    } else {
                        output.println(message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
