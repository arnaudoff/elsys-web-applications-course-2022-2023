
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Surver implements Runnable {
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    public Surver(){
        connections = new ArrayList<>();
        done = false;
    }
    @Override
    public void run(){
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while (!done) {

                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            shutDown();
        }
    }
    public  void Broadcast(String massage){
        for (ConnectionHandler ch : connections){
            if(ch != null){
                ch.SendMassage(massage);
            }
        }
    }
    public  void shutDown(){
        try {
            done = true;
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections){
                ch.shutdown();
            }
        }catch (IOException e){
            //ignor
        }

    }
    class ConnectionHandler implements Runnable{
        private  Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        private ConnectionHandler(Socket client){
            this.client=client;
        }
        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Enter  nickname");
                nickname = in.readLine();
                System.out.println(nickname + "is connected!" );
                Broadcast(nickname + "joined the chat");
                String message;
                while ((message = in.readLine()) != null){
                    if(message.startsWith("/nick") ){
                        String[] messageSplit = message.split(" ",2);
                        if(messageSplit.length == 2){
                            Broadcast(nickname + " renamed themselves to"+ messageSplit[1]);
                            System.out.println(nickname + " renamed themselves to"+ messageSplit);
                            nickname = messageSplit[1];
                            System.out.println("Successfully changed nickname to "+ nickname);
                        }
                        else {
                            out.println("no nickname was provided");
                        }
                    }else if (message.startsWith("/quit")){
                        Broadcast(nickname+"left the server");
                        shutdown();
                    }else if (message.startsWith("/msg")) {
                        message = message.substring("/msg".length());
                        Broadcast(nickname + ":" + message);
                    }
                }
            }catch (Exception e ){
                shutdown();
            }
        }
        public  void SendMassage(String massage){
            out.println(massage);
        }
        public  void shutdown(){
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            }catch (IOException e ){

            }
        }
    }

    public static void main(String[] args) {
        Surver server = new Surver();
        server.run();
    }
}
