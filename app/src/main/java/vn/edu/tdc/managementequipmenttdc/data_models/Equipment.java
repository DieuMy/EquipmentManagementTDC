package vn.edu.tdc.managementequipmenttdc.data_models;

public class Equipment {
    private String equipmentID;
    private String equipmentName;
    private boolean status;
    private String datePurchase;
    private String parentID;
    private String roomID;
    private String create_at;
    private String update_at;

    public Equipment() {
    }

    public Equipment(String equipmentID, String equipmentName, boolean status, String datePurchase, String parentID, String roomID, String create_at, String update_at) {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.status = status;
        this.datePurchase = datePurchase;
        this.parentID = parentID;
        this.roomID = roomID;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
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
