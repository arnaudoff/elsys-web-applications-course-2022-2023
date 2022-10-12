package homework;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientHandler implements Runnable{

    public static List<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private DataOutputStream outToGroup;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.outToGroup = new DataOutputStream(socket.getOutputStream());
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clientHandlers.add(this);
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()){
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(getTime() + messageFromClient + "\n");
            } catch (IOException e) {
                System.out.println("IOException");
                break;
            }
        }
    }

    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        String dateString = dateFormat.format(new Date()).toString();
        return ("[" + dateString + "] ");
    }

    public void broadcastMessage(String messageToSend) throws IOException {
        for (ClientHandler clientHandler : clientHandlers){
            if (!clientHandler.equals(this)){
                DataOutputStream out = clientHandler.outToGroup;
                out.writeBytes(messageToSend);
            }
        }
    }
}

