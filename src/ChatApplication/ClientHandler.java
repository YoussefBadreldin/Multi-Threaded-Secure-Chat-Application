package ChatApplication;
import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private String name;
    private FileTransfer fileTransfer;

    public ClientHandler(Socket clientSocket, String name) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.name = name;
        fileTransfer = new FileTransfer(clientSocket);
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("FILE_TRANSFER")) {
                    String filePath = inputLine.split(" ")[1];
                    fileTransfer.receiveFile(filePath);
                } else {
                    System.out.println("Received from client: "+this.name+ inputLine);
                    String text = inputLine;
                    String[] words = text.split("#"); // Split recipient name
                    String mes=words[1];
                    String rec=words[0];
                    ChatServer.broadcastMessage(mes, this,rec);

                    if (inputLine.equals("exit")) {
                        break;
                    }
                }
            }

            System.out.println("Client disconnected: " + clientSocket);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("An error occurred while handling the client.");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getName() {
        return name;
    }
}
