package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private final int port;
    private ArrayList<Socket> clients;
    private ServerSocket socket;

    Server(int port){
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public ArrayList<Socket> getClients() {
        return clients;
    }

    public void removeClient(Socket client){
        clients.remove(client);
    }
    public void start() {
        try {
            socket = new ServerSocket(port);
            while(true){
                Socket clientSocket = socket.accept();
                clients.add(clientSocket);
                ServerReceiver receiver = new ServerReceiver(clientSocket, this);
                receiver.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
