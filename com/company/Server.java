package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    public Server() {
        this.connections = new ArrayList<>();
        this.done = false;
    }

    @Override
    public void run(){
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while(!done){
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute( handler);

            }
        } catch (IOException e) {
        shutdown();
        }
    }

    public void broadcast (String message){
        for (ConnectionHandler ch: connections){
            if (ch != null){
                ch.sendMessage((message));
            }
        }
    }

    private void shutdown(){
        try{
            done = true;
            pool.shutdown();

            if (!server.isClosed()){
                server.close();
            }
            for (ConnectionHandler ch: connections){
                ch.shutdown();
            }
        } catch (IOException e) {
      
        }
    }

    class ConnectionHandler implements Runnable{


        private Socket client;
        private BufferedReader input;
        private PrintWriter output;
        private String nickname;


        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run(){
            try {
                output = new PrintWriter(client.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      
                output.println("Please enter nickname: ");
                nickname = input.readLine();
                System.out.println(nickname + " connected");
                broadcast(nickname + " joined the chat!");
                String message;
                while ( (message = input.readLine()) != null){
                    if(message.startsWith("/nick " )){
                        String[] messageSplit = message.split(" ",2);
                        if (messageSplit.length == 2){
                            broadcast(nickname + " renamed themselves to " + messageSplit[1]);
                            System.out.println(nickname + " renamed themselves to " + messageSplit[1]);
                            nickname = messageSplit[1];
                            output.println("Successfully changed nickname to "+ nickname);
                        }  else{
                            output.println("No nickname provided!");
                        }
                    }else if (message.startsWith("/quit")){
                        broadcast(nickname + " left the chat!");
                        shutdown();
                    }else if (message.startsWith("/msg ")){
                        broadcast(nickname + ": " + message.replace("/msg ", ""));
                    }

                }
            } catch (IOException e) {
                shutdown();
            }
        }



        public void sendMessage(String message){
            output.println(message);
        }

        public  void shutdown( ){
            try{
                input.close();
                output.close();
                if(!client.isClosed()){
                    client.close();
                }
            } catch (IOException e) {
//                ignore
            }

        }
        
    }public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}
