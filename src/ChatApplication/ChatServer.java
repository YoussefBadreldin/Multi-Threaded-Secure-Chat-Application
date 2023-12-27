package ChatApplication;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static List<ClientThread> clients =new ArrayList<>();
    public static void main(String[] args) {
    	
    	Scanner q=new Scanner(System.in);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                System.out.println("enter client name");
            	String name=q.next();
            	ClientThread Handler = new  ClientThread(clientSocket,name);
                clients.add(Handler);
                Thread handlerThread = new Thread(Handler);
                handlerThread.start();
            }
        } catch (IOException e) {
        	System.out.println("here CH");

            e.printStackTrace();
        }
    }

    public static void sendMessage(String message,  ClientThread sender,String rec) {
    	
        for ( ClientThread handler : clients) {
        	if(rec.equalsIgnoreCase("All")) {
        		if(handler !=sender) {
        			handler.sendMessage(message);
        		}
        	}
            if (handler.name.equalsIgnoreCase(rec) ) {
                handler.sendMessage(message);
            }
        }
    }
  
 
}


