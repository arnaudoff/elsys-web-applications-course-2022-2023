package org.example;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
        String clientAction = "";

        while(!clientAction.equals("/quit")){
            System.out.print("Enter wanted action: ");
            try {
                clientAction = clientInput.readLine();
            } catch(IOException exception){
                System.out.println("I/O error: " + exception.getMessage());
            }
            String[] separatedActionText = clientAction.split(" +");

            if(separatedActionText.length != 2){
                continue;
            }
            if(separatedActionText[0].equals("/connect")){
                String[] hostnameAndPort = separatedActionText[1].split(":");
                if(hostnameAndPort.length != 2){
                    continue;
                }

                try (Socket socket = new Socket(hostnameAndPort[0], Integer.parseInt(hostnameAndPort[1]))) {
                    System.out.println("Connected to server!");
                    OutputStream output = socket.getOutputStream();
                    DataOutputStream writer = new DataOutputStream(output);
                    InputStream input = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    while(!clientAction.equals("/quit")){
                        while(reader.ready()){
                            System.out.println(reader.readLine());
                        }
                        System.out.print("Enter action: ");
                        clientAction = clientInput.readLine();
                        if(clientAction.startsWith("/nick ") || clientAction.startsWith("/msg")){
                            writer.writeBytes(clientAction + "\n");
                        }
                    }
                } catch (UnknownHostException exception1) {
                    System.out.println("Server not found: " + exception1.getMessage());
                } catch (IOException exception2) {
                    System.out.println("I/O error: " + exception2.getMessage());
                }
            }
        }
    }
}