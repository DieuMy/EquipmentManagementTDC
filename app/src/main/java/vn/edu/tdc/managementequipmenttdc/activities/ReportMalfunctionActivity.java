package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ReportMalfunctionActivity extends AppCompatActivity {

    private Button btnOK;
    private ImageView imgToolBar;
    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_malfunction_screen_flagment);

        btnOK = findViewById(R.id.ReportMalfuntionBtnOK);
        imgToolBar = findViewById(R.id.reportmalFunctionToolBarBack);

        //Processing event button OK
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ReportMalfunctionActivity.this, ViewReportMalfunctionActivity.class);
                startActivity(intent);
            }
        });

        //Proccessing event tool bar back
        imgToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}