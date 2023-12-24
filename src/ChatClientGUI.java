package test1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;

public class ChatClientGUI {
    private static final String SERVER_ADDRESS = "127.0.0.2";
    private static final int PORT = 5000;
    private static Socket socket;
    private static PrintWriter out;
    public static  cryptograph c1=new cryptograph();
    public static void main(String[] args) {
    	//cryptograph c1=new cryptograph();
    	 c1.generateKeys(1024);
    	Scanner x=new Scanner (System.in);
    	String name =x.next();
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JTextField messageField = new JTextField();
        panel.add(messageField, BorderLayout.SOUTH);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String message = messageField.getText();
            
           
            if (!message.isEmpty()) {
                out.println(message);
                messageField.setText("");
            }
        });
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel);
        frame.setVisible(true);

        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread receiveThread = new Thread(() -> {  // Thread to receive messages 
                try {
                    String received;
                    while ((received = in.readLine()) != null) {
                       chatArea.append("Received: " + received + "\n");
                   }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            receiveThread.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        messageField.addActionListener(e -> {	// Thread to send messages
            String message = messageField.getText();
            String text = message;
            String[] words = text.split("#");
            BigInteger plaintext = new BigInteger(words[1].getBytes()); //convert string to Bytes
            BigInteger enc = c1.encrypt(plaintext); // to encript message
            if (!message.isEmpty()) {
                out.println(words[0]+"#"+enc);
                messageField.setText("");
            }
        });
    }
}

