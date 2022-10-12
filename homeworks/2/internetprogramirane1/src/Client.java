import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void ListenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your nickname: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 6666);
        Client client = new Client(socket, username);
        client.ListenForMessage();
        client.sendMessage();

//        String username = null;
//        int i =0;
//        Scanner scanner = new Scanner(System.in);
//        while ( i < 1){
//            if (scanner.nextLine().startsWith("/nick")) {
//                String[] messageSplit = scanner.nextLine().split(" ", 2);
//                if (messageSplit.length == 2) {
//                     username = messageSplit[1];
//                     i=1;
//                }else{
//                    System.out.println("No name");
//            }
//
//        }
//            Socket socket = new Socket("localhost", 6666);
//            Client client = new Client(socket, username);
//
//            if (scanner.nextLine().startsWith("/quit")){
//                client.
//            }
//            if(scanner.nextLine().startsWith("/msg")){
//                client.ListenForMessage();
//                client.sendMessage();
//            }
//
//
//        }

    }
}


