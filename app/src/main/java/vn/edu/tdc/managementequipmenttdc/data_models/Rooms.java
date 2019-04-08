package vn.edu.tdc.managementequipmenttdc.data_models;

public class Rooms {
    private String roomID;
    private String roomName;
    private String description;
    private String areaID;
    private String userID;
    private String create_at;
    private String update_at;

    public Rooms() {
    }

    public Rooms(String roomID, String roomName, String description, String areaID, String userID, String create_at, String update_at) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.description = description;
        this.areaID = areaID;
        this.userID = userID;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAreaID() {
        return areaID;
    }

    public void setAreaID(String areaID) {
        this.areaID = areaID;
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
