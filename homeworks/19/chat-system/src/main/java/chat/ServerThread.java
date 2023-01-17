package chat;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class ServerThread extends Thread {
    private Socket socket;
    private String clientNickname = "Guest " + currentThread().getId();
    private static final HashSet<Socket> sockets = new HashSet<>();

    public ServerThread(Socket socket) {
        this.socket = socket;
        synchronized (sockets) {
            sockets.add(socket);
        }
    }

    public void run() {
        try {
            InputStream in = socket.getInputStream();
            var input = new BufferedReader(new InputStreamReader(in));

            String command;
            SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm aa");

            while (true) {
                command = input.readLine();
                if (command == null) {
                    break;
                }
                if (command.startsWith("/nick ")) {
                    clientNickname = command.substring(6);
                }
                else if (command.startsWith("/msg ")) {
                    String msg = "[" + dateFormatter.format(new Date()) + "] " +
                            clientNickname + ": " + command.substring(5);
                    synchronized (sockets) {
                        for (var socket : sockets) {
                            if (socket.equals(this.socket)) {
                                continue;
                            }
                            OutputStream out = socket.getOutputStream();
                            var output = new DataOutputStream(out);
                            output.writeBytes(msg + "\n");
                        }
                    }
                }
            }

            synchronized (sockets) {
                sockets.remove(socket);
            }
            socket.close();
            System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " disconnected");
        } catch (IOException err) {
            System.out.println("Server exception: " + err.getMessage());
            err.printStackTrace();
        }
    }
}
