package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class PersonalPageActivity extends AppCompatActivity {
    private Intent intent;
    private TextView btnSettingProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_page_screen_flagment);

        btnSettingProfile = (TextView) findViewById(R.id.personalScreenTxtSettingProfile);

        btnSettingProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingProfile();
            }
        });
    }

    //Setting profile
    private void settingProfile() {
        intent = new Intent(PersonalPageActivity.this, EditProfileActivity.class);
        startActivity(intent);
    }
}
