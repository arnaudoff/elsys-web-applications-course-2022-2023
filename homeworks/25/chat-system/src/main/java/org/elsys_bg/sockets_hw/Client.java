package org.elsys_bg.sockets_hw;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {

    private static Scanner inputScanner = new Scanner(System.in);
    private static Socket clientSocket = new Socket();


    private static void startChat() {

        do {
            System.out.print("# ");
            String line = inputScanner.nextLine();
            String[] tokens = line.trim().split("\\s+");
            
            // Parse and send chat room commands
            switch(tokens[0]) {
                /* ----------------------------------------------------------------------------- */
                case "/msg":
                    if(tokens.length < 2) {
                        System.out.println("Invalid number or arguments!");
                        break;
                    }

                    try {
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        outToServer.writeBytes(line + '\n');
                        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    
                    break;

                /* ----------------------------------------------------------------------------- */
                case "/nick":
                    if(tokens.length != 2) {
                        System.out.println("Invalid number or arguments!");
                        break;
                    }

                    try {
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        outToServer.writeBytes(line + '\n');
                        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    
                    break;
                    
                /* ----------------------------------------------------------------------------- */
                case "/quit":
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;

                /* ----------------------------------------------------------------------------- */
                case "":
                    break;
                    
                /* ----------------------------------------------------------------------------- */
                default:
                    System.out.println("Invalid command!");
            }

        }while(true);
    }
    
    public static void main(String[] args) {

        System.out.println("Type /connect <ip of chat server>:<port> to connect to a chat room");
        
        do {
            System.out.print("> ");
            String line = inputScanner.nextLine();
            String[] tokens = line.trim().split("\\s+");
            
            // Parse global commands
            switch(tokens[0]) {
                /* ----------------------------------------------------------------------------- */
                case "/connect":
                    if(tokens.length != 2) {
                        System.out.println("Invalid number or arguments!");
                        break;
                    }
                    
                    String[] socket = tokens[1].trim().split(":");

                    try {
                        // clientSocket.connect(new InetSocketAddress(socket[0], Integer.parseInt(socket[1])));    
                        clientSocket = new Socket(socket[0], Integer.parseInt(socket[1]));
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        System.out.println(inFromServer.readLine());

                        (new Thread(() -> {
    
                            while(true) {
                                try {
                                    System.out.println('\r' + inFromServer.readLine());
                                } catch (IOException e) {
                                    return;
                                }
                            }
                            
                        })).start();

                    } catch (UnknownHostException e) {
                        System.out.println("Unknown hostname!");
                        break;
                    
                    } catch (ConnectException e) {
                        System.out.println("Invalid port!");
                        break;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    startChat();

                    break;
                
                /* ----------------------------------------------------------------------------- */
                case "/quit":
                    inputScanner.close();
                    return;

                /* ----------------------------------------------------------------------------- */
                case "":
                    break;

                /* ----------------------------------------------------------------------------- */
                default:
                    System.out.println("Invalid command!");
            }

        }while(true);
    }
}
