import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket client;
    private BufferedReader input;
    private PrintWriter output;
    private boolean done;
    private String clientHost;
    private int clientPort;

    @Override
    public void run() {
        try {
            System.out.println("To connect to server use '/connect <ip of chat server> <port>'.");
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            String message = inputReader.readLine();
            while (client == null) {
                if (message.startsWith("/connect")) {
                    String[] splittedMessage = message.split(" ", 3);
                    clientHost = splittedMessage[1];
                    clientPort = Integer.parseInt(splittedMessage[2]);
                    client = new Socket(clientHost, clientPort);
                }
            }

            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            InputHandler inputHandler = new InputHandler();
            Thread thread = new Thread(inputHandler);
            thread.start();
            String inMessage;
            while ((inMessage = input.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            input.close();
            output.close();
            if (!client.isClosed()) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class InputHandler implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inputReader.readLine();
                    output.println(message);
                    if (message.equals("/quit")) {
                        inputReader.close();
                        shutdown();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}






