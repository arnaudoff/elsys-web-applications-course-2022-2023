import com.sun.source.util.SourcePositions;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run() {
        try{
            client = new Socket("127.0.0.1", 4444);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null){
                System.out.println(inMessage);
            }

        }catch (IOException e){
            quit();
        }
    }

    public void quit(){
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }

        }catch (IOException e){
            //
        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            try{
                BufferedReader inReader = new BufferedReader( new  InputStreamReader(System.in));
                while (!done){
                    String message = inReader.readLine();
                    if (message.equals("/quit")){
                        out.println(message);   //sending the command to the server
                        inReader.close();
                        quit();
                    } else {
                        out.println(message);
                    }
                }
            }catch (IOException e){
                quit();
            }

        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
