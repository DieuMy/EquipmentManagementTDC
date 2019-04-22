package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class DetailMyWorkActivity  extends AppCompatActivity {
    private ImageView imgToolBarBack;
    private Button btnTiepNhan;
    private TextView txtTiepNhan;
    private TextView txtToolBarRight;
    private LinearLayout detailReportLinnearLayoutLoiDaKhacPhuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_mywork_screen_flagment);

        //Gets view from layout
        imgToolBarBack = findViewById(R.id.detailMyWorkToolBarBack);
        txtToolBarRight = findViewById(R.id.detailReportToolBarRight);
        txtTiepNhan = findViewById(R.id.detailMyWorkBtnTiepNhan);
        detailReportLinnearLayoutLoiDaKhacPhuc = findViewById(R.id.detailReportLinnearLayoutLoiDaKhacPhuc);

        checkUI();

        //Proccessing event
        imgToolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtTiepNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bat nut hoan tat
                txtToolBarRight.setVisibility(View.VISIBLE);
                detailReportLinnearLayoutLoiDaKhacPhuc.setVisibility(View.VISIBLE);
                txtTiepNhan.setVisibility(View.GONE);

                //Thay doi trang thai cua loi tren co so du lieu
                finish();
            }
        });
    }

    public void checkUI(){
        //Kiem tra trang thai cua loi(da tiep nhan hay chua?: neu tiep nhan roi thi an button tiep nhan,
        // hien thi edt nhap noi dung loi, hien thi tool bar right xac nhan hoan tat sua chua)
    }
}
