package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ViewReportMalfunctionActivity extends AppCompatActivity {

    private ImageView imgSend;
    private ImageView imgToolBack;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_malfuntion_screen_flagment);

        imgSend = findViewById(R.id.viewReportMalfunctionToolBarSend);
        imgToolBack = findViewById(R.id.viewReportMalfunctionToolBarBack);

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
}
