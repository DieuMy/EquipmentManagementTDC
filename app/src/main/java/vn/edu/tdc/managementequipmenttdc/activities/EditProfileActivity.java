package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class EditProfileActivity extends AppCompatActivity {
    private Intent intent;
    private Button btnChangePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_flagment);
        btnChangePassword = (Button) findViewById(R.id.editProfileBtnChangePassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword(){
        intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
