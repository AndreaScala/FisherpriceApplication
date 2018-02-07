package utilities;

public class LogEntry {
    private String timeStamp;
    private String MachineID;
    private String messageString;

    public LogEntry(String timeStamp, String MachineID, String messageString) {
        this.timeStamp = timeStamp;
        this.MachineID = MachineID;
        this.messageString = messageString;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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