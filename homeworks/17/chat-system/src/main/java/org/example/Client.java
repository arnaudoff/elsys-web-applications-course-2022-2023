package org.example;

import java.io.*;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username = "Anonimen";


    public Client(Socket socket) {
        setSocket(socket);
        //setUsername(username);
        try {
            setBufferedReader(socket.getInputStream());
            setBufferedWriter(socket.getOutputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String host = "";
        int port = 0;
        boolean flag = true;
        do {
            System.out.println("Connect to chatroom using: /connect <ip of chat server>:<port>");
            String message = scanner.nextLine();
            try{
                if (Objects.nonNull(message) && message.startsWith("/connect ")) {
                    message = message.substring("/connect ".length());
                    String[] input = message.split(":");
                    if (input.length == 2) {
                        host = input[0];
                        port = Integer.parseInt(input[1]);

                        flag = false;
                    }
                }
            }catch (Exception exception) {
                System.out.println("Invalid Input");
            }
        }while(flag);

        Socket socket = new Socket(host, port);
        Client client = new Client(socket);
        client.listenForMessage();
        client.sendMessage();
    }

    private boolean isCommandCorrect(String messageToSend) {
        return Objects.nonNull(messageToSend);
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                if (isCommandCorrect(messageToSend)) {
                    bufferedWriter.write(messageToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException exception) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeReader(BufferedReader bufferedReader) throws IOException {
        if (Objects.nonNull(bufferedReader)) {
            bufferedReader.close();
        }
    }

    private void closeWriter(BufferedWriter bufferedWriter) throws IOException {
        if (Objects.nonNull(bufferedWriter)) {
            bufferedWriter.close();
        }
    }

    private void closeSocket(Socket socket) throws IOException {
        if (Objects.nonNull(socket)) {
            socket.close();
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            closeWriter(bufferedWriter);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            closeReader(bufferedReader);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            closeSocket(socket);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromServer;
                while (socket.isConnected()) {
                    try {
                        messageFromServer = bufferedReader.readLine();
                        if(Objects.isNull(messageFromServer)) {
                            closeEverything(socket, bufferedReader, bufferedWriter);
                        }else {
                            System.out.println(messageFromServer);
                        }
                    } catch (IOException exception) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    private void setSocket(Socket socket) {
        if (Objects.isNull(socket)) {
            throw new InvalidParameterException();
        }
        this.socket = socket;
    }

    private void setBufferedReader(InputStream inputStream) {
        if (Objects.isNull(inputStream)) {
            throw new InvalidParameterException();
        }
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    }

    private void setBufferedWriter(OutputStream outputStream) {
        if (Objects.isNull(outputStream)) {
            throw new InvalidParameterException();
        }
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void setUsername(String username) {
        if (Objects.isNull(username) || username.isBlank() || username.length() > 20) {
            System.out.println("Wrong name");
        } else {
            this.username = username;
        }
    }
}
