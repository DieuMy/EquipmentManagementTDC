package vn.edu.tdc.managementequipmenttdc.data_models;

public class ListMalfunctionEquipmentModels {
    private  String malfunctionEquipmentContent;
    private  String malfunctionEquipmentDate;

    public ListMalfunctionEquipmentModels(String malfunctionEquipmentContent, String malfunctionEquipmentDate) {
        this.malfunctionEquipmentContent = malfunctionEquipmentContent;
        this.malfunctionEquipmentDate = malfunctionEquipmentDate;
    }

    public String getMalfunctionEquipmentContent() {
        return malfunctionEquipmentContent;
    }

    public void setMalfunctionEquipmentContent(String malfunctionEquipmentContent) {
        this.malfunctionEquipmentContent = malfunctionEquipmentContent;
    }

    public String getMalfunctionEquipmentDate() {
        return malfunctionEquipmentDate;
    }

    public void setMalfunctionEquipmentDate(String malfunctionEquipmentDate) {
        this.malfunctionEquipmentDate = malfunctionEquipmentDate;
    }
}
