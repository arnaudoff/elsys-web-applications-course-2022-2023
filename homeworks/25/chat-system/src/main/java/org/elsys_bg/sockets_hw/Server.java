package org.elsys_bg.sockets_hw;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {

	private static List<Socket> clientSockets = new ArrayList<>();
	private static Map<Integer, String> clientNames = new HashMap<>();

	public static void main(String argv[]) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a");  
        LocalDateTime now = LocalDateTime.now();

		try {
			ServerSocket serverSocket = new ServerSocket(6789);

			while(true) {
				System.out.println("Listening for new connections on port " + serverSocket.getLocalPort() + " ...");

				Socket clientSocket = serverSocket.accept();
				clientSockets.add(clientSocket);

				(new Thread(() -> {

					try {
						BufferedReader inFromClient = new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream())
						);

						do {
							String clientMessage = inFromClient.readLine();
							if(clientMessage == null) {
								clientSockets.remove(clientSocket);
								System.out.println("Client disconnected from " + clientSocket.getInetAddress().toString());
								return;
							}
							String[] tokens = clientMessage.trim().split("\\s+", 2);

            				// Execute chat room commands
							switch(tokens[0]) {
								/* ----------------------------------------------------------------------------- */
								case "/msg":
									System.out.println("Received: " + clientMessage);
									
									for(Socket s : clientSockets) {
										DataOutputStream outToClient = new DataOutputStream(s.getOutputStream());
										outToClient.writeBytes(
											String.format("[%s] %s: %s" + '\n',
														  dtf.format(now),
														  clientNames.containsKey(clientSocket.getPort()) ?
														      clientNames.get(clientSocket.getPort()) : "Guest",
														  tokens[1])
										);
									}

									break;

								/* ----------------------------------------------------------------------------- */
								case "/nick":
									DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
									if(clientNames.containsValue(tokens[1])) {
										outToClient.writeBytes(
											String.format("The nickname %s is already in use!" + '\n', tokens[1])
										);

									}else {
										clientNames.put(clientSocket.getPort(), tokens[1]);
										System.out.println(
											String.format("%s:%d set nickname to %s",
												clientSocket.getInetAddress().toString(),
												clientSocket.getPort(),
												tokens[1]
											));
										outToClient.writeBytes("Nickname set successfully!" + '\n');
									}

									break;
							}

						}while(true);

					} catch (IOException e) {
						e.printStackTrace();
					}

				})).start();

				System.out.println("Client connected from " + clientSocket.getInetAddress().toString());
	
				DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
				outToClient.writeBytes("Welcome to the chat room! Use /nick to set your nickname." + '\n');
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
