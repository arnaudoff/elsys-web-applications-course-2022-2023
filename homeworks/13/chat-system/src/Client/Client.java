package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {

        private static Thread receiverThread;
        private static Socket serverSocket; 
        private static BufferedReader serverInput;  // input from server
        private static DataOutputStream serverOutput;  // output to server
        private String username; 

        public Client() {
                serverSocket = null;
                username = "";
        }

        public static void main(String[] args) throws Exception {

                Client client = new Client();
                sendToServer(client);
                closeConnection();
        }

        public static void printSocket(String header, Socket socket, String tail) {
                String socketInfo = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
                System.out.println(header + socketInfo + tail);
        }

        private void connect(String hostname, short port) {
               try {
                        if (serverSocket != null) {
                                System.out.println("Error, connection already exists");
                                return;
                        }

                        serverSocket = new Socket(hostname, port);
                        printSocket("Established connection with ", serverSocket, "");

                        serverInput = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                        serverOutput = new DataOutputStream(serverSocket.getOutputStream());

                        receiveFromServer();  // start a thread that reads from server

               } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
               }
        }

        private static void sendToServer(Client client) {
                String command = "";

                try {
                        Scanner scanner = new Scanner(System.in);

                        while(true) {
                                command = scanner.nextLine();
                                String[] args = null;

                                if (command.startsWith("/connect")) {
                                        args = command.trim().split(":| ", 0);
                                        if (args.length != 3) {
                                                System.out.println("Invalid amount of arguments");
                                        }
                                        // TODO: parse and pass parameters
                                        client.connect(args[1], Short.parseShort(args[2]));
                                } else if (command.startsWith("/quit")) {
                                        serverOutput.writeBytes("/quit");
                                        scanner.close();
                                        return;
                                } else {
                                        System.out.println("Error, not a command");
                                }

                        }
                } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
                        error.printStackTrace();

                }
        }

        private static void receiveFromServer() {
                receiverThread = new Thread(() -> {
                        while (serverSocket.isClosed() == false) {
                                String inputLine = "";
                                try {
                                        if ((inputLine = serverInput.readLine()) == null) {
                                                continue;
                                        }
                                } catch (IOException error) {
                                        if (serverSocket.isClosed() == true) {
                                                return;
                                        }

                                        System.out.println("IOException: " + error.getMessage());
                                        error.printStackTrace();
                                }


                                if (inputLine.equals("") == false && inputLine.charAt(0) != '/' ) {
                                        System.out.println(inputLine);
                                } else {
                                        if (inputLine.equals("/expire")) {
                                                System.out.println("Session expired");
                                                closeConnection();
                                        }
                                }
                        }
                });

                receiverThread.start();
        }

        public static void closeConnection() {
                try {
                        receiverThread.interrupt();
                        serverSocket.close();
                        serverInput.close();
                        serverOutput.close();

                        printSocket("Closed connection with ", serverSocket, "");
                        serverSocket = null;
                } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
                        error.printStackTrace();

                }
        }
}
