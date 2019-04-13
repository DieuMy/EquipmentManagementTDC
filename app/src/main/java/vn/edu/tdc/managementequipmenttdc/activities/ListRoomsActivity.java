package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListRoomRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;

public class ListRoomsActivity extends AppCompatActivity {

    //Display room
    private Vector<ListRoomCardViewModel> listRoomCardViewModels;
    RecyclerView listRoomRecycleView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private String areaID, areaName;

    private ArrayList<Rooms> listRooms = new ArrayList<Rooms>();
    private TextView txtScreenName;
    private ImageView imgToolBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_room_flagment);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            areaID = bundle.getString("areaID");
            areaName = bundle.getString("areaName");
        }

        txtScreenName = findViewById(R.id.listRoomTxtScreenName);
        imgToolBack = findViewById(R.id.listRoomToolBarBack);
        txtScreenName.setText("Danh sách phòng khu vực/ tòa nhà " + areaName);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        listRoomCardViewModels = new Vector<ListRoomCardViewModel>();

        getDataRoomsOfCorrespondingArea();

        imgToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Lay danh sach cac phong thuc hanh theo khu vuc tuong ưng
    private void getDataRoomsOfCorrespondingArea() {
        //Lay danh sach phòng theo id cua khu vuc
        Query query = databaseReference.child("rooms").orderByChild("areaID").equalTo(areaID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Duyet de lay danh sach
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Rooms room = item.getValue(Rooms.class);
                        listRooms.add(room);//them room vao danh sach
                        listRoomCardViewModels.add(new ListRoomCardViewModel(room.getRoomName()));
                    }
                    displayListRooms();
                } else {
                    Toast.makeText(ListRoomsActivity.this, "Không có phòng thực hành trong khu vực/ tòa nhà này!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi danh sach cac phong
    private void displayListRooms() {
        //Get views layout
        listRoomRecycleView = (RecyclerView) findViewById(R.id.listRoomRecycleView);

        //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        listRoomRecycleView.setLayoutManager(gridLayoutManager);
        ListRoomRecycleAdapter adapter = new ListRoomRecycleAdapter(R.layout.card_view_list_room_layout, listRoomCardViewModels);
        listRoomRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListRoomRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(ListRoomsActivity.this, TypeEquipmentActivity.class);
                //Truyen du lieu areaID sang listRoom
                EquipmentsActivity.ROOMID = listRooms.get(position).getRoomID();
                EquipmentsActivity.ROOMNAME = listRooms.get(position).getRoomName();

                TypeEquipmentActivity.ROOMNAME = listRooms.get(position).getRoomName();
                startActivity(intent);
            }
        });
    }
}
