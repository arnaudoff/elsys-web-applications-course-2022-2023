package org.example;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        int port = 22222;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected!");
                new ServerThread(socket).start();
            }
        } catch (IOException exception) {
            System.out.println("Server exception: " + exception.getMessage());
        }
    }
}
