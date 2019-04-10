package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
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
    private Button btnForgetPassword;
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
        btnForgetPassword = (Button) findViewById(R.id.loginScreenBtnForgetPassword);
        btnHelp = (Button) findViewById(R.id.loginScreenBtnHelp);
        btnExit = (Button) findViewById(R.id.loginScreenBtnExit);
        btnLogin = (Button) findViewById(R.id.loginScreenBtnLogin);
        progressBarLoading = (ProgressBar) findViewById((R.id.loginScreenProgressBar));

        firebaseAuth = FirebaseAuth.getInstance();

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

    private void processingForgetPassword(){

    }

    private void helpUser(){

    }

    //Kiem tra dang nhap
    private void checkLogin(){
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
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
//                    Toast.makeText(LoginActivity.this, firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    User_Provider.username = username;
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
