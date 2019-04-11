package vn.edu.tdc.managementequipmenttdc.data_models;

public class AreaBuildingCardviewModel {
    private String areaName;

    public AreaBuildingCardviewModel(int ic_login, String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
