package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
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

    private TextView txtScreenName, txtNotification;
    private LinearLayout linearLayoutNotification;
    private ImageView imgToolBar;
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
        txtScreenName = findViewById(R.id.listMalfunctionTxtScreenName);
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
        txtScreenName.setText("Danh sách sự cố đang xử lý " + equipmentName);
        imgToolBar = findViewById(R.id.listMalfunctionToolBarBack);

        //Proccessing event for button OK
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ListMalfunctionOneEquipmentActivity.this, ReportMalfunctionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Proccessing event tool bar back
        imgToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllDataMalfunctionOfEquipment(equipmentID);
    }

    public void getAllDataMalfunctionOfEquipment(String equipID) {
        Query query = databaseReference.child("repairDiarys").orderByChild("equipmentID").equalTo(equipID);
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
