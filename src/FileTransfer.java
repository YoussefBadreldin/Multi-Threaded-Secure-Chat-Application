import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTransfer {
    private File file;
    private String fileName;
    private long fileSize;
    private long timeStamp;

    public FileTransfer(String filePath) {
        try {
            this.file = new File(filePath);
            this.fileName = file.getName();
            this.fileSize = file.length();
            Path path = Paths.get(filePath);
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            this.timeStamp = attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getFileName() {
        return this.fileName;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }
}
