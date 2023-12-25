package ChatApplication;
import java.io.*;
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
                System.out.println("Enter client name");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String name = reader.readLine();
                ClientHandler clientHandler = new ClientHandler(clientSocket, name);
                clients.add(clientHandler);
                Thread handlerThread = new Thread(clientHandler);
                handlerThread.start();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while starting the server.");
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender, String rec) {
        for (ClientHandler handler : clients) {
            if (rec.equals("All")) {
                if (handler != sender) {
                    handler.sendMessage("Sender name is: " + sender.getName() + "\nMessage: " + message);
                }
            }
            if (handler.getName().equals(rec)) {
                handler.sendMessage("Sender name is: " + sender.getName() + " " + message + " Handler name is: " + handler.getName());
            }
        }
    }
}
