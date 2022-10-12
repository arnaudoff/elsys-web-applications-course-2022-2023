import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements  Runnable {
    private  Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private String host;
    private int port;


    public Client(String host,int port){
        this.host=host;
        this.port=port;
    }
    @Override
    public void run() {
        try {


            client = new Socket(host, port);
            //client = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();
            String inMessage;
            while((inMessage = in.readLine())!=null){
                System.out.println(inMessage);
            }
        }catch (Exception e ){

        }
    }
    public void shutdown(){
        done = true;
        try {
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }

        }catch (IOException e ){

        }
    }
    class InputHandler implements Runnable{
        @Override
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done){
                    String message = inReader.readLine();
                    if(message.equals("/quit")){
                        out.println(message);
                        inReader.close();
                        shutdown();
                    }else {
                        out.println(message);
                    }
                }

            }catch (Exception e){
                shutdown();
            }

        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        String name = read.readLine();
        String host;
        int port;
        if(name.equals("/conect")) {
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            host = reader.readLine();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));

            // Reading data using readLine
            port = Integer.parseInt(reader2.readLine());
        }
        else{
            throw new Exception("Trqbava da napishesh (/conect ,posle hosta i posle porta na survera za da stane)");
        }
        Client client = new Client(host,port);
        client.run();
    }
}
