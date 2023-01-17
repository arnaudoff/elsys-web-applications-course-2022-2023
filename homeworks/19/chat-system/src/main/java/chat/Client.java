package chat;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("/quit")) {
            System.out.print("Enter command: ");
            command = scanner.nextLine();
            var input = command.split(" +");
            if (input.length != 2) {
                continue;
            }
            if (input[0].equals("/connect")) {
                var ipAndPort = input[1].split(":");
                if (ipAndPort.length != 2) {
                    continue;
                }
                connectToServer(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
            }
        }
    }

    private static void connectToServer(String hostname, int port) {
        try (var socket = new Socket(hostname, port)) {
            System.out.println("Connected to " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

            OutputStream out = socket.getOutputStream();
            var output = new DataOutputStream(out);
            InputStream in = socket.getInputStream();
            var input = new BufferedReader(new InputStreamReader(in));

            var userInput = new Scanner(System.in);
            String command = "";

            while (!command.equals("/quit")) {
                String msg;

                while (input.ready()) {
                    msg = input.readLine();
                    System.out.println(msg);
                }

                command = userInput.nextLine();
                if (command.startsWith("/nick ") || command.startsWith("/msg ")) {
                    output.writeBytes(command + "\n");
                }
            }
        } catch (UnknownHostException err) {
            System.out.println("Server not found: " + err.getMessage());
        } catch (IOException err) {
            System.out.println("I/O error: " + err.getMessage());
        }
    }
}
