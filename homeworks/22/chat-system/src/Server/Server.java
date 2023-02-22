package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static final int PORT = 1914;
    private ArrayList<ClientThread> clients;
    private ArrayList<String> usernames;
    public Server(){
        this.clients = new ArrayList<>();
        this.usernames = new ArrayList<>();
    }
    public ArrayList<String> getUsernames(){
        return this.usernames;
    }
    public ArrayList<ClientThread> getClients(){
        return this.clients;
    }
    public void removeClient(ClientThread client){
        this.clients.remove(client);
    }


    public void removeUsername(String nickname) {
        this.usernames.remove(nickname);
    }

    public void start() throws IOException {
        ServerSocket serverSocket;
        Socket socket;
        ExecutorService executor = Executors.newFixedThreadPool(100);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {

            while (true) {
                try {
                    socket = serverSocket.accept();
                    if (socket.isConnected()) {
                        socket.setSoTimeout(60 * 1000);
                        socket.setKeepAlive(true);
                        ClientThread thread = new ClientThread(socket, this);
                        clients.add(thread);
                        executor.execute(thread);
                    }
                } catch (IOException e) {
                    System.err.println("I/O error: " + e);
                }
            }
        }
        catch (Exception e){
            serverSocket.close();
            executor.shutdown();
            while (!executor.isTerminated()) {
            }
            System.out.println("Finished all threads");
        }
    }

}
