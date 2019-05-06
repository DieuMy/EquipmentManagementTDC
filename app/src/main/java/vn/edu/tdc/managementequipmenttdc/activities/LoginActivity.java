package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuilding;
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.Log;
import vn.edu.tdc.managementequipmenttdc.data_models.Permissions;
import vn.edu.tdc.managementequipmenttdc.data_models.Role_Group;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;
import vn.edu.tdc.managementequipmenttdc.data_models.Users;
import vn.edu.tdc.managementequipmenttdc.tools.ConnectionDetector;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class LoginActivity extends AppCompatActivity {
    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ToolUtils toolUtils;

    private EditText edtAccount;
    private EditText edtPasword;
    private Button btnLogin;
    private Button btnForgetPassword;
    private Button btnHelp;
    private Button btnExit;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_flagment);

        getSupportActionBar().hide();

        //Get views from layout
        edtAccount = (EditText) findViewById(R.id.loginScreenEdtAccount);
        edtPasword = (EditText) findViewById(R.id.loginScreenEdtPassword);
        btnForgetPassword = (Button) findViewById(R.id.loginScreenBtnForgetPassword);
        btnHelp = (Button) findViewById(R.id.loginScreenBtnHelp);
        btnExit = (Button) findViewById(R.id.loginScreenBtnExit);
        btnLogin = (Button) findViewById(R.id.loginScreenBtnLogin);
        progressBarLoading = (ProgressBar) findViewById((R.id.loginScreenProgressBar));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        toolUtils = new ToolUtils();

        //Processing button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        //Proccessing button exit
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                finish();
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpUser();
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processingForgetPassword();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
    }

    private void helpUser() {
        Intent intent = new Intent(LoginActivity.this, HelpUserActivity.class);
        startActivity(intent);
    }

    //Kiem tra dang nhap
    private void checkLogin() {
        final String username = edtAccount.getText().toString().trim();
        final String password = edtPasword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Nhập địa chỉ tài khoản đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Nhập mật khẩu đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBarLoading.setVisibility(View.VISIBLE);

        //check login
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarLoading.setVisibility(View.GONE);
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không trùng khớp!", Toast.LENGTH_LONG).show();
                } else {
                    //Kiem tra tai khoan co bi khoa hay khong
                    Query query = databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Users users = dataSnapshot.getValue(Users.class);
                                if(users.isLock_account() == true) {
                                    Toast.makeText(LoginActivity.this, "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ phòng kỹ thuật để được cấp lại!", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                //Update log
                                updateLogOfUser();

                                Intent intent = new Intent(LoginActivity.this, Main_Activity.class);
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    private void processingForgetPassword() {
        final Dialog dialog = new Dialog(LoginActivity.this);

        dialog.setContentView(R.layout.popup_forget_password_layout);

        Button btnSend = dialog.findViewById(R.id.forgetPassowrdBtnSend);
        Button btnCancel = dialog.findViewById(R.id.forgetPassowrdBtnCancel);
        final EditText edtEmailOrPhone = dialog.findViewById(R.id.forgetPasswordEdtEmail);

        //Processing event for close dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailOrPhone = edtEmailOrPhone.getText().toString().trim();
                if (emailOrPhone.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email đã đăng ký!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    progressBarLoading.setVisibility(View.VISIBLE);

                    firebaseAuth.sendPasswordResetEmail(emailOrPhone).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra email để được cấp lại mật khẩu", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Gửi yêu cầu thất bại! Tài khoản chưa được đăng ký! Vui lòng thử lại sau! Hoặc liên hệ phòng kỹ thuật để cấp lại mật khẩu", Toast.LENGTH_LONG).show();
                            }

                            progressBarLoading.setVisibility(View.GONE);
                        }
                    });
                    dialog.dismiss();
                }
            }
        });

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void updateLogOfUser() {
        String logID = FirebaseDatabase.getInstance().getReference().push().getKey();
        String userID = firebaseAuth.getCurrentUser().getUid();
        String manipulation = "Đăng nhập ứng dụng";
        String dateManipulation = toolUtils.getCurrentTimeString();

        vn.edu.tdc.managementequipmenttdc.data_models.Log log = new Log(logID, userID, manipulation, dateManipulation);
        databaseReference.child("log").child(logID).setValue(log);
    }


//    private void createEquipment(){
//        String equipmentID;
//        String equipmentName;
//        boolean status = true;
//        String datePurchase = "01-01-2019";
//        String parentID="MT";
//        String roomID = "B002C";
//        String create_at = toolUtils.getCurrentTimeString();
//        String update_at = toolUtils.getCurrentTimeString();
//
//        for(int i = 1 ; i <= 9 ; i++){
//            if(i<10){
//                equipmentID = "B002C_MT0"+i;
//                equipmentName = "Máy 0"+i;
//            } else {
//                equipmentID = "B002C_MT" + i;
//                equipmentName = "Máy " + i;
//            }
//
//            Equipment equipment = new Equipment(equipmentID, equipmentName, status, datePurchase, parentID, roomID, create_at, update_at);
//            databaseReference.child("equipments").child(equipment.getEquipmentID()).setValue(equipment);
//        }
//
//    }

//    private void createPremissionForUser() {
////        String roleGroupID = FirebaseDatabase.getInstance().getReference().push().getKey();;
////        String roleID = "NV";
////        String functionID = "BCSC";
////        String permiss = "F";
////        String create_at = toolUtils.getCurrentTimeString();
////        String update_at = toolUtils.getCurrentTimeString();
////
////        Role_Group role_group = new Role_Group(roleGroupID, roleID, functionID, permiss, create_at, update_at);
////        databaseReference.child("role_groups").child(roleGroupID).setValue(role_group);
//
////        String permissionID = FirebaseDatabase.getInstance().getReference().push().getKey();
////        String userID = "SdMyQrIp2mNzLzAZcDhatPHwGmi2";
////        String functionID = "BCSC";
////        String permiss = "V";
////        String create_at = toolUtils.getCurrentTimeString();
////        String update_at = toolUtils.getCurrentTimeString();
////
////        Permissions permissions = new Permissions(userID, functionID, permiss, create_at, update_at);
////        databaseReference.child("permissions").child(permissionID).setValue(permissions);
//    }
}
