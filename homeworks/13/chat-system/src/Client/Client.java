package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {

        Socket serverSocket; 
        private String username = "";

        public static void main(String[] args) throws Exception {

                Client client = new Client();
                getAndParseInput(client);

        }

        private void connect(String hostname, short port) {
               try {
                        serverSocket = new Socket(hostname, port);
                        System.out.println("Established connection with " +
                                serverSocket.getInetAddress().getHostAddress() +
                                ":" + serverSocket.getPort());

               } catch (IOException error) {
                        System.out.println("IOException: " + error.getMessage());
               }
        }

        private static void getAndParseInput(Client client) {
                Scanner scanner = new Scanner(System.in);
                String command = "";

                while(true) {
                        command = scanner.nextLine();

                        switch(command) {
                        case "/connect":
                                client.connect("localhost", (short)1210);
                                break;
                        case "/quit":
                                scanner.close();
                                return;
                        default:
                                System.out.println("Error, not a command");

                        }

                }
        }
}
