package vn.edu.tdc.managementequipmenttdc.data_models;

public class Permissions {
    private String userID;
    private String functionID;
    private String permiss;
    private String create_at;
    private String update_at;

    public Permissions() {
    }

    public Permissions(String userID, String functionID, String permiss, String create_at, String update_at) {
        this.userID = userID;
        this.functionID = functionID;
        this.permiss = permiss;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFunctionID() {
        return functionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public String getPermiss() {
        return permiss;
    }

    public void setPermiss(String permiss) {
        this.permiss = permiss;
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
