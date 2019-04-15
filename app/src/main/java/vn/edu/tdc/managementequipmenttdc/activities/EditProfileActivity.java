package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.Department;
import vn.edu.tdc.managementequipmenttdc.data_models.Role;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class EditProfileActivity extends AppCompatActivity {
    private Intent intent;
    private Button btnChangePassword;
    private ImageView imgToolBarBack;
    private ImageView imgToolBarSave;
    private ImageView imgAvatar;
    private TextView txtFullName;
    private TextView txtAccount;
    private Spinner spnGender;
    private EditText edtAddress;
    private EditText edtNumberPhone;
    private EditText edtEmail;
    private Spinner spnRole;
    private Spinner spnDepartment;
    private TextView txtLastAccess;

    private ArrayList<Role> listRole = new ArrayList<Role>();
    private ArrayList<Department> listDepartment = new ArrayList<Department>();

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_flagment);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Gets view from layout
        btnChangePassword = (Button) findViewById(R.id.editProfileBtnChangePassword);
        imgToolBarBack = findViewById(R.id.editProfileToolBarBack);
        imgToolBarSave = findViewById(R.id.editProfileToolBarSave);
        txtFullName = findViewById(R.id.editProfileTxtFullName);
        txtAccount = findViewById(R.id.editProfileTxtAccountName);
        spnGender = findViewById(R.id.editProfileSpnGender);
        spnRole = findViewById(R.id.editProfileSpnRole);
        spnDepartment = findViewById(R.id.editProfileSpnDepartment);
        edtAddress = findViewById(R.id.editProfileEdtAddress);
        edtNumberPhone = findViewById(R.id.editProfileEdtNumberPhone);
        edtEmail = findViewById(R.id.editProfileEdtEmail);
        txtLastAccess = findViewById(R.id.editProfileTxtLastAccess);

        //Set adapter for spinner
        //Set data for spinner gender
        ArrayList<String> listGender = new ArrayList<String>();
        listGender.add("Nam");
        listGender.add("Nữ");
        listGender.add("Khác");
        ArrayAdapter<String> spinnerGenderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listGender);
        spinnerGenderAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spnGender.setAdapter(spinnerGenderAdapter);

        //Set data for spinner role
        getAllDataOfRoleTable();//get list role
        //Set data for spinner department
        getAllDataOfDepartmentTable();


        //Display information of current user login
        txtFullName.setText(User_Provider.user.getFullName());
        edtAddress.setText(User_Provider.user.getAddress());
        edtNumberPhone.setText(User_Provider.user.getNumberPhone());
        edtEmail.setText(User_Provider.user.getEmail());
        txtAccount.setText(firebaseAuth.getCurrentUser().getEmail());
        txtLastAccess.setText(User_Provider.user.getLastAccess());

        //Proccessing event for back
        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Proccessing event for button change password
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getAllDataOfRoleTable() {
        Query query = databaseReference.child("roles");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> listRoleName = new ArrayList<String>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Role roles = item.getValue(Role.class);
                        listRole.add(roles);//them role vao danh sach
                        listRoleName.add(roles.getRoleName());
                    }
                    ArrayAdapter<String> spinnerRoleAdapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_spinner_item, listRoleName);
                    spinnerRoleAdapter.setDropDownViewResource
                            (android.R.layout.simple_list_item_single_choice);
                    spnRole.setAdapter(spinnerRoleAdapter);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không lấy được dữ liệu vị trí làm việc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllDataOfDepartmentTable() {
        Query query = databaseReference.child("departments");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> listDepartmentName = new ArrayList<String>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Department department = item.getValue(Department.class);
                        listDepartment.add(department);//them role vao danh sach
                        //Toast.makeText(EditProfileActivity.this, "Size: " + listRole.size(), Toast.LENGTH_SHORT).show();
                        listDepartmentName.add(department.getDepartmentName());
                    }
                    ArrayAdapter<String> spinnerDepartmentAdapter = new ArrayAdapter<String>(EditProfileActivity.this,
                            android.R.layout.simple_spinner_item, listDepartmentName);
                    spinnerDepartmentAdapter.setDropDownViewResource
                            (android.R.layout.simple_list_item_single_choice);
                    spnDepartment.setAdapter(spinnerDepartmentAdapter);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không lấy được dữ liệu vị trí làm việc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
