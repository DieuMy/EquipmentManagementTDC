package vn.edu.tdc.managementequipmenttdc.data_models;

import java.util.Date;

public class Users {
    private String userID;
    private String fullName;
    private String gender;
    private String address;
    private String numberPhone;
    private String email;
    private String roleID;
    private String departmentID;
    private String avartaUser;
    private boolean active;
    private boolean lock_account;
    private String create_at;
    private String update_at;
    private String last_changePassword;
    private String lastAccess;

    public Users() {
    }

    public Users(String userID, String fullName, String gender, String address, String numberPhone, String email, String roleID, String departmentID, String avartaUser, boolean active, boolean lock_account, String create_at, String update_at, String last_changePassword, String lastAccess) {
        this.userID = userID;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.numberPhone = numberPhone;
        this.email = email;
        this.roleID = roleID;
        this.departmentID = departmentID;
        this.avartaUser = avartaUser;
        this.active = active;
        this.lock_account = lock_account;
        this.create_at = create_at;
        this.update_at = update_at;
        this.last_changePassword = last_changePassword;
        this.lastAccess = lastAccess;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getAvartaUser() {
        return avartaUser;
    }

    public void setAvartaUser(String avartaUser) {
        this.avartaUser = avartaUser;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLock_account() {
        return lock_account;
    }

    public void setLock_account(boolean lock_account) {
        this.lock_account = lock_account;
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

    public String getLast_changePassword() {
        return last_changePassword;
    }

    public void setLast_changePassword(String last_changePassword) {
        this.last_changePassword = last_changePassword;
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(String lastAccess) {
        this.lastAccess = lastAccess;
    }
}
