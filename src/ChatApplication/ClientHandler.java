package ChatApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private FileTransfer fileTransfer;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.fileTransfer = new FileTransfer(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("FILE")) {
                    fileTransfer.receiveFile();
                } else {
                    System.out.println("Received from client: " + inputLine);
                    ChatServer.broadcastMessage(inputLine, this);
                }

                if (inputLine.equals("exit")) {
                    break;
                }
            }

            System.out.println("Client disconnected: " + clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
