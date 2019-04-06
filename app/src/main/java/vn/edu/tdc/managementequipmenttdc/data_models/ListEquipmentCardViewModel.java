package vn.edu.tdc.managementequipmenttdc.data_models;

public class ListEquipmentCardViewModel {
    private String functionName;

    public ListEquipmentCardViewModel(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}