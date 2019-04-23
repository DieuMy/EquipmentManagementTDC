package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.Department;
import vn.edu.tdc.managementequipmenttdc.data_models.Role;
import vn.edu.tdc.managementequipmenttdc.data_models.Users;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class EditProfileActivity extends AppCompatActivity {
    private Intent intent;
    private Button btnChangePassword;
    private ImageView imgToolBarBack;
    private ImageView imgToolBarSave;
    private ImageView imgAvatar;
    private EditText edtFullName;
    private TextView txtAccount, txtUserID, labelUserID;
    private Spinner spnGender;
    private EditText edtAddress;
    private EditText edtNumberPhone;
    private EditText edtEmail;
    private Spinner spnRole;
    private Spinner spnDepartment;
    private TextView txtLastAccess, txtDisplayGender, txtDisplayRole, txtDisplayDepartment;

    private ArrayList<Role> listRole = new ArrayList<Role>();
    private ArrayList<Department> listDepartment = new ArrayList<Department>();

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ToolUtils toolUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_flagment);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        toolUtils = new ToolUtils();

        //Gets view from layout
        btnChangePassword = (Button) findViewById(R.id.editProfileBtnChangePassword);
        imgToolBarBack = findViewById(R.id.editProfileToolBarBack);
        imgToolBarSave = findViewById(R.id.editProfileToolBarSave);
        edtFullName = findViewById(R.id.editProfileTxtFullName);
        txtAccount = findViewById(R.id.editProfileTxtAccountName);
        spnGender = findViewById(R.id.editProfileSpnGender);
        spnRole = findViewById(R.id.editProfileSpnRole);
        spnDepartment = findViewById(R.id.editProfileSpnDepartment);
        edtAddress = findViewById(R.id.editProfileEdtAddress);
        edtNumberPhone = findViewById(R.id.editProfileEdtNumberPhone);
        edtEmail = findViewById(R.id.editProfileEdtEmail);
        txtLastAccess = findViewById(R.id.editProfileTxtLastAccess);
        txtDisplayGender = findViewById(R.id.editProfileTxtGender);
        txtDisplayRole = findViewById(R.id.editProfileTxtRole);
        txtDisplayDepartment = findViewById(R.id.editProfileTxtDepartment);
        txtUserID = findViewById(R.id.EditProfileTxtUserID);
        labelUserID = findViewById(R.id.editProfileTxtLabelUserID);

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

        getInformationOfUserCurrentLogin();

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

        txtDisplayGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDisplayGender.setVisibility(View.GONE);
                spnGender.setVisibility(View.VISIBLE);
            }
        });

        txtDisplayRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDisplayRole.setText("");
                txtDisplayRole.setVisibility(View.GONE);
                spnRole.setVisibility(View.VISIBLE);
            }
        });

        txtDisplayDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDisplayDepartment.setText("");
                txtDisplayDepartment.setVisibility(View.GONE);
                spnDepartment.setVisibility(View.VISIBLE);
            }
        });

        imgToolBarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewProfile();
            }
        });
    }

    private void displayInformationCurrentUserOnActivity() {
        //Display information of current user login
        txtUserID.setText(User_Provider.user.getUserID());
        edtFullName.setText(User_Provider.user.getFullName());
        edtAddress.setText(User_Provider.user.getAddress());
        edtNumberPhone.setText(User_Provider.user.getNumberPhone());
        edtEmail.setText(User_Provider.user.getEmail());
        txtAccount.setText(firebaseAuth.getCurrentUser().getEmail());
        txtLastAccess.setText(User_Provider.user.getLastAccess());
    }

    public void getInformationOfUserCurrentLogin() {
        Query query = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    edtFullName.setText(user.getFullName());
                    edtAddress.setText(user.getAddress());
                    edtNumberPhone.setText(user.getNumberPhone());
                    edtEmail.setText(user.getEmail());
                    txtAccount.setText(firebaseAuth.getCurrentUser().getEmail());
                    txtLastAccess.setText(user.getLastAccess());
                    txtDisplayGender.setText(user.getGender());
                    txtUserID.setText(user.getUserID());
                    getRoleOfUserCurrentLogin(user.getRoleID());
                    getDepartmentOfUserCurrentLogin(user.getDepartmentID());
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getRoleOfUserCurrentLogin(final String roleID) {
        if (roleID == null) {
            return;
        }
        Query query = databaseReference.child("roles").child(roleID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Role roles = dataSnapshot.getValue(Role.class);
                    String roleName = roles.getRoleName();
                    txtDisplayRole.setText(roleName);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không lấy được dữ liệu vị trí làm việc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDepartmentOfUserCurrentLogin(final String departmentID) {
        if (departmentID == null) {
            return;
        }
        Query query = databaseReference.child("departments").child(departmentID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Department department = dataSnapshot.getValue(Department.class);
                    String departmentName = department.getDepartmentName();
                    txtDisplayDepartment.setText(departmentName);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không lấy được dữ liệu vị trí làm việc", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Lay toan bo du lieu cua bang vi tri lam viec de gan vao spinner
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

    private void saveNewProfile() {
        String userID = txtUserID.getText().toString();
        String fullName = edtFullName.getText().toString().trim();
        String gender = spnGender.getSelectedItem().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String numberPhone = edtNumberPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String update_at = toolUtils.getCurrentTimeString();
        String lastAccess = User_Provider.user.getLastAccess();

        if (fullName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numberPhone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại liên lạc", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email liên hệ", Toast.LENGTH_SHORT).show();
            return;
        }

        //Lay du lieu role_id va department_id
        // Toast.makeText(this, "size: " + listRole.size(), Toast.LENGTH_SHORT).show();

        String roleID = "";
        for (int i = 0; i < listRole.size(); i++) {
            if (txtDisplayRole.getText() == "") {
                if (spnRole.getSelectedItem().equals(listRole.get(i).getRoleName())) {
                    roleID = listRole.get(i).getRoleID();
                    break;
                }
            } else {
                if (txtDisplayRole.getText().equals(listRole.get(i).getRoleName())) {
                    roleID = listRole.get(i).getRoleID();
                    break;
                }
            }
        }

        String departmentID = "";
        for (Department department : listDepartment) {
            if (txtDisplayDepartment.getText() == "") {
                if (spnDepartment.getSelectedItem().equals(department.getDepartmentName())) {
                    departmentID = department.getDepartmentID();
                    break;
                }
            } else {
                if (txtDisplayDepartment.getText().equals(department.getDepartmentName())) {
                    departmentID = department.getDepartmentID();
                    break;
                }
            }
        }

        final Users users = new Users(userID, fullName, gender, address, numberPhone, email, roleID, departmentID, "",
                User_Provider.user.isActive(), User_Provider.user.isLock_account(), User_Provider.user.getCreate_at(),
                update_at, User_Provider.user.getLast_changePassword(), lastAccess);

        final AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn chắc chắn muốn thay đổi thông tin ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query query = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(users);
                            Toast.makeText(EditProfileActivity.this, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                            getInformationOfUserCurrentLogin();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Không lấy được dữ liệu vị trí làm việc", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                displayInformationCurrentUserOnActivity();
            }
        });

        builder.show();

    }
}
