package vn.edu.tdc.managementequipmenttdc.data_models;

public class RepairDiary {
    private String repairDiaryID;
    private String equipmentID;
    private String roomID;
    private String userIDReport;//Nguoi bao cao
    private String incident_content;//Noi dung su co
    private String dateReport;//Ngay bao cao
    private String userIDReceive;//Nhan vien nhan sua chua
    private boolean statusReceive; //Nhan vien tiep nhan xu y su co chua
    private String maintenanceContent; //Loi that su cua thiet bi(nhan vien sua chua cap nhat)
    private String processingDate; //Ngay nhan xu ly su co
    private boolean processingStatus;//Tinh trang su co duoc xu ly hay chua
    private String dateComplete;//Ngay hoan thanh sua chua
    private String confirmRepaired;//Xac nhan hoan thanh sua chua(nguoi bao cao loi: giang vien)
    private int rating; //Danh gia (1*, 2*, 3*..)
    private String create_at;
    private String update_at;

    public RepairDiary() {
    }

    public RepairDiary(String repairDiaryID, String equipmentID, String roomID, String userIDReport, String incident_content, String dateReport, String userIDReceive, boolean statusReceive, String maintenanceContent, String processingDate, boolean processingStatus, String dateComplete, String confirmRepaired, int rating, String create_at, String update_at) {
        this.repairDiaryID = repairDiaryID;
        this.equipmentID = equipmentID;
        this.roomID = roomID;
        this.userIDReport = userIDReport;
        this.incident_content = incident_content;
        this.dateReport = dateReport;
        this.userIDReceive = userIDReceive;
        this.statusReceive = statusReceive;
        this.maintenanceContent = maintenanceContent;
        this.processingDate = processingDate;
        this.processingStatus = processingStatus;
        this.dateComplete = dateComplete;
        this.confirmRepaired = confirmRepaired;
        this.rating = rating;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getRepairDiaryID() {
        return repairDiaryID;
    }

    public void setRepairDiaryID(String repairDiaryID) {
        this.repairDiaryID = repairDiaryID;
    }

    public String getUserIDReceive() {
        return userIDReceive;
    }

    public void setUserIDReceive(String userIDReceive) {
        this.userIDReceive = userIDReceive;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getUserIDReport() {
        return userIDReport;
    }

    public void setUserIDReport(String userIDReport) {
        this.userIDReport = userIDReport;
    }

    public String getIncident_content() {
        return incident_content;
    }

    public void setIncident_content(String incident_content) {
        this.incident_content = incident_content;
    }

    public String getDateReport() {
        return dateReport;
    }

    public void setDateReport(String dateReport) {
        this.dateReport = dateReport;
    }

    public boolean isStatusReceive() {
        return statusReceive;
    }

    public void setStatusReceive(boolean statusReceive) {
        this.statusReceive = statusReceive;
    }

    public String getMaintenanceContent() {
        return maintenanceContent;
    }

    public void setMaintenanceContent(String maintenanceContent) {
        this.maintenanceContent = maintenanceContent;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public boolean isProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(boolean processingStatus) {
        this.processingStatus = processingStatus;
    }

    public String getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(String dateComplete) {
        this.dateComplete = dateComplete;
    }

    public String getConfirmRepaired() {
        return confirmRepaired;
    }

    public void setConfirmRepaired(String confirmRepaired) {
        this.confirmRepaired = confirmRepaired;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
