package vn.edu.tdc.managementequipmenttdc.data_models;

public class Role {
    private String roleID;
    private String roleName;
    private String description;
    private String create_at;
    private String update_at;

    public Role() {
    }

    public Role(String roleID, String roleName, String description, String create_at, String update_at) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.description = description;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
