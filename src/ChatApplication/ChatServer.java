package ChatApplication;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                Thread handlerThread = new Thread(clientHandler);
                handlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler handler : clients) {
            if (handler != sender) {
                handler.sendMessage(message);
            }
        }
    }
}
