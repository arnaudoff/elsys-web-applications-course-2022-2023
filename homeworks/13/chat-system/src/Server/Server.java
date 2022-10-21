package Server;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class Server {

        private ServerSocket serverSocket;
        private short port;
        private static List<ClientThread> clients;
        private static Map<String, String> clientMap;
        private static DateTimeFormatter dtf;
        private static LocalDateTime now;

        public Server(short port) {
                this.port = port;
                clients = new ArrayList<ClientThread>();
                clientMap = new HashMap<String, String>();
                dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
                now = LocalDateTime.now();
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
                                clientSocket.setSoTimeout(60 * 1000);
                                clientSocket.setKeepAlive(true);

                                printSocket("Established connection with ", clientSocket, "");

                                // start thread
                                ClientThread clientThread = new ClientThread(clientSocket);
                                clients.add(clientThread);
                                clientThread.start();
                        }
                } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
                }
        }

        public static void printSocket(String header, Socket socket, String tail) {
                String socketInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                System.out.println(header + socketInfo + tail);
        }

        private void broadcastMsg(String msg) {
                System.out.println(clients.size());
                for (ClientThread clientThread : clients) {
                        // TODO: also send to sender with the added time stamp
                        try {
                                clientThread.clientOutput.writeBytes(msg + "\n");
                        } catch (IOException error) {
                                System.out.println("IOException: " + error.getMessage());
                                error.printStackTrace();
                        }
                }
        }

        private String getClientMapKey(Socket clientSocket) {
                return clientSocket.getInetAddress().getHostAddress() + Integer.toString(clientSocket.getPort());
        }

        public class ClientThread extends Thread {

                private Socket clientSocket;
                private BufferedReader clientInput;
                private DataOutputStream clientOutput;

                public ClientThread(Socket clientSocket) {
                        this.clientSocket = clientSocket;
                        clientMap.put(getClientMapKey(clientSocket), clientSocket.getInetAddress().getHostAddress() + ":" + Integer.toString(clientSocket.getPort()));

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
                                        } else if (inputLine.startsWith("/msg")) {
                                                inputLine = inputLine.replaceFirst("/msg", "/msg[" + clientMap.get(getClientMapKey(clientSocket)) + "] ");
                                                System.out.println("[RECEIVED]" + inputLine);
                                                broadcastMsg(inputLine + " (" + dtf.format(now) + ")");
                                        } else if (inputLine.startsWith("/nick")) {
                                                inputLine = inputLine.replaceFirst("/nick", "").trim();
                                                clientMap.put(getClientMapKey(clientSocket), inputLine);
                                        }
                                }
                        } catch (SocketException error) {
                                System.out.println("SocketException: " + error);
                        } catch (SocketTimeoutException timeout) {
                                printSocket("", clientSocket, " Session's expired");
                                try {
                                        clientOutput.writeBytes("/expire\n");
                                } catch (IOException error) {
                                        System.out.println("IOException: " + error.getMessage());
                                        error.printStackTrace();
                                }
                        } catch (IOException error) {
                                System.out.println("IOException: " + error.getMessage());
                                error.printStackTrace();
                        }
                        
                        clients.remove(this);
                        clientMap.remove(getClientMapKey(clientSocket));
                        closeConnection();
                }
        }
}
