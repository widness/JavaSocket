package common;
import java.util.Date;
import java.util.logging.*;
import java.io.*;

public class loggingManager extends Formatter {

    private Logger myLogger;
    private FileHandler fh;

    public loggingManager() {
        super();

        myLogger = Logger.getLogger("TestLog");

        try {
            fh = new FileHandler("./my.log",true);
            myLogger.addHandler(fh);

            loggingManager myFormatter = new loggingManager();
            fh.setFormatter(myFormatter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addInfoLog(String message){
        myLogger.setLevel(Level.INFO);
        myLogger.info(message);
    }

    public void addWarningLog(String message){
        myLogger.setLevel(Level.WARNING);
        myLogger.info(message);
    }

    public void addSeverLog(String message){
        myLogger.setLevel(Level.SEVERE);
        myLogger.info(message);
    }

    public String format(LogRecord record) {

        // Create a StringBuffer to contain the formatted record
        StringBuffer sb = new StringBuffer();

        // Get the date from the LogRecord and add it to the buffer
        Date date = new Date(record.getMillis());
        sb.append(date.toString());
        sb.append(";");

        sb.append(record.getSourceClassName());
        sb.append(";");

        // Get the level name and add it to the buffer
        sb.append(record.getLevel().getName());
        sb.append(";");

        sb.append(formatMessage(record));
        sb.append("Hello world");
        sb.append(";");
        sb.append("\r\n");

        return sb.toString();
    }
}
