package vn.edu.tdc.managementequipmenttdc.activities;

import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.Log;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;

import android.os.Bundle;

public class DetailHistoryActivity extends AppCompatActivity {
    public static Log LOGOFUSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_history_layout);

        getSupportActionBar().setTitle("Chi tiết");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
