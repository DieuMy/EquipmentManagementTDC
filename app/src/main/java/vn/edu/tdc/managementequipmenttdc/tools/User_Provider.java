package vn.edu.tdc.managementequipmenttdc.tools;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import vn.edu.tdc.managementequipmenttdc.data_models.Users;

public class User_Provider {
    public static Users user;
    public static String roleNameOfCurrentUser;//Get data from personalPage
    private boolean canAdd, canModify, canDelete;

    public void permissionFunction(String functionID) {
        //Lay du lieu permission cua function theo functionID : A&D&M
        String str_permission = "";
        //Cat chuoi dua vao mang string[] = {A, D, M}
        String[] arrayPermission = str_permission.split("&");
        //duyet permission
        for (String item : arrayPermission) {
            switch (item) {
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
