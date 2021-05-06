package stocks_management.services;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    private static AuditService instance = null;

    private BufferedWriter writer;

    private AuditService() {

        try {
            File outputFile = new File("./resources/audit.csv");
            outputFile.createNewFile();

            writer = new BufferedWriter(new FileWriter(outputFile, true));

        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public static AuditService getInstance() {

        if(instance == null) {
            instance = new AuditService();
        }

        return instance;
    }

    public void writeAction(String actionMessage) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        try {
            writer.write(actionMessage + ", " + date + "\n");
        } catch (IOException exception) {
            System.out.println("EXCEPTION: " + exception.getMessage());
        }
    }

    public void close() throws IOException {
        writer.close();
    }
}
