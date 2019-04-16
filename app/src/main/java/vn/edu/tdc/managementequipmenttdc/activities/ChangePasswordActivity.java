package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView imgToolBarBack;
    private Button btnCancel;
    private Button btnSave;
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtCofirmPassword;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String currentPassword = "", newPassword = "", cofirmPassword = "";

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
                currentPassword = edtCurrentPassword.getText().toString();
                newPassword = edtNewPassword.getText().toString();
                cofirmPassword = edtCofirmPassword.getText().toString();

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
                        changePassword();
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

    private void changePassword() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        String account = user.getEmail();
        AuthCredential authCredential = EmailAuthProvider.getCredential(account, currentPassword);
        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                View view = findViewById(R.id.changePass_layout);
                                final Snackbar snackbar_fail = Snackbar.make(view, "Mật khẩu mới quá yếu. Vui lòng thử lại từ 6 ký tự trở lên!", Snackbar.LENGTH_LONG);
                                snackbar_fail.show();
                                snackbar_fail.setAction("Ẩn", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        snackbar_fail.dismiss();
                                    }
                                });
                            } else {
                                View view = findViewById(R.id.changePass_layout);
                                final Snackbar snackbar_success = Snackbar.make(view, "Thay đổi mật khẩu thành công! Vui lòng đăng nhập lại", Snackbar.LENGTH_LONG);
                                snackbar_success.show();
                                firebaseAuth.signOut();
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            }
                        }
                    });
                } else {
                    View view = findViewById(R.id.changePass_layout);
                    final Snackbar snackbar = Snackbar.make(view, "Mật khẩu hiện tại không chính xác!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    snackbar.setAction("Ẩn", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                }
            }
        });
    }
}
