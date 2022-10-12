package Client;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientReceiver  extends Thread {

    private Socket socket;

    ClientReceiver(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            while(true){
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String fromServer = input.readUTF();
                System.out.println(fromServer);
            }
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
