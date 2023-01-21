package Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client extends Thread {
    private Socket socket = new Socket();
    public Client(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        while (true) {
            DataInputStream in = null;
            try {
                in = new DataInputStream(socket.getInputStream());
                String fromServer = in.readUTF();
                System.out.println(fromServer);
            } catch (IOException e) {
                try {
                    if (in != null) {
                        in.close();
                    }
                    socket.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket();
        socket.setKeepAlive(true);
        Client thread;
        DataOutputStream dout = null;

        while (true) {
            String command = scanner.nextLine();
            ArrayList<String> commandList = new ArrayList<>(List.of(command.split(" ")));
            switch (commandList.get(0)) {
                case "/connect":
                    String ip = commandList.get(1);
                    int port = Integer.parseInt(commandList.get(2));
                    try {
                        socket = new Socket(ip, port);
                        thread = new Client(socket);
                        thread.start();
                        dout = new DataOutputStream(socket.getOutputStream());
                    } catch (IOException exception) {
                        System.out.println(exception);
                    }
                    break;
                case "/nick":
                case "/msg":
                    if (dout != null) {
                        dout.writeUTF(String.join(" ", commandList));
                    }
                    break;
                case "/quit":
                    dout.close();
                    socket.close();
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }
}