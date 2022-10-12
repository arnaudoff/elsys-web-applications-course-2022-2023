import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread receiver;

    public Client(){
        clientSocket = new Socket();
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    private void sendMessage() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while(!clientSocket.isClosed()){
            String msg = scanner.nextLine();
            out.println(msg);
            if(msg.equals("/quit")){
                this.stopConnection();
                break;
            }
        }
    }

    private void receive() throws Exception {
        if (this.receiver != null){
            throw new Exception("There's already a receiver running.");
        }
        this.receiver = new Thread(() -> {
            while(!clientSocket.isClosed()) {
                String resp;
                try {
                    resp = in.readLine();
                    if(resp==null){
                        continue;
                    }
                } catch (IOException e) {
                    return;
                }
                System.out.println(resp);
            }
        });
        this.receiver.start();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);

        System.out.println("In order to connect to a server use: /connect <ip>:<port>");
        while(!client.clientSocket.isConnected()) {
            String msg = scanner.nextLine();

            if(!msg.startsWith("/connect ")){
                System.out.println("Please don't forget to add /connect to the start of your line");
                continue;
            }
            String[] command = msg.substring("/connect ".length()).split(":");
            if (command.length != 2){
                System.out.println("Please only use the following syntax: /connect <ip>:<port>");
            }
            String Ip = command[0];
            int Port;
            try{
                Port = Integer.parseInt(command[1]);
            }catch (NumberFormatException e){
                System.out.println("Port has to be a valid number!");
                continue;
            }

            client.startConnection(Ip, Port);
        }

        client.receive();
        client.sendMessage();
    }
}
