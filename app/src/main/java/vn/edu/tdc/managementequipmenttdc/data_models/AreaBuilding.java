package vn.edu.tdc.managementequipmenttdc.data_models;

public class AreaBuilding {
    private String areaID;
    private String areaName;
    private String description;
    private int numberOfFloors;
    private String userID;
    private String create_at;
    private String update_at;

    public AreaBuilding() {
    }

    public AreaBuilding(String areaID, String areaName, String description, int numberOfFloors, String userID, String create_at, String update_at) {
        this.areaID = areaID;
        this.areaName = areaName;
        this.description = description;
        this.numberOfFloors = numberOfFloors;
        this.userID = userID;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
