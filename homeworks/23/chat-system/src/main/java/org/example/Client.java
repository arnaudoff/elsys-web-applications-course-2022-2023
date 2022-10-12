package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        String command = "";

        while (!command.equals("/quit")) {
            System.out.print("\033[35;3mEnter command\033[0m: ");
            // System.out.print("Enter command: ");  // Commented lines do not apply color to the printed text (some terminals do not support coloring)
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
            System.out.println("Connected to \033[34;3m" + socket.getInetAddress().getHostAddress() + "\033[0;3m:\033[33;3m" + socket.getPort() + "\033[0m");
            // System.out.println("Connected to " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

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

                System.out.print("\033[32m==>\033[0m ");
                // System.out.print("==> ");
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
