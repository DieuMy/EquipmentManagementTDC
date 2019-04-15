package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ChangePasswordActivity  extends AppCompatActivity {
    private ImageView imgToolBarBack;
    private Button btnCancel;
    private Button btnSave;
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtCofirmPassword;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pasword_flagment);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Gets view from layout
        imgToolBarBack = findViewById(R.id.changePasswordToolBarBack);
        btnCancel = findViewById(R.id.changePasswordBtnCancel);
        btnSave = findViewById(R.id.changePasswordBtnSave);
        edtCurrentPassword = findViewById(R.id.changePasswordEdtCurrentPassword);
        edtNewPassword = findViewById(R.id.changePasswordNewPassword);
        edtCofirmPassword = findViewById(R.id.changePasswordCofirmPassword);

        //Proccessing event tool bar back
        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Proccessing event btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = edtCurrentPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                String cofirmPassword = edtCofirmPassword.getText().toString();

                //Kiem tra password hien tai co dung khong


                //Kiem tra pass moi co > 6 ky tu va khong co khoang trang khong


                //Kiem tra pass moi va pass hien tai co giong nhau khong?
                if (newPassword.equals(currentPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng đổi mật khẩu khác mật khẩu hiện tại!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!cofirmPassword.equals(newPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu xác nhận không trùng khớp!", Toast.LENGTH_LONG).show();
                    return;
                }

                //Cofirm change password
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn chắc chắn muốn thay đổi mật khẩu ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // changePassword();
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.show();
            }
        });

    }

    private void changePassword(){

    }
}
