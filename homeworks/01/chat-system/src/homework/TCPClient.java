package homework;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPClient {
    public static void main(String argv[]) throws Exception {
        String command;
        String nickname = "Guest";

        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        command = scanner.nextLine();

        if (command.contains("/connect ")) {
            String connection = command.replace("/connect ", "");
            String[] keyValuePair = connection.split(":", 2);
            Socket clientSocket = new Socket(keyValuePair[0], Integer.parseInt(keyValuePair[1]));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            command = scanner.nextLine();

            if (command.contains("/nick ")) {
                nickname = command.replaceAll("/nick ", "");
            }

            while (!command.equals("/quit")) {
                while (bufferedReader.ready()) {
                    String msgFromGroupChat = bufferedReader.readLine();
                    System.out.println(msgFromGroupChat);
                }
                command = scanner.nextLine();

                if (command.contains("/msg ")) {
                    String inFromUser = command.replaceAll("/msg ", "");
                    outToServer.writeBytes(nickname + ": " + inFromUser + "\n");
                }
            }
            clientSocket.close();
        }
    }
}
