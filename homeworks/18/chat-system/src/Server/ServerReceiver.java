package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServerReceiver extends Thread {

    private Socket socket;
    private Server server;
    private String nick;
    public ServerReceiver(Socket clientSocket, Server server) {
        this.socket = clientSocket;
        this.server = server;
        this.nick = null;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            while(true){
                String line = input.readUTF();
                ArrayList<String> args = new ArrayList<>(List.of(line.split(" ")));
                switch(args.get(0)){
                    case "/nick":
                        try{
                            args.remove(0);
                            this.nick = String.join(" ", args);
                        }catch (Exception e){
                            System.err.println(e.getMessage());
                        }
                        break;
                    case "/msg":
                        if(this.nick == null){
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                            output.writeUTF("Cannot send messages without nickname");
                            break;
                        }
                        try{
                            args.remove(0);
                            for(Socket client : server.getClients()){
                                if(client == this.socket){
                                    continue;
                                }
                                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                                LocalDateTime now = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
                                output.writeUTF(String.format("[%s] %s: %s",
                                        now.format(formatter),
                                        this.nick,
                                        String.join(" ", args)
                                ));
                            }
                        }catch (Exception e){
                            System.err.println(e.getMessage());
                        }
                        break;
                    case "/quit":
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        output.writeUTF("Disconnected");
                        this.server.removeClient(this.socket);
                        input.close();
                        this.socket.close();
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
