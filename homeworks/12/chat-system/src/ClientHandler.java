import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientNickname;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientNickname = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientNickname + " has entered the chat!");
        } catch (Exception e) {
            try {
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            }catch (Exception exception1) {
                System.out.println(exception1);
            }
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (Exception exception) {
                try {
                    bufferedReader.close();
                    bufferedWriter.close();
                    socket.close();
                }catch (Exception exception1) {
                    System.out.println(exception1);
                }
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) throws Exception {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientNickname.equals(clientNickname)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException exception){
                try {
                    bufferedReader.close();
                    bufferedWriter.close();
                    socket.close();
                }catch (Exception exception1) {
                    System.out.println(exception1);
                }
            }
        }
    }
}
