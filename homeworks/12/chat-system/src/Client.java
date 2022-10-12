import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String nickname;

    public Client(Socket socket, String nickname) {
        try {
            this.socket = socket;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.nickname = nickname;
        } catch (IOException exception){
            try {
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            }catch (Exception exception1) {
                System.out.println(exception1);
            }
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(nickname);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                if(message.startsWith("/msg")) {
                    String newString = message.substring("/msg".length());
                    bufferedWriter.write(nickname + ": " + newString);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
        } catch (IOException exception) {
            try {
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            }catch (Exception exception1) {
                System.out.println(exception1);
            }
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String msgFromGroupChat;

            while (socket.isConnected()) {
                try {
                    msgFromGroupChat = bufferedReader.readLine();
                    System.out.println(msgFromGroupChat);
                } catch (IOException exception) {
                    try {
                        bufferedReader.close();
                        bufferedWriter.close();
                        socket.close();
                    }catch (Exception exception1) {
                        System.out.println(exception1);
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String nickname = null;
        String host = null;
        int port = 0;
        Socket socket = new Socket();
        while(host == null || port == 0 || nickname == null) {
            String command = scanner.nextLine();
            if (command.startsWith("/nick")) {
                nickname = command.substring("/nick".length());
            } else if (command.startsWith("/connect")) {
                ArrayList<String> strings = new ArrayList<>(List.of(command.split(" ")));
                host = strings.get(1);
                port = Integer.parseInt(strings.get(2));
                socket = new Socket(host, port);
            }
        }
        Client client = new Client(socket, nickname);
        client.listenForMessage();
        client.sendMessage();
    }
}
