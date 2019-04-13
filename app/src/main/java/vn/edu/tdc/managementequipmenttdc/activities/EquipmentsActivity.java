package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Vector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListRoomRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;

public class EquipmentsActivity extends AppCompatActivity {
    public static String ROOMID = "";
    public static String ROOMNAME = "";


    //List Equipment
    private Vector<ListRoomCardViewModel> listEquipmentsModels = new Vector<ListRoomCardViewModel>();
    RecyclerView listequipmentrecycleview;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    ToolUtils toolUtils;
    ArrayList<Equipment> listEquipments = new ArrayList<Equipment>();
    String roomID;

    private TextView txtScreenName;
    private ImageView imgToolBar;
    private String equipmentID ="", equipmentName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_equipment_flagment);

        //Khoi tao
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        txtScreenName = findViewById(R.id.listComputersScreenName);
        imgToolBar = findViewById(R.id.listComputersToolBarBack);

        //Nhan du lieu
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            equipmentID = bundle.getString("equipmentID");
            equipmentName = bundle.getString("equipmentName");
        }
        txtScreenName.setText("Danh sách " + equipmentName + " phòng " + ROOMNAME);

        displayListEquipments();

        //Processing event tool bar
        imgToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Hien thi danh sach thiet bi theo phan loai
    private void displayListEquipments() {
        //Get views layout
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.listComputersRecycleView);

        //Khoi tao gia tri
        listEquipmentsModels = new Vector<ListRoomCardViewModel>();
        listEquipmentsModels.add(new ListRoomCardViewModel("MT01"));
        listEquipmentsModels.add(new ListRoomCardViewModel("MT02"));
        listEquipmentsModels.add(new ListRoomCardViewModel("MT03"));
        listEquipmentsModels.add(new ListRoomCardViewModel("MT04"));

        //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        listequipmentrecycleview.setLayoutManager(gridLayoutManager);
        ListRoomRecycleAdapter adapter = new ListRoomRecycleAdapter(R.layout.card_view_list_room_layout, listEquipmentsModels);
        listequipmentrecycleview.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListRoomRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(EquipmentsActivity.this, ListMalfunctionEquipmentActivity.class);

//                Bundle bundle = new Bundle();
//                bundle.putString("equipmentIDMal", listEquipments.get(position).getEquipmentID());
//                bundle.putString("equipmentNameMal", listEquipments.get(position).getEquipmentName());
//
//                intent.putExtras(bundle);
                //Truyen du lieu areaID sang listRoom
                startActivity(intent);
            }
        });
    }
}
