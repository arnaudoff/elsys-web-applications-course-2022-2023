package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connectionHandlers;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private int port = 9999;

    public Server() {
        connectionHandlers = new ArrayList<>();
        done = false;
    }



    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server created at port " + port + "!");

            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connectionHandlers.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public void broadcast(String message){
        for(ConnectionHandler ch : connectionHandlers){
            if(ch != null){
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown(){
        try {
            done = true;
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connectionHandlers) {
                ch.shutdown();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ConnectionHandler implements Runnable{

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                //out.println("Enter nickname: ");
                //nickname = in.readLine();

                String message;
                while((message = in.readLine()) != null){
                    if(message.startsWith("/nick")){
                        nickname = message.substring("/nick ".length());
                        System.out.println(nickname + " connected!");
                        broadcast(nickname + " joined!");

                    }else if (message.startsWith("/quit")){
                        broadcast(nickname + " quit!");
                        shutdown();

                    }else if(message.startsWith("/msg")){
                        if(nickname == null){
                            //System.out.println("Set nickname!");
                            continue;
                        }
                        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm");
                        String time = formatDate.format(new Date());
                        message = message.substring("/msg ".length());
                        broadcast("["+time+"] " + nickname + ": " + message);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                shutdown();
            }

        }

        public void sendMessage(String message){
            out.println(message);

        }
        public void shutdown(){

            try {
                in.close ();
                out.close ();
                if (!client. isClosed());
                    client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

}


