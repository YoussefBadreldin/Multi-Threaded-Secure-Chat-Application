//package test1;
//import java.io.*;
//import java.net.*;
//import java.util.Scanner;
//public class Client {
//	
//	    private static  String SERVER_ADDRESS = "127.0.0.2"; // Replace with the server's IP address
//	    private static final int PORT = 5000;
//	
//	    
//		public static void main(String[] args) {
//	        try (
//	        		
//	            Socket socket = new Socket(SERVER_ADDRESS, PORT);
//	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//	            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
//	        ) {
//	        	
//	            System.out.println("Connected to the server." );
//
//	            Thread receiveThread = new Thread(() -> {
//	                try {
//	                    String received;
//	                    while ((received = in.readLine()) != null) {
//	                        System.out.println("Received from server: " + received);
//	                    }
//	                } catch (IOException e) {
//	                    e.printStackTrace();
//	                }
//	            });
//	            receiveThread.start();
//
//	            String userInput;
//	            while ((userInput = consoleInput.readLine()) != null) {
//	                out.println(userInput);
//	                if (userInput.equalsIgnoreCase("exit")) {
//	                    break;
//	                }
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}
//
//
//
