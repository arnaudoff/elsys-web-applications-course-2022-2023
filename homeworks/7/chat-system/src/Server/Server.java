package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public ArrayList<ClientHandler> getClients(){
        return clients;
    }
    public void removeClient(ClientHandler client){
        clients.remove(client);
    }

    public Server(){}

    public void start() throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        try {
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                if (connectionSocket.isConnected()) {
                    ClientHandler thread = new ClientHandler(connectionSocket, this);
                    clients.add(thread);
                    thread.start();
                }
            }
        } catch (Exception exception){
            welcomeSocket.close();
            System.out.println(exception);
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.start();
    }
}