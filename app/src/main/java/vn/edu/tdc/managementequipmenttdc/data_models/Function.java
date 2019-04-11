package vn.edu.tdc.managementequipmenttdc.data_models;

public class Function {
    private String functionID;
    private String functionName;
    private String description;
    private String activityClass;
    private String packageClass;
    private String iconFunction;
    private String create_at;
    private String update_at;

    public Function() {
    }

    public Function(String functionID, String functionName, String description, String activityClass, String packageClass, String iconFunction, String create_at, String update_at) {
        this.functionID = functionID;
        this.functionName = functionName;
        this.description = description;
        this.activityClass = activityClass;
        this.packageClass = packageClass;
        this.iconFunction = iconFunction;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getPackageClass() {
        return packageClass;
    }

    public void setPackageClass(String packageClass) {
        this.packageClass = packageClass;
    }

    public String getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(String activityClass) {
        this.activityClass = activityClass;
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

    @Override
    public String toString() {
        return "Function{" +
                "functionID='" + functionID + '\'' +
                ", functionName='" + functionName + '\'' +
                ", description='" + description + '\'' +
                ", activityClass='" + activityClass + '\'' +
                ", packageClass='" + packageClass + '\'' +
                ", iconFunction='" + iconFunction + '\'' +
                ", create_at='" + create_at + '\'' +
                ", update_at='" + update_at + '\'' +
                '}';
    }
}
