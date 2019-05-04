package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;

public class DetailMalfunctionActivity extends AppCompatActivity {
    public static RepairDiary REPAIR_DIARY;

    private TextView txtDateReport, txtRoomID, txtEquipmentID, txtUserIDReport,
            txtUserNameReport, txtUserIDReceiver, txtUserNameReceiver,
            txtMalfunctionContent, txtDateRepair, txtLoiDaKhacPhuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_report_screen_flagment);

        getSupportActionBar().setTitle("Chi tiết");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Gets view from layout
        txtDateReport = findViewById(R.id.detailreportscreen_timebaocao);
        txtRoomID = findViewById(R.id.detailreportscreen_maphong);
        txtEquipmentID = findViewById(R.id.detailreportscreen_mathietbi);
        txtUserIDReport = findViewById(R.id.detailreportscreen_manhanvien);
        txtUserNameReport = findViewById(R.id.detailreportscreen_tennhanvien); ;
        txtUserIDReceiver = findViewById(R.id.detailreportscreen_employeeIDRepair);
        txtUserNameReceiver = findViewById(R.id.detailreportscreen_employeeNameRepair);
        txtMalfunctionContent = findViewById(R.id.detailreportscreen_noidung);
        txtDateRepair = findViewById(R.id.detailreportscreen_timesuachua);
        txtLoiDaKhacPhuc = findViewById(R.id.detailreportscreen_loikhacphuc);

        displayDetailHistory();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

//    //Gan layout menu vua tao(menu_layout) vao menu cha
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //Gan layout menu vua tao vao menu
//        getMenuInflater().inflate(R.menu.menu_review, menu);//Hien thi ra man hinh co menu tren thanh cong cu
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
//        //Kiem tra xem da click vao item nào
//        switch (indexItem) {
//            case R.id.menu_item_review: //Xu ly item xoa
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void displayDetailHistory() {
        if(REPAIR_DIARY != null) {
            txtDateReport.setText(REPAIR_DIARY.getDateReport());
            txtRoomID.setText(REPAIR_DIARY.getRoomID());
            txtEquipmentID.setText(REPAIR_DIARY.getEquipmentID());
            txtUserIDReport.setText(REPAIR_DIARY.getUserIDReport());
            txtUserIDReceiver.setText(REPAIR_DIARY.getUserIDReceive());
            txtMalfunctionContent.setText(REPAIR_DIARY.getIncident_content());
            txtDateRepair.setText(REPAIR_DIARY.getDateComplete());
            txtLoiDaKhacPhuc.setText(REPAIR_DIARY.getMaintenanceContent());
        }
    }
}
