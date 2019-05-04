package vn.edu.tdc.managementequipmenttdc.data_models;

public class Log {
    private String logID;
    private String userID;
    private String manipulation;
    private String dateManipulation;

    public Log() {
    }

    public Log(String logID, String userID, String manipulation, String dateManipulation) {
        this.logID = logID;
        this.userID = userID;
        this.manipulation = manipulation;
        this.dateManipulation = dateManipulation;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getManipulation() {
        return manipulation;
    }

    public void setManipulation(String manipulation) {
        this.manipulation = manipulation;
    }

    public String getDateManipulation() {
        return dateManipulation;
    }

    public void setDateManipulation(String dateManipulation) {
        this.dateManipulation = dateManipulation;
    }
}
