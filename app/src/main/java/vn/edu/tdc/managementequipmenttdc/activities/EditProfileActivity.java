package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class EditProfileActivity extends AppCompatActivity {
    private Intent intent;
    private Button btnChangePassword;
    private ImageView imgToolBarBack;
    private ImageView imgToolBarSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_flagment);

        //Gets view from layout
        btnChangePassword = (Button) findViewById(R.id.editProfileBtnChangePassword);
        imgToolBarBack = findViewById(R.id.editProfileToolBarBack);
        imgToolBarSave = findViewById(R.id.editProfileToolBarSave);

        //Proccessing event for back
        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



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
