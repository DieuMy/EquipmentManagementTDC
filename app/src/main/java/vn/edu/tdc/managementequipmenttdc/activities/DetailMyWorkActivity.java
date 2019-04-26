package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;
import vn.edu.tdc.managementequipmenttdc.data_models.Users;

public class DetailMyWorkActivity extends AppCompatActivity {
    public static RepairDiary REPAIRDIARY = null;
    private LinearLayout detailReportLinnearLayoutLoiDaKhacPhuc, linearLayoutContainProgressBar;
    private FloatingActionButton floatingActionButtonTiepNhan;
    private ProgressBar progressBarLoading;

    private TextView txtDateReport, txtRoomID, txtRoomName, txtEquipmentID, txtEmployeeID, txtEmployeeName, txtMalfunctionContent;
    private EditText edtRealError;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private boolean statusReceive = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_mywork_screen_flagment);

        getSupportActionBar().setTitle("Chi tiết");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Gets view from layout
        detailReportLinnearLayoutLoiDaKhacPhuc = findViewById(R.id.detailReportLinnearLayoutLoiDaKhacPhuc);
        linearLayoutContainProgressBar = findViewById(R.id.myWWorkLinearlayoutContainProgressBar);
        floatingActionButtonTiepNhan = findViewById(R.id.myWWorkFloatButtonViewListMalfunction);
        txtDateReport = findViewById(R.id.detailMyWorkscreen_timebaocao);
        txtRoomID = findViewById(R.id.ddetailMyWork_maphong);
        txtRoomName = findViewById(R.id.ddetailMyWork_tenphong);
        txtEmployeeID = findViewById(R.id.detailMyWork_manhanvien);
        txtEmployeeName = findViewById(R.id.detailMyWork_tennhanvien);
        txtEquipmentID = findViewById(R.id.detailMyWork_mathietbi);
        txtMalfunctionContent = findViewById(R.id.detailMyWork_noidung);
        edtRealError = findViewById(R.id.detailMyWork_loikhacphuc);
        progressBarLoading = findViewById(R.id.detailMyWorkProgressBar);

        //Initial
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        displayDetailRepairDiary();

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
        linearLayoutContainProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //Gan layout menu vua tao(menu_layout) vao menu cha
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //Gan layout menu vua tao vao menu
        getMenuInflater().inflate(R.menu.menu_complete, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        if (REPAIRDIARY.isStatusReceive() == false) {
            MenuItem item = menu.findItem(R.id.menu_item_complete);
            item.setVisible(false);

            floatingActionButtonTiepNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Bat nut hoan tat
                    detailReportLinnearLayoutLoiDaKhacPhuc.setVisibility(View.VISIBLE);
                    MenuItem item = menu.findItem(R.id.menu_item_complete);
                    item.setVisible(true);
                    floatingActionButtonTiepNhan.setVisibility(View.GONE);

                    //Thay doi trang thai cua su co thanh da tiep nhan StatusReceive = true
                    updateIsStatusReceiveOfMalfunction();

                }
            });

        } else {
            floatingActionButtonTiepNhan.setVisibility(View.GONE);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_complete: //Xu ly item xoa
                updateIsProcessingStatus();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayDetailRepairDiary(){
        txtDateReport.setText(REPAIRDIARY.getDateReport());
        txtRoomID.setText(REPAIRDIARY.getRoomID());

        progressBarLoading.setVisibility(View.VISIBLE);
        linearLayoutContainProgressBar.setVisibility(View.VISIBLE);
        //Get room name
        Query query = databaseReference.child("rooms").child(REPAIRDIARY.getRoomID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Rooms room = dataSnapshot.getValue(Rooms.class);
                    txtRoomName.setText(room.getRoomName());
                } else{
                    txtRoomName.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtEquipmentID.setText(REPAIRDIARY.getEquipmentID());
        txtEmployeeID.setText(REPAIRDIARY.getUserIDReport());

        //Get user name report
        Query _query = databaseReference.child("users").orderByChild("userID").equalTo(REPAIRDIARY.getUserIDReport());
        _query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot item : dataSnapshot.getChildren()) {
                        Users user = item.getValue(Users.class);
                        txtEmployeeName.setText(user.getFullName());
                        break;
                    }
                    progressBarLoading.setVisibility(View.GONE);
                    linearLayoutContainProgressBar.setVisibility(View.GONE);
                } else{
                    txtEmployeeName.setText("");
                }
                progressBarLoading.setVisibility(View.GONE);
                linearLayoutContainProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtMalfunctionContent.setText(REPAIRDIARY.getIncident_content());
    }

    private void updateIsStatusReceiveOfMalfunction(){
        Query query = databaseReference.child("repairDiarys").child(REPAIRDIARY.getRepairDiaryID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    databaseReference.child("repairDiarys").child(REPAIRDIARY.getRepairDiaryID()).child("statusReceive").setValue(true);
                } else{
                    Toast.makeText(DetailMyWorkActivity.this, "Sự cố đã được hủy bởi phòng kỹ thuật", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateIsProcessingStatus(){
        Query query = databaseReference.child("repairDiarys").child(REPAIRDIARY.getRepairDiaryID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    databaseReference.child("repairDiarys").child(REPAIRDIARY.getRepairDiaryID()).child("processingStatus").setValue(true);
                    databaseReference.child("repairDiarys").child(REPAIRDIARY.getRepairDiaryID()).child("roomID_processingStatus").setValue(REPAIRDIARY.getRoomID()+"&true");
                } else{
                    Toast.makeText(DetailMyWorkActivity.this, "Sự cố đã được hủy bởi phòng kỹ thuật", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
