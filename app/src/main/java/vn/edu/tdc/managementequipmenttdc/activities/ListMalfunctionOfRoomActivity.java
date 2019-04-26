package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListMalfunctionEquipmentAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.ListMalfunctionEquipmentModels;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;

public class ListMalfunctionOfRoomActivity extends AppCompatActivity {
    //public static String ROOMID = "";
    private Vector<ListMalfunctionEquipmentModels> list_displayListMalfunctionCardViewModels;
    RecyclerView recycleViewListMalfunction;

    private TextView txtNotification;
    private LinearLayout linearLayoutNotification;
    private Button btnReport;
    Intent intent;

    private String equipmentID = "", equipmentName = "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malfunction_equipment_layout);

        getSupportActionBar().setTitle("Danh sách sự cố của phòng " + Room_Provider.room.getRoomID());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Gets view from layout
        recycleViewListMalfunction = findViewById(R.id.listMalfunctionRecycleView);
        btnReport = findViewById(R.id.listMalfuntionBtnOK);
        txtNotification = findViewById(R.id.malfunctionTxtNotifyCation);
        linearLayoutNotification = findViewById(R.id.malfunctionLinearLayoutNotifyCation);

        //Initial
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        list_displayListMalfunctionCardViewModels = new Vector<ListMalfunctionEquipmentModels>();
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
        getMenuInflater().inflate(R.menu.menu_search, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_search: //Xu ly item xoa

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnReport.setVisibility(View.GONE);
        getAllDataMalfunctionOfRoomWithProcessingStatusIsFalse(Room_Provider.room.getRoomID());
    }

    public void getAllDataMalfunctionOfRoomWithProcessingStatusIsFalse(String roomID) {
        //  Lay danh sach su co cua phong thuc hành roomID = "a" and processingStatus = true
        Query query = databaseReference.child("repairDiarys").orderByChild("roomID_processingStatus").equalTo(roomID+"&false");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        RepairDiary repairDiary = item.getValue(RepairDiary.class);
                        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Máy " +
                                repairDiary.getEquipmentID() + "\n" + repairDiary.getIncident_content(), repairDiary.getDateReport()));
                    }

                    displayListMalfunctionOfEquipment();
                } else {
                    recycleViewListMalfunction.setVisibility(View.GONE);
                    linearLayoutNotification.setVisibility(View.VISIBLE);
                    txtNotification.setText("Phòng thực hành này hiện tại không có thiết bị nào bị sự cố");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi lich su
    private void displayListMalfunctionOfEquipment() {
        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleViewListMalfunction.setLayoutManager(layoutManager);

        ListMalfunctionEquipmentAdapter adapter = new ListMalfunctionEquipmentAdapter(R.layout.card_view_popup_malfunction_equipment_layout, list_displayListMalfunctionCardViewModels);
        recycleViewListMalfunction.setAdapter(adapter);
    }
}
