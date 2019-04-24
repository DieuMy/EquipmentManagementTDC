package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ListMalfunctionOneEquipmentActivity extends AppCompatActivity {
    private Vector<ListMalfunctionEquipmentModels> list_displayListMalfunctionCardViewModels;
    RecyclerView recycleViewListMalfunction;

    private TextView txtNotification;
    private LinearLayout linearLayoutNotification;
    private Button btnOK;
    Intent intent;
    private String equipmentID = "", equipmentName = "";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malfunction_equipment_layout);

        //Gets view from layout
        recycleViewListMalfunction = findViewById(R.id.listMalfunctionRecycleView);
        btnOK = findViewById(R.id.listMalfuntionBtnOK);
        txtNotification = findViewById(R.id.malfunctionTxtNotifyCation);
        linearLayoutNotification = findViewById(R.id.malfunctionLinearLayoutNotifyCation);

        //Initial
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        list_displayListMalfunctionCardViewModels = new Vector<ListMalfunctionEquipmentModels>();

        //Nhan du lieu
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            equipmentID = bundle.getString("equipmentIDMal");
            equipmentName = bundle.getString("equipmentNameMal");
        }
        //Toast.makeText(this, "IDMal: " + equipmentID, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Danh sách sự cố đang xử lý " + equipmentName);

        getAllDataMalfunctionOfEquipment(equipmentID);

        //Proccessing event for button OK
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ListMalfunctionOneEquipmentActivity.this, ReportMalfunctionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Gan layout menu vua tao(menu_layout) vao menu cha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Gan layout menu vua tao vao menu
        getMenuInflater().inflate(R.menu.menu_close, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_close: //Xu ly item xoa
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getAllDataMalfunctionOfEquipment(String equipID) {
        Query query = databaseReference.child("repairDiarys").orderByChild("equipmentID_processingStatus").equalTo(equipID + "&false");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        RepairDiary repairDiary = item.getValue(RepairDiary.class);
                        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels(repairDiary.getIncident_content(), repairDiary.getDateReport()));
                    }

                    displayListMalfunctionOfEquipment();
                } else {
                    recycleViewListMalfunction.setVisibility(View.GONE);
                    linearLayoutNotification.setVisibility(View.VISIBLE);
                    txtNotification.setText("Thiết bị hiện tại không có sự cố nào");
                    Toast.makeText(ListMalfunctionOneEquipmentActivity.this, "Thiết bị hiện tại không có sự cố nào", Toast.LENGTH_SHORT).show();
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
