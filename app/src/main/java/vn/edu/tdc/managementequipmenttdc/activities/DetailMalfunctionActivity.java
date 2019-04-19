package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class DetailMalfunctionActivity  extends AppCompatActivity {
    private ImageView imgToolBarBack;
    private TextView txtToolBarRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_report_screen_flagment);

        //Gets view from layout
        imgToolBarBack = findViewById(R.id.detailReportToolBarBack);
        txtToolBarRight = findViewById(R.id.detailReportToolBarRight);

        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
