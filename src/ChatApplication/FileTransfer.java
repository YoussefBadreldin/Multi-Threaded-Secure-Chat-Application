package ChatApplication;

import java.io.*;
import java.net.*;

public class FileTransfer {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public FileTransfer(Socket socket) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void sendFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileIn = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int read;

        out.writeUTF(file.getName());
        out.writeLong(file.length());

        while ((read = fileIn.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        fileIn.close();
    }

    public void receiveFile() throws IOException {
        String fileName = in.readUTF();
        long fileSize = in.readLong();
        String savePath = "\\Multi-Threaded-Secure-Chat-Application\\Files";
        FileOutputStream fileOut = new FileOutputStream(savePath + "\\" + fileName);
        byte[] buffer = new byte[4096];
        int read;
        long totalRead = 0L;
        int remaining = (int) fileSize;

        while ((read = in.read(buffer, 0, Math.min(buffer.length, remaining))) != -1) {
            totalRead += read;
            remaining -= read;
            fileOut.write(buffer, 0, read);
        }

        fileOut.close();
    }

    public void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }
}
