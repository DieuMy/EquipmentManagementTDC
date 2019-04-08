package vn.edu.tdc.managementequipmenttdc.data_models;

public class Department {
    private String departmentID;
    private String departmentName;
    private String description;
    private String create_at;
    private String update_at;

    public Department() {
    }

    public Department(String departmentID, String departmentName, String description, String create_at, String update_at) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.description = description;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
