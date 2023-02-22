package Client;

import java.io.*;
import java.net.Socket;

public class ReceiverThread extends Thread {
    private Socket s;
    public ReceiverThread(Socket socket){
        this.s = socket;
    }


    public void run(){
        while (true) {
            DataInputStream in = null;
            try {
                in = new DataInputStream(s.getInputStream());
                String fromServer = in.readUTF();
                System.out.println(fromServer);
            } catch (IOException e) {
                try {
                    in.close();
                    this.s.close();
                } catch (IOException ex) {
                    System.err.printf("An error has occurred: %s%n", ex.getMessage());
                }
                break;
            }
        }
    }
}
