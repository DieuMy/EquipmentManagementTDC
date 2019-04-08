package vn.edu.tdc.managementequipmenttdc.data_models;

public class Log {
    private String logID;
    private String userID;
    private String manipulation;
    private String dateManipulation;
    private String create_at;
    private String update_at;

    public Log() {
    }

    public Log(String logID, String userID, String manipulation, String dateManipulation, String create_at, String update_at) {
        this.logID = logID;
        this.userID = userID;
        this.manipulation = manipulation;
        this.dateManipulation = dateManipulation;
        this.create_at = create_at;
        this.update_at = update_at;
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

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
