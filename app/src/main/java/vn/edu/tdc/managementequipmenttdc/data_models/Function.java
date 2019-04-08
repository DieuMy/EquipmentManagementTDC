package vn.edu.tdc.managementequipmenttdc.data_models;

public class Function {
    private String functionID;
    private String functionName;
    private String description;
    private String iconFunction;
    private String create_at;
    private String update_at;

    public Function() {
    }

    public Function(String functionID, String functionName, String description, String iconFunction, String create_at, String update_at) {
        this.functionID = functionID;
        this.functionName = functionName;
        this.description = description;
        this.iconFunction = iconFunction;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getFunctionID() {
        return functionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconFunction() {
        return iconFunction;
    }

    public void setIconFunction(String iconFunction) {
        this.iconFunction = iconFunction;
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
