package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Server s = new Server();
        try {
            s.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

