package Server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Thread {
    private Socket socket = new Socket();
    private Server server = new Server();
    private String nickname = null;
    public ClientHandler(Socket clientSocket, Server server) {
        this.socket = clientSocket;
        this.server = server;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Socket getSocket(){
        return socket;
    }

    public void run() {
        try {
            DataInputStream dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            while(true){
                String str = dataInputStream.readUTF();
                if(str.startsWith("/nick")){
                    str = str.substring("/nick ".length());
                    setNickname(str);
                }else if(str.startsWith("/msg")){
                    str = str.substring("/msg ".length());
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
                    for(var client : server.getClients()){
                        if(client == this){
                            continue;
                        }
                        DataOutputStream outputStream = new DataOutputStream(client.getSocket().getOutputStream());
                        String message = "[" + dateTime.format(formatter) + "]" + " " +  nickname + ":" + " " + str;
                        outputStream.writeUTF(message);
                    }
                }else if(str.startsWith("/quit")){
                    dataInputStream.close();
                    socket.close();
                    server.removeClient(this);
                    break;
                }
            }
        } catch (IOException exception) {
            System.out.println(exception);
            server.removeClient(this);
        }
    }
}