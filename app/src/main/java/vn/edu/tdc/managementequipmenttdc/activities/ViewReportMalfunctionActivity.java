package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class ViewReportMalfunctionActivity extends AppCompatActivity {
    public static String EQUIPMENTID = "";
    public static String EQUIPMENTNAME = "";
    public static String contentMalfunction = "";
    String repairDiaryID = "";

    private TextView txtRoomID, txtEquipmentID, txtUserID, txtEmployeeName, txtMalfunctionContent, txtDateReport;
    Intent intent;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ToolUtils toolUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_malfuntion_screen_flagment);

        getSupportActionBar().setTitle("Xem phiếu báo cáo sự cố");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initial
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        toolUtils = new ToolUtils();

        //Gets view from layout
        txtRoomID = findViewById(R.id.viewreportmalfunctionTextviewRoomID);
        txtEquipmentID = findViewById(R.id.viewreportmalfunctiontextview_mathietbi);
        txtUserID = findViewById(R.id.viewreportmalfunctiontextview_manhanvien);
        txtEmployeeName = findViewById(R.id.viewreportmalfunctiontextview_tennhanvien);
        txtMalfunctionContent = findViewById(R.id.viewreportmalfunctiontextview_noidungsuco);
        txtDateReport = findViewById(R.id.viewreportmalfunctiontextview_thoigian);

        viewReportMalfunction();

        getAllDataRepairDiary();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //Gan layout menu vua tao(menu_layout) vao menu cha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Gan layout menu vua tao vao menu
        getMenuInflater().inflate(R.menu.menu_send, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_send: //Xu ly item xoa
                sendDataAndSaveDataOnDatabase();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllDataRepairDiary(){
        Query query = databaseReference.child("repairDiarys");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot item : dataSnapshot.getChildren()){
                        RepairDiary repairDiary = item.getValue(RepairDiary.class);
                        repairDiaryID = String.valueOf(Integer.parseInt(repairDiary.getRepairDiaryID()) + 1);
                    }
                    //Toast.makeText(ViewReportMalfunctionActivity.this, "Hello " + repairDiaryID, Toast.LENGTH_SHORT).show();
                } else {
                    repairDiaryID = "1";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void viewReportMalfunction() {
        txtUserID.setText(User_Provider.user.getUserID());
        txtEmployeeName.setText(User_Provider.user.getFullName());
        txtEquipmentID.setText(EQUIPMENTID);
        txtRoomID.setText(Room_Provider.room.getRoomID());
        txtDateReport.setText(toolUtils.getCurrentTimeString());
        txtMalfunctionContent.setText(contentMalfunction);
    }

    private void sendDataAndSaveDataOnDatabase() {
        String equipmentID = EQUIPMENTID;
        String roomID = Room_Provider.room.getRoomID();
        String userIDReport = User_Provider.user.getUserID();//Nguoi bao cao
        String incident_content = contentMalfunction;//Noi dung su co
        String dateReport = toolUtils.getCurrentTimeString();//Ngay bao cao
        boolean statusReceive = false; //Nhan vien tiep nhan xu y su co chu
        boolean processingStatus = false;//Tinh trang su co duoc xu ly hay chua
        String create_at = toolUtils.getCurrentTimeString();
        String update_at = toolUtils.getCurrentTimeString();

        RepairDiary repairDiary = new RepairDiary(repairDiaryID, equipmentID, roomID, userIDReport,
                incident_content, dateReport, "", statusReceive, "", "",
                processingStatus, "", "", 0, create_at, update_at);

        databaseReference.child("repairDiarys").child(repairDiaryID).setValue(repairDiary);
        databaseReference.child("repairDiarys").child(repairDiaryID).child("equipmentID_processingStatus").setValue(equipmentID + "&" + processingStatus);
        databaseReference.child("repairDiarys").child(repairDiaryID).child("roomID_processingStatus").setValue(roomID + "&" + processingStatus);
        finish();
    }
}
