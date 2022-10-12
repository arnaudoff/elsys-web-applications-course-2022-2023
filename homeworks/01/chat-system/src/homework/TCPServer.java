package homework;

import java.io.*;
import java.net.*;

class TCPServer {

    private ServerSocket serverSocket;

    public TCPServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    public void startServer() throws IOException {
        while(!serverSocket.isClosed()){
            Socket connectionSocket = serverSocket.accept();
            System.out.println("New connection");
            ClientHandler clientHandler = new ClientHandler(connectionSocket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }
    public static void main(String argv[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(1234);
        TCPServer server = new TCPServer(welcomeSocket);
        server.startServer();
    }
}
