package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        try (var serverSocket = new ServerSocket(port)){
            System.out.println("Server is listening on port " + port);
            System.out.println(serverSocket.getLocalSocketAddress());

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + " connected");

                new ServerThread(socket).start();
            }
        } catch (IOException err) {
            System.out.println("Server exception: " + err.getMessage());
            err.printStackTrace();
        }
    }
}