package vn.edu.tdc.managementequipmenttdc.data_models;

public class HomeScreenCardViewModel {
    private int imgFunctionID;
    private String functionName;

    public HomeScreenCardViewModel(int imgFunctionID, String functionName) {
        this.imgFunctionID = imgFunctionID;
        this.functionName = functionName;
    }

    public int getImgFunctionID() {
        return imgFunctionID;
    }

    public void setImgFunctionID(int imgFunctionID) {
        this.imgFunctionID = imgFunctionID;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
