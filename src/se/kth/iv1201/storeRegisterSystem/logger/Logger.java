package se.kth.iv1201.storeRegisterSystem.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private PrintWriter pw;
    private DateFormat dateFormat;

    public Logger() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String time = dateFormat.format(new Date());
        String logFileName = "store_register_system";
        try {
            pw = new PrintWriter(new FileWriter(logFileName + "-" + time + ".log"), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logException(Exception ex) {
        String time = dateFormat.format(new Date());
        String logEntry = time + ": " + ex.getMessage();
        pw.println(logEntry);
    }
}
