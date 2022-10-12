package org.example;

import java.io.*;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private String username;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public ClientHandler(Socket socket) {
        setSocket(socket);

        try {
            setBufferedWriter(socket.getOutputStream());
            setBufferedReader(socket.getInputStream());
            setUserName();
            clientHandlers.add(this);
            broadcastAnnouncement(username + " has entered with glavata through vratata!");
        } catch (IOException exception) {
            exception.printStackTrace();
            closeEverything(this.socket, bufferedReader, bufferedWriter);
        }
    }

    private void setUserName() throws IOException {
        this.username = bufferedReader.readLine();
    }

    private void setBufferedReader(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    private void setBufferedWriter(OutputStream outputStream) {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    private void setSocket(Socket socket) {
        if (Objects.isNull(socket)) {
            throw new InvalidParameterException("Client socket cant be null");
        }
        this.socket = socket;
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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
        removeHandler();
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

    public String getUsername() {
        return username;
    }

    public void broadcastMessage(String messageToSend) {
        if (Objects.isNull(messageToSend) || messageToSend.startsWith("/quit")) {
            closeEverything(this.socket, bufferedReader, bufferedWriter);
            broadcastAnnouncement("SERVER: " + username + " has ritna kambanata, rip bratan o7!");
        } else if (messageToSend.startsWith("/rename ")) {
            broadcastAnnouncement("SERVER: " + username + " has changed their name to " + messageToSend.substring("/rename ".length()));
            username = messageToSend.substring("/rename ".length());
        } else if (messageToSend.startsWith("/message ")) {

            for (ClientHandler clientHandler : clientHandlers) {
                try {
                    if (!clientHandler.username.equals(username)) {
                        clientHandler.bufferedWriter.write("[" + DateTimeFormatter.ofPattern("HH:mm a").format(LocalTime.now()) + "] ");
                        clientHandler.bufferedWriter.write(username + ": " + messageToSend.substring("/message ".length()));
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                } catch (IOException exception) {
                    closeEverything(this.socket, bufferedReader, bufferedWriter);
                }
            }
        }
    }

    public void broadcastAnnouncement(String announcementToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.username.equals(username) && Objects.nonNull(announcementToSend)) {
                    clientHandler.bufferedWriter.write("[" + DateTimeFormatter.ofPattern("HH:mm a").format(LocalTime.now()) + "] ");
                    clientHandler.bufferedWriter.write(announcementToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException exception) {
                closeEverything(this.socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeHandler() {
        clientHandlers.remove(this);
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException exception) {
                closeEverything(this.socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

}