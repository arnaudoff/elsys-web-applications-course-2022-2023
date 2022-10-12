package org.example;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class ServerThread extends Thread {
    private Socket socket;
    private static Random random = new Random();
    private String nickname = "Person" + random.nextInt();
    private static final HashSet<Socket> clientSockets = new HashSet<>();

    public ServerThread(Socket socket) {
        this.socket = socket;
        synchronized (clientSockets){
            clientSockets.add(socket);
        }
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String clientAction;

            while(true) {
                clientAction = reader.readLine();
                if(clientAction == null){
                    break;
                }
                if(clientAction.startsWith("/nick ")){
                    nickname = clientAction.substring(6);
                } else if(clientAction.startsWith("/msg ")){
                    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
                    String msg = "[" + timeFormatter.format(new Date()) + "] " + nickname + " " + clientAction.substring(5);
                    synchronized (clientSockets){
                        for(Socket clientSocket : clientSockets){
                            if(clientSocket.equals(socket)) {
                                continue;
                            }
                            OutputStream output = clientSocket.getOutputStream();
                            DataOutputStream writer = new DataOutputStream(output);
                            writer.writeBytes(msg + "\n");
                        }
                    }
                }
            }
            synchronized (clientSockets){
                clientSockets.remove(socket);
            }
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
