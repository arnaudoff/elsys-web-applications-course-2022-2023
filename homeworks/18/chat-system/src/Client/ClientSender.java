package Client;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientSender {
    public void start(){
        Scanner sc = new Scanner(System.in);
        Socket socket = null;
        while(true){
            String line = sc.nextLine();
            ArrayList<String> args = new ArrayList<>(List.of(line.split(" ")));
            switch (args.get(0)){
                case "/connect" :
                    try{
                        try{
                            if(socket.isConnected()){
                                System.out.println("Already connected");
                            }
                        }catch(Exception ex){}
                        socket = new Socket(args.get(1), Integer.parseInt(args.get(2)));
                        ClientReceiver receiver = new ClientReceiver(socket);
                        receiver.start();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                    break;
                case "/nick" : case "/msg" : case "/quit":
                    try{
                        if(socket.isConnected()) {
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                            output.writeUTF(line);
                        }
                        else{
                            System.err.println("Not connected");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.err.println("Not a command");
            }
        }
    }
}
