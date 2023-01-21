package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        try (var serverSocket = new ServerSocket(port)){
            System.out.println("Server is listening on port \033[33;3m" + port + "\033[0m");
            // System.out.println("Server is listening on port " + port);

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " \033[32mconnected\033[0m");
                // System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " connected");

                new ServerThread(socket).start();
            }
        } catch (IOException err) {
            System.out.println("Server error: " + err.getMessage());
            err.printStackTrace();
        }
    }
}