package Server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.ArrayList;

class Server {

        private ServerSocket serverSocket;
        private ArrayList clients = new ArrayList<>();
        // private HashMap clients<String, IP>

        public static void main(String argv[]) throws Exception {
                Server server = new Server();
                server.start((short)1210);
        }

        private void start(short serverPort) {
                try {
                        serverSocket = new ServerSocket(serverPort);
                        System.out.println("Listening on port " + serverPort);

                        while (serverSocket.isClosed() == false) {
                                Socket clientSocket = serverSocket.accept();
                                System.out.println("Established connection with " +
                                                clientSocket.getInetAddress().getHostAddress() +
                                                ":" + clientSocket.getPort());

                                clients.add(clientSocket);
                                // start thread
                        }
                } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
                }
        }

}
