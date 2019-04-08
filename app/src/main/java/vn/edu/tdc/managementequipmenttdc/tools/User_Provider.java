package vn.edu.tdc.managementequipmenttdc.tools;

import vn.edu.tdc.managementequipmenttdc.data_models.Users;

public class User_Provider {
    public static String username;
    public static Users user;

    private boolean canAdd, canModify, canDelete;

    public void permissionFunction(String functionID) {
        //Lay du lieu permission cua function theo functionID : A&D&M
        String str_permission = "";
        //Cat chuoi dua vao mang string[] = {A, D, M}
        String[] arrayPermission = str_permission.split("&");
        //duyet permission
        for(String item : arrayPermission){
            switch (item){
                case "A":
                    canAdd = true;
                    break;
                case "D":
                    canDelete = true;
                    break;
                case "M":
                    canModify = true;
                    break;
                case "F":
                    canAdd = true;
                    canDelete = true;
                    canModify = true;
                    break;
                case "N":
                    canAdd = false;
                    canDelete = false;
                    canModify = false;
                    break;
                    default:
                        break;
            }
        }

    }
}
