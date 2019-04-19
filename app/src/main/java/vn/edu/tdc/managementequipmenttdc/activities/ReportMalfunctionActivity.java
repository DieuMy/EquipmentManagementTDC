package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ReportMalfunctionActivity extends AppCompatActivity {

    private Button btnOK;
    private ImageView imgToolBar;
    private CheckBox chkAll, chkHuChuot, chkHuBanPhim, chkMatNguon, chkManHinh, chkThieuPhanMem;
    private EditText edtOther;
    private String reportContent = "";

    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_malfunction_screen_flagment);

        //Gets view from layout
        btnOK = findViewById(R.id.ReportMalfuntionBtnOK);
        imgToolBar = findViewById(R.id.reportmalFunctionToolBarBack);
        chkAll = findViewById(R.id.reportmalfunctioncheckboxall);
        chkHuChuot = findViewById(R.id.reportmalfunctioncheckboxmouse);
        chkHuBanPhim = findViewById(R.id.reportmalfunctioncheckboxkeyboard);
        chkMatNguon = findViewById(R.id.reportmalfunctioncheckboxpower);
        chkManHinh = findViewById(R.id.reportmalfunctioncheckboxscreen);
        chkThieuPhanMem = findViewById(R.id.reportmalfunctioncheckboxsoftware);
        edtOther = findViewById(R.id.reportmalfunctionEdtOther);


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

        //Processing for event check all
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkAll.isChecked()) {
                    chkHuBanPhim.setChecked(true);
                    chkHuChuot.setChecked(true);
                    chkMatNguon.setChecked(true);
                    chkManHinh.setChecked(true);
                    chkThieuPhanMem.setChecked(true);
                    reportContent += chkHuBanPhim.getText() + "; " + chkHuChuot.getText() + "; " +
                            chkMatNguon.getText() + "; " + chkManHinh.getText() + "; " +
                            chkThieuPhanMem.getText() + "; " + edtOther.getText();
                    Toast.makeText(ReportMalfunctionActivity.this, reportContent, Toast.LENGTH_SHORT).show();
                } else {
                    chkHuBanPhim.setChecked(false);
                    chkHuChuot.setChecked(false);
                    chkMatNguon.setChecked(false);
                    chkManHinh.setChecked(false);
                    chkThieuPhanMem.setChecked(false);
                    reportContent = "";

                    if (chkHuBanPhim.isChecked()) {
                        reportContent += chkHuBanPhim.getText() + "; ";
                    }
                    if (chkHuChuot.isChecked()) {
                        reportContent += chkHuChuot.getText() + "; ";
                    }
                    if (chkMatNguon.isChecked()) {
                        reportContent += chkMatNguon.getText() + "; ";
                    }
                    if (chkManHinh.isChecked()) {
                        reportContent += chkManHinh.getText() + "; ";
                    }
                    if (chkThieuPhanMem.isChecked()) {
                        reportContent += chkThieuPhanMem.getText() + "; ";
                    }
                    reportContent += edtOther.getText();
                }
            }
        });

    }
}

