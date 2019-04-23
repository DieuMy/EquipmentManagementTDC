package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ViewReportMalfunctionActivity extends AppCompatActivity {

    private ImageView imgSend;
    private ImageView imgToolBack;
    private TextView txtMalfunctionID, txtRoomID, txtEquipmentID, txtUserID, txtEmployeeName, txtMalfunctionContent, txtDateReport;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_malfuntion_screen_flagment);

        //Gets view from layout
        imgSend = findViewById(R.id.viewReportMalfunctionToolBarSend);
        imgToolBack = findViewById(R.id.viewReportMalfunctionToolBarBack);
        txtMalfunctionID = findViewById(R.id.viewreportmalfunctiontextview_masuco);
        txtRoomID = findViewById(R.id.viewreportmalfunctionTextviewRoomID);
        txtEquipmentID = findViewById(R.id.viewreportmalfunctiontextview_mathietbi);
        txtUserID = findViewById(R.id.viewreportmalfunctiontextview_manhanvien);
        txtEmployeeName = findViewById(R.id.viewreportmalfunctiontextview_tennhanvien);
        txtMalfunctionContent = findViewById(R.id.viewreportmalfunctiontextview_noidungsuco);
        txtDateReport = findViewById(R.id.viewreportmalfunctiontextview_thoigian);


        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gui di thong bao
                //Tro ve man hinh home
                intent = new Intent(ViewReportMalfunctionActivity.this, TypeEquipmentActivity.class);
                startActivity(intent);
                finish();

            }
        });

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void viewReportMalfunction(){

    }
}
