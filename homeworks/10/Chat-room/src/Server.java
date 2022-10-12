import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connectionHandlers;
    private ServerSocket server;
    private boolean done;

    private ExecutorService pool;

    public Server(){
        connectionHandlers = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(1234);
            pool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connectionHandlers.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e){
            close();
        }

    }

    public void broadcastMessage(String message){
        for(ConnectionHandler conn: connectionHandlers){
            if (message!= null){
                if(conn.sender == true){
                    continue;
                }
                conn.sendMessage(message);
            }

        }
    }

    public void close(){
        done = true;
        if (!server.isClosed()){
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(ConnectionHandler ch: connectionHandlers){
            ch.close();
        }
    }


    class ConnectionHandler implements Runnable{

        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;
        private boolean sender;

        public ConnectionHandler(Socket client) {
            this.client = client;
            sender = false;
        }

        @Override
        public void run() {
            try{
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Welcome!\nYou can use the following commands\n" +
                        "\t /nick to change your username\n" +
                        "\t /msg to send a message to other users\n" +
                        "\t /quit to exit\n");
                nickname = "User";
                System.out.println("New user has connected!");
                broadcastMessage("New user joined the chat!");

                String message;

                while ((message = in.readLine()) != null){
                    if(message.startsWith("/nick")){
                        String[] newMessage = message.split(" ");
                            broadcastMessage(nickname + " changed his nickname to " + newMessage[1]);
                            nickname = newMessage[1];
                    }else if (message.startsWith("/quit")) {
                        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm");
                        String time = formatDate.format(new Date());
                        sender = true;
                        broadcastMessage("[" + time + "] " + nickname + " left the chat!");
                        sender = false;
                        System.out.println(nickname + " left the chat");
                        close();
                    }else if (message.startsWith("/msg")){
                        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm");
                        String time = formatDate.format(new Date());
                        String[] newMessage = message.split(" ", 2);
                        sender = true;
                        broadcastMessage("["+time+"] " + nickname + ": " + newMessage[1]);
                        sender = false;
                    }else{
                    out.println("Wrong command!\n You can use the following commands\n" +
                            "\t /nick to change your username\n" +
                            "\t /msg to send a message to other users\n" +
                            "\t /quit to exit\n");
                    }
                }
            } catch (IOException e){
                close();
            }
        }

        public void sendMessage(String message){
            out.println(message);
        }

        public void close(){
            try {
                in.close();
                out.close();
                if(!client.isClosed()){
                    client.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}
