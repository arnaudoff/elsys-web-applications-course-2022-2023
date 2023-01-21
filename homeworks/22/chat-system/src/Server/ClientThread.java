package Server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientThread implements Runnable {
    private Socket s;
    private Server server;
    private String nickname = null;
    public ClientThread(Socket clientSocket, Server server) {
        this.s = clientSocket;
        this.server = server;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Socket getSocket(){
        return this.s;
    }

    public void run() {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            while(true){
                String str = dis.readUTF();

                if(str.startsWith("/nick")){
                    str = str.substring("/nick ".length());
                    if(!server.getUsernames().contains(str)){
                        setNickname(str);
                    }
                    else {
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("Nickname already used!");
                    }
                }
                else if(str.startsWith("/msg")){
                    if(nickname == null){
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("Set nickname by typing '/nick [nickname]' in order to send messages!");
                        continue;
                    }
                    if(!server.getClients().isEmpty()){
                        str = str.substring("/msg ".length());
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
                        for(ClientThread t : server.getClients()){
                            if(t != this){
                                DataOutputStream dout = new DataOutputStream(t.getSocket().getOutputStream());
                                dout.writeUTF(String.format("[%s] %s: %s",now.format(formatter), this.nickname, str));
                            }
                        }
                    }
                }
                else if(str.startsWith("/quit")){

                    dis.close();
                    this.s.close();
                    this.server.removeClient(this);
                    this.server.removeUsername(nickname);
                    break;
                }

            }
        } catch (IOException e) {
            System.err.printf("An error has occurred: %s%n", e.getMessage());
            this.server.removeClient(this);
            this.server.removeUsername(nickname);
        }

    }
}
