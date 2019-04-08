package vn.edu.tdc.managementequipmenttdc.data_models;

public class Role_Group {
    private String roleGroupID;
    private String roleID;
    private String functionID;
    private String permiss;
    private String create_at;
    private String update_at;

    public Role_Group() {
    }

    public Role_Group(String roleGroupID, String roleID, String functionID, String permiss, String create_at, String update_at) {
        this.roleGroupID = roleGroupID;
        this.roleID = roleID;
        this.functionID = functionID;
        this.permiss = permiss;
        this.create_at = create_at;
        this.update_at = update_at;
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

    public String getRoleGroupID() {
        return roleGroupID;
    }

    public void setRoleGroupID(String roleGroupID) {
        this.roleGroupID = roleGroupID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
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
}
