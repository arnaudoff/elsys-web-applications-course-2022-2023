
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService threadPool;

    public Server() {
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(2004);
            threadPool = Executors.newCachedThreadPool();
            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                threadPool.execute(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
            shutdown();
        }

    }

    public void broadcast(String message) {
        for (ConnectionHandler ch : connections) {
            if (ch != null) {
                if (ch.threadSender == false) {
                    ch.sendMessage(message);
                }
            }
        }
    }

    public void shutdown() {
        try {
            done = true;
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader input;
        private PrintWriter output;
        private String nickname;
        private boolean threadSender;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                output = new PrintWriter(client.getOutputStream(), true);
                input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                nickname = "User";
                System.out.println("New " + nickname + " connected!");
                broadcast("New " + nickname + " joined the chat!");
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.startsWith("/nick")) {
                        String[] splittedMessage = message.split(" ", 2);
                        if (splittedMessage.length == 2) {
                            threadSender = true;
                            broadcast(nickname + " renamed themselves to " + splittedMessage[1]);
                            threadSender = false;
                            System.out.println(nickname + " renamed themselves to " + splittedMessage[1]);
                            nickname = splittedMessage[1];
                            output.println("Successfully changed nickname to " + nickname);
                        } else {
                            output.println("No nickname provided!");
                        }
                    } else if (message.startsWith("/msg")){
                        String[] splittedMessage = message.split(" ", 2);
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
                        threadSender = true;
                        broadcast("[" + now.format(formatter) + "] " + nickname + ": " + splittedMessage[1]);
                        threadSender = false;
                    } else if (message.startsWith("/quit")) {
                        threadSender = true;
                        System.out.println(nickname + " left the chat!");
                        broadcast(nickname + " left the chat!");
                        threadSender = false;
                        shutdown();
                    } else {
                        output.println("Invalid command! Use one of this commands:\n" +
                                "\t '/nick' - set or change nickname\n" +
                                "\t '/msg' - send message to other users\n" +
                                "\t '/quit' - leave the chat");
                    }
                }
            } catch (IOException exception) {
                shutdown();
            }
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        public void shutdown() {
            try {
                input.close();
                output.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}



