package ChatApplication;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 5000;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
    	
    	Scanner q=new Scanner(System.in);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");

            while (true) {
            	
           
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                System.out.println("enter client name");
            	String name=q.next();
                ClientHandler clientHandler = new ClientHandler(clientSocket,name);
                clients.add(clientHandler);
                Thread handlerThread = new Thread(clientHandler);
                handlerThread.start();
            }
        } catch (IOException e) {
        	System.out.println("here CH");

            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender,String rec) {
    	cryptograph c=new cryptograph();
        for (ClientHandler handler : clients) {
        	if(rec.equals("All")) {
        		if(handler !=sender) {
        			handler.sendMessage("sender name is : "+sender.name+"\n message : "+message);
        		}
        	}
            if (handler.name.equals(rec) ) {
                handler.sendMessage("sender name is : "+sender.name+" "+message+" handler name is : "+handler.name);
            }
        }
    }
  
 
}


