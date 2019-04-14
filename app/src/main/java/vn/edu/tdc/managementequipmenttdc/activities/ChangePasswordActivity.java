package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ChangePasswordActivity  extends AppCompatActivity {
    private ImageView imgToolBarBack;
    private Button btnCancel;
    private Button btnSave;
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtCofirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pasword_flagment);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String value = bundle.getString("test");
            Toast.makeText(ChangePasswordActivity.this,value, Toast.LENGTH_SHORT).show();
        }

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
    }
}
