import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> waitingClients = new ArrayList<>();
    private HashMap<String, ClientHandler> roomClients = new HashMap<>();
    ExecutorService clientExecutor = Executors.newFixedThreadPool(200);

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (!serverSocket.isClosed()) {
            Socket client = serverSocket.accept();
            if (client.isConnected()) {
                client.setSoTimeout(1000 * 120);
                client.setKeepAlive(true);
                ClientHandler clh = new ClientHandler(client);
                waitingClients.add(clh);
                clientExecutor.execute(new Thread(clh));
            }
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private void sendMsgToEveryone(String senderNick, String msg){
        for(ClientHandler ch: roomClients.values()){
            if(!ch.getNickname().equals(senderNick)){
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
                String formattedMessage = String.format("[%s] %s: %s", now.format(formatter), senderNick, msg);
                ch.sendMessage(formattedMessage);
            }
        }
    }

    private void removeClientHandler(ClientHandler ch){
        if(waitingClients.contains(ch)){
            waitingClients.remove(ch);
        }else{
            this.roomClients.remove(ch.nickname);
        }
    }

    private void dropFromWaiting(ClientHandler ch){
        this.waitingClients.remove(ch);
    }

    private class ClientHandler implements Runnable {
        private String nickname;
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClientHandler that = (ClientHandler) o;
            return Objects.equals(nickname, that.nickname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nickname);
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        public void sendMessage(String message){
            if(out!=null){
                out.println(message);
            }
        }

        public void closeClientHandler() {
            Server.this.removeClientHandler(this);
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            this.sendMessage("You have successfully connected!");
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if(this.nickname==null && !inputLine.startsWith("/nick ")){
                        this.sendMessage("In order to chat and see what everyone chats set your nickname first with: /nick <nickname>");
                        continue;
                    }

                    if(inputLine.startsWith("/nick ")){
                        if(this.nickname != null){
                            this.sendMessage("You have already set your nickname to " + this.nickname);
                            continue;
                        }
                        String wantedNickname = inputLine.substring("/nick ".length());
                        if(Server.this.roomClients.containsKey(wantedNickname)){
                            String msg = wantedNickname.concat(" nickname is already in use. Please choose another nickname!");
                            this.sendMessage(msg);
                            continue;
                        }

                        this.nickname = wantedNickname;
                        Server.this.dropFromWaiting(this);
                        Server.this.roomClients.put(this.nickname, this);
                        String msg = "You have successfully set your nickname to ".concat(this.nickname);
                        this.sendMessage(msg);
                        continue;
                    }

                    if (inputLine.startsWith("/msg ")) {
                        Server.this.sendMsgToEveryone(this.nickname, inputLine.substring("/msg ".length()));
                        continue;
                    }

                    if (inputLine.startsWith("/quit")){
                        this.sendMessage("Quiting...");
                        this.closeClientHandler();
                        break;
                    }

                    this.sendMessage("Please use /msg <message> to send a message or /quit to quit the chat!");
                }

            }catch (SocketException e){
                this.closeClientHandler();
            }
            catch (SocketTimeoutException e){
                this.sendMessage("You just got server timed out!");
                this.closeClientHandler();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(1234);
    }
}
