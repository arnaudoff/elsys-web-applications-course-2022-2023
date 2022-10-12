import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private int port;
    private String ip;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
       try{
           client = new Socket(ip, port);
           out = new PrintWriter(client.getOutputStream(), true);
           in = new BufferedReader(new InputStreamReader(client.getInputStream()));

           InputHandler inputHandler = new InputHandler();
           Thread t = new Thread(inputHandler);
           t.start();

           String inmessage;
           while((inmessage = in.readLine())!= null){
               System.out.println(inmessage);
           }
       } catch (IOException e){
            close();
       }
    }

    public void close(){
        done = true;
        try{
            in.close();
            out.close();
            if(!client.isClosed()){
                client.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    class InputHandler implements Runnable{

        @Override
        public void run() {
               try{
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                while(!done){
                    String message = input.readLine();
                    out.println(message);
                    if(message.equals("/quit")){
                        input.close();
                        close();
                    }
                }
            }catch (IOException e){
                close();
            }
        }
    }

    public static void main(String[] args){
        System.out.println("Welcome to our chat system! Type /connect <ip of chat server>:<port> to connect to chat room!");
        String message;
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String clientIp = null;
        int clientPort = 0;
        try {
            while ((message = input.readLine()) != null){
                if(message.startsWith("/connect")){
                    String[] newMessage = message.split(" ", 2);
                    String[] info = newMessage[1].split(":", 2);
                    clientIp = info[0];
                    clientPort = Integer.parseInt(info[1]);

                    break;
                }else{
                    System.out.println("The command you have entered is incorrect! Type /connect <ip of chat server>:<port> to connect to chat room! ");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Client client = new Client(clientIp, clientPort);
        client.run();
    }
}
