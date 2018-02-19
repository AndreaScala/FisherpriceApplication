package utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private LocalDateTime timeStamp;
    private String MachineID;
    private String messageString;

    public LogEntry(String timeStamp, String MachineID, String messageString) {
        this.timeStamp = LocalDateTime.parse(timeStamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        this.MachineID = MachineID;
        this.messageString = messageString;
    }

    public LogEntry(LocalDateTime timeStamp, String MachineID, String messageString) {
        this.timeStamp = timeStamp;
        this.MachineID = MachineID;
        this.messageString = messageString;
    }

    public String getTimeStamp() {
        return timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = LocalDateTime.parse(timeStamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public String getMachineID() {
        return MachineID;
    }

    public void setMachineID(String MachineID) {
        this.MachineID = MachineID;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    @Override
    public String toString() {
        return "LogEntry{" + "timeStamp=" + timeStamp + ", MachineID=" + MachineID + ", messageString=" + messageString + '}';
    }
    
}