package com.company;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable {
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a", Locale.ENGLISH);

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(6789);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
            shutdown();
        }
    }

    public void broadcast(String nickname, String message) {
        for (ConnectionHandler ch : connections) {
            if (ch != null && ch.nickname != nickname) {
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections){
                ch.shutdown();
            }
        } catch (IOException e) {
            //ignore
        }
    }

    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader input;
        private PrintWriter output;
        private String nickname;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                output = new PrintWriter(client.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader((client.getInputStream())));
                output.println("Please enter a nickname: ");
                nickname = input.readLine();
                System.out.println(nickname + " connected!");
                broadcast(nickname, nickname + " joined the chat!");
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.startsWith("/nick ")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname, nickname + " set their nickname to '" + messageSplit[1] + "'");
                            System.out.println(nickname + " set their nickname to '" + messageSplit[1] + "'");
                            nickname = messageSplit[1];
                            output.println("Successfully changed nickname to " + nickname);
                        } else {
                            output.println("No nickname provided!");
                        }
                    } else if (message.equals("/quit")) {
                        System.out.println(nickname + " left the chat!");
                        broadcast(nickname,nickname + " left the chat!");
                        shutdown();
                    } else if (message.startsWith("/msg ")) {
                        String[] messageSplit = message.split(" ", 2);
                        broadcast(nickname,nickname + ": " + messageSplit[1]);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void sendMessage(String message) {
            output.println("[" + LocalDateTime.now().format(formatter) + "] " + message);
        }

        public void shutdown() {
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
    }

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}




