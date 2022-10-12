package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {

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
                getAndParseInput(client);
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
                        printSocket("Established connection with", serverSocket, "");

                        serverInput = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                        serverOutput = new DataOutputStream(serverSocket.getOutputStream());

               } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
               }
        }

        private static void getAndParseInput(Client client) {
                String command = "";

                try {
                        Scanner scanner = new Scanner(System.in);

                        while(true) {
                                command = scanner.nextLine();

                                switch(command) {
                                case "/connect":
                                        // TODO: parse and pass parameters
                                        client.connect("localhost", (short)1210);
                                        break;
                                case "/quit":
                                        serverOutput.writeBytes("/quit\n");
                                        scanner.close();
                                        return;
                                default:
                                        System.out.println("Error, not a command");

                                }

                        }
                } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
                        error.printStackTrace();

                }
        }

        public static void closeConnection() {
                try {
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
