package ChatApplication;
import java.io.*;
import java.net.*;

public class Client {
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int PORT = 5000;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private FileTransfer fileTransfer;

	public Client() throws IOException {
		socket = new Socket(SERVER_ADDRESS, PORT);
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		fileTransfer = new FileTransfer(socket);
	}

	public void sendFile(String filePath) throws IOException {
		out.println("FILE_TRANSFER " + filePath);
		fileTransfer.sendFile(filePath);
	}

	public static void main(String[] args) {
		try (
				BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
		) {
			Client client = new Client();
			System.out.println("Connected to the server.");

			Thread receiveThread = new Thread(() -> {
				try {
					String received;
					while ((received = client.in.readLine()) != null) {
						System.out.println("Received from server: " + received);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			receiveThread.start();

			String userInput;
			while ((userInput = consoleInput.readLine()) != null) {
				if (userInput.startsWith("FILE_TRANSFER")) {
					String filePath = userInput.split(" ")[1];
					client.sendFile(filePath);
				} else {
					client.out.println(userInput);
				}
				if (userInput.equalsIgnoreCase("exit")) {
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred while connecting to the server.");
			e.printStackTrace();
		}
	}
}
