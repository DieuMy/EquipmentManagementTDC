package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class LoginActivity extends AppCompatActivity {
    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    private EditText edtAccount;
    private EditText edtPasword;
    private Button btnLogin;
    private Button btnCancel;
    private Button btnHelp;
    private Button btnExit;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_flagment);

        //Get views from layout
        edtAccount = (EditText) findViewById(R.id.loginScreenEdtAccount);
        edtPasword = (EditText) findViewById(R.id.loginScreenEdtPassword);
        btnCancel = (Button) findViewById(R.id.loginScreenBtnForgetPassword);
        btnHelp = (Button) findViewById(R.id.loginScreenBtnHelp);
        btnExit = (Button) findViewById(R.id.loginScreenBtnExit);
        progressBarLoading = (ProgressBar) findViewById((R.id.loginScreenProgressBar));

        btnLogin = (Button) findViewById(R.id.loginScreenBtnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    //Kiem tra dang nhap
    private void checkLogin(){
        String username = edtAccount.getText().toString().trim();
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

        if(username.equals("huongnguyen") && password.equals("123456")) {
            progressBarLoading.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            User_Provider.username = username;
            startActivity(intent);
        } else{
            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không trùng khớp!", Toast.LENGTH_LONG).show();
        }
    }
}
