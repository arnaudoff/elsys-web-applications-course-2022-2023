import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.text.*;

public class Server implements Runnable{

    private ArrayList <ClientToServerConnection> connections; //list of connected clients
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a", Locale.ENGLISH);

    public Server(){
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run(){
        try {
           server = new ServerSocket(4444);
           pool = Executors.newCachedThreadPool();
            while (!done) {                                 //assure there is constant connection
                Socket client = server.accept();
                ClientToServerConnection handler = new ClientToServerConnection(client);
                connections.add(handler);
                pool.execute(handler);
            }
        }catch (Exception e){
            quit();
        }
    }

    //to broadcast message to everyone
    public void broadcast(String message){
        for (ClientToServerConnection ch : connections){
            if(ch!=null) {
                ch.sendMessage(message);
            }
        }
    }

    public void quit() {                                            //shutdown of server
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();
            }
            for (ClientToServerConnection ch : connections){
                ch.quit();
            }
        }catch (IOException e){
            //
        }
    }

    class ClientToServerConnection implements Runnable{             //handles individual connections

        private Socket client;
        private BufferedReader in;                                  //get stream from the socket
        private PrintWriter out;                                    // write to client
        private String nickname;

        public ClientToServerConnection(Socket client) {
            this.client = client;
        }

        @Override
        public void run(){
            try {
                    out = new PrintWriter(client.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out.println("Enter nickname:");                 //message to the client
                    nickname = in.readLine();                      
                    System.out.println(nickname + " connected");
                    broadcast (nickname + " joined the chat!");      // to everyone

                    String message;
                    while ((message = in.readLine()) !=null){

                        if (message.startsWith("/nick ")){          //to sign with nickname
                            String[] messageSplit = message.split(" ", 2);
                            if(messageSplit.length == 2){
                                broadcast(nickname + "has new name" + messageSplit[1]);
                                System.out.println(nickname + "has new name" + messageSplit[1]);
                                nickname = messageSplit[1];
                                out.println("Successful nickname update: " + nickname);
                            }

                        }else if(message.startsWith("/quit")) {     // to quit communicaton
                            broadcast(nickname + "is out of the chat");
                            quit();

                        }else if (message.startsWith("/msg ")) {
                            String[] messageSplit = message.split(" ", 2);
                            broadcast(nickname + "-> " + messageSplit[1]);
                        }
            }

        }catch (IOException e){
                //
            }

        }
        public void sendMessage (String message){
            out.println(message);
                out.println("[" + LocalDateTime.now().format(formatter) + "] " + message);
            }


        public void quit(){
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            }catch (IOException e){

            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
