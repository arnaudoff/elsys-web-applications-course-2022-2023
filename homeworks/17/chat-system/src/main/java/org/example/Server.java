package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Objects;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        setServerSocket(serverSocket);
    }

    public static void main(String[] args) throws IOException {
        int port = 42069;
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        System.out.println("Port is " + port + ". Machkai tigre!");
        server.start();
    }

    private void setServerSocket(ServerSocket serverSocket) {
        if (Objects.isNull(serverSocket)) {
            throw new InvalidParameterException("Server socket cant be null");
        }
        this.serverSocket = serverSocket;
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println(clientHandler.getUsername() + " e vutre");

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (Objects.nonNull(serverSocket)) {
                serverSocket.close();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}