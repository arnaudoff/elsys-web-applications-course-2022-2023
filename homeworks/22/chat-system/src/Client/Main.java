package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket();
        s.setKeepAlive(true);
        ReceiverThread thread;
        DataOutputStream dout = null;
        boolean connected = false;
        while(true){
            String command = sc.nextLine();
            if(command.startsWith("/")){
                ArrayList<String> commandArgs = new ArrayList<>(List.of(command.split(" ")));
                commandArgs.set(0, commandArgs.get(0).toLowerCase());
                if(commandArgs.get(0).equals("/connect")){
                    if(commandArgs.size() != 3){
                        System.err.println("Incorrect amount of parameters");
                        continue;
                    }
                    String ip = commandArgs.get(1);
                    String port = commandArgs.get(2);
                    try{
                        s = new Socket(ip, Integer.parseInt(port));
                        thread = new ReceiverThread(s);
                        thread.start();
                        dout = new DataOutputStream(s.getOutputStream());
                        connected = true;
                    } catch(IOException e){
                        System.err.printf("An error has occurred: %s%n", e.getMessage());
                    }
                }
                else if(
                    (commandArgs.get(0).equals("/nick") ||
                    commandArgs.get(0).equals("/msg")) &&
                    connected)
                {
                    if(commandArgs.size() < 2){
                        System.err.println("Incorrect amount of parameters");
                        continue;
                    }
                    dout.writeUTF(String.join(" ", commandArgs));
                    dout.flush();
                }
                else if (commandArgs.get(0).equals("/quit") && connected) {
                    dout.writeUTF(commandArgs.get(0));
                    dout.flush();
                    dout.close();
                    s.close();
                    connected = false;
                }
                else{
                    System.out.println("/connect [ip] [port] - used to connect to a chat server");
                    System.out.println("/nick [nickname] - used to set nickname");
                    System.out.println("/msg [message] - used to send messages to all other users in a chat server");
                    System.out.println("/quit - used to disconnect form a chat server");
                }
            }
            else{
                System.err.println("Commands must start with /. Type /help for a list of commands");
            }
        }
    }
}
