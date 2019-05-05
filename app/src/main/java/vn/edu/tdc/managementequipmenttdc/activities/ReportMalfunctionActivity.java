package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class ReportMalfunctionActivity extends AppCompatActivity {
    private final String TAG = "ReportMalfunction";
    private Button btnOK;
    private CheckBox chkAll, chkHuChuot, chkHuBanPhim, chkMatNguon, chkManHinh, chkThieuPhanMem;
    private EditText edtOther;
    private String reportContent = "";

    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_malfunction_screen_flagment);

        getSupportActionBar().setTitle("Phiếu báo cáo sự cố");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Gets view from layout
        btnOK = findViewById(R.id.ReportMalfuntionBtnOK);
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
//            Kiem tra xem co it nhat 1 check box duoc check hoac edittext phai co noi dung neu khong check checkbox
                checkCheckBoxAllIsCheck();
                if (reportContent.isEmpty()) {
                    Toast.makeText(ReportMalfunctionActivity.this, "Bạn phải chọn hoặc nhập nội dung sự cố trước khi gửi", Toast.LENGTH_LONG).show();
                    return;
                }
                intent = new Intent(ReportMalfunctionActivity.this, ViewReportMalfunctionActivity.class);
                ViewReportMalfunctionActivity.contentMalfunction = reportContent;
                startActivity(intent);
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
                } else {
                    chkHuBanPhim.setChecked(false);
                    chkHuChuot.setChecked(false);
                    chkMatNguon.setChecked(false);
                    chkManHinh.setChecked(false);
                    chkThieuPhanMem.setChecked(false);
                }
            }
        });

        chkThieuPhanMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chkThieuPhanMem.isChecked()){
                    chkAll.setChecked(false);
                }
            }
        });

        chkManHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chkManHinh.isChecked()){
                    chkAll.setChecked(false);
                }
            }
        });

        chkMatNguon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chkMatNguon.isChecked()){
                    chkAll.setChecked(false);
                }
            }
        });

        chkHuChuot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chkHuChuot.isChecked()){
                    chkAll.setChecked(false);
                }
            }
        });

        chkHuBanPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!chkHuBanPhim.isChecked()){
                    chkAll.setChecked(false);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        reportContent = "";
    }

    private void checkCheckBoxAllIsCheck() {
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

