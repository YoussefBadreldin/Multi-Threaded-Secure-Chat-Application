package ChatApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ClientThread implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    public String name;
    

    public ClientThread(Socket clientSocket,String name ) {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
        	System.out.println("here 2");
            e.printStackTrace();
        }
        this.name=name;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from client: "+this.name+ inputLine);
                String text = inputLine;
                String[] words = text.split("#"); // Split recipient name 
                String mes=words[1]+"#"+words[2]+"#"+words[3];
                String rec=words[0];
                ChatServer.sendMessage(mes, this,rec);

                if (inputLine.equals("exit")) {
                    break;
                }
            }

            System.out.println("Client disconnected: " + clientSocket);
            clientSocket.close();
        } catch (IOException e) {
        	System.out.println("here 1");
            e.printStackTrace();
        }
    }
    public void sendMessage(String message) {
        out.println(message);
    }
}

