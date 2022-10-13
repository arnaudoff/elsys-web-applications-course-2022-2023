package Server;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Server {

        private ServerSocket serverSocket;
        private short port;
        private List<Socket> clients;
        // private Map clients<String, IP>

        public Server(short port) {
                this.port = port;
                clients = new ArrayList<Socket>();
        }

        public static void main(String argv[]) throws Exception {
                Server server = new Server((short) 1210);
                server.start(server.port);
        }

        private void start(short serverPort) {
                try {
                        serverSocket = new ServerSocket(serverPort);
                        System.out.println("Listening on port " + serverPort);

                        while (serverSocket.isClosed() == false) {
                                Socket clientSocket = serverSocket.accept();
                                clientSocket.setSoTimeout(4 * 1000);
                                clientSocket.setKeepAlive(true);

                                printSocket("Established connection with ", clientSocket, "");
                                clients.add(clientSocket);

                                // start thread
                                new ClientThread(clientSocket).start();
                        }
                } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
                }
        }

        public static void printSocket(String header, Socket socket, String tail) {
                String socketInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                System.out.println(header + socketInfo + tail);
        }


        public class ClientThread extends Thread {

                private Socket clientSocket;
                private BufferedReader clientInput;
                private DataOutputStream clientOutput;

                public ClientThread(Socket clientSocket) {
                        this.clientSocket = clientSocket;

                        try {
                                clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                        } catch (IOException error) {
                                System.out.println("IOException: " + error.getMessage());
                                error.printStackTrace();
                        }
                }

                public void closeConnection() {
                        try {
                                clientSocket.close();
                                clientInput.close();
                                clientOutput.close();
                        } catch (IOException error) {
                                System.out.println("IOException: " + error.getMessage());
                                error.printStackTrace();
                        }
                }

                @Override
                public void run() {
                        String inputLine = "";  // from client 

                        try {
                                while ((inputLine = clientInput.readLine()) != null) {
                                        if (inputLine.startsWith("/quit")) {
                                                printSocket("Destroyed connection with ", clientSocket, "");
                                                break;
                                        }
                                        // TODO: more commands
                                }
                        } catch (SocketException error) {
                                System.out.println("SocketException: " + error);
                        } catch (SocketTimeoutException timeout) {
                                printSocket("", clientSocket, " Session's expired");
                                try {
                                        clientOutput.writeBytes("/expire");
                                } catch (IOException error) {
                                        System.out.println("IOException: " + error.getMessage());
                                        error.printStackTrace();
                                }
                        } catch (IOException error) {
                                System.out.println("IOException: " + error.getMessage());
                                error.printStackTrace();
                        }
                        

                        clients.remove(clientSocket);
                        closeConnection();
                }
        }
}
