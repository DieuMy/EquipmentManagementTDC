package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
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
import vn.edu.tdc.managementequipmenttdc.data_models.Function;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;

public class ListRoomsActivity extends AppCompatActivity {

    //Display room
    private Vector<ListRoomCardViewModel> listRoomCardViewModels = new Vector<ListRoomCardViewModel>();
    RecyclerView listRoomRecycleView;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private ArrayList<Rooms> listRoms = new ArrayList<Rooms>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_room_flagment);

        displayListRooms();
    }

    //Lay danh sach chuc nang cho user dang dang nhap
    private void getDataFunctionsForCurrentUser() {
        //lay quyen cua user dang dang nhap hien tai
        Query query = databaseReference.child("rooms");//Lay danh sach quyen cua user dang dang nhap
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {//kiem tra ton tai du lieu khong
                    //Duyet de lay danh sach
                    for (DataSnapshot func : dataSnapshot.getChildren()) {
                        Rooms rooms = func.getValue(Rooms.class);
                        listRoms.add(rooms);//them function vao danh sach
                        listRoomCardViewModels.add(new ListRoomCardViewModel(rooms.getRoomName()));
                    }
                    displayListRooms();
                } else {
                    Toast.makeText(ListRoomsActivity.this, "Không có chức năng gì", Toast.LENGTH_SHORT).show();
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

        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));

        //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        listRoomRecycleView.setLayoutManager(gridLayoutManager);
        ListRoomRecycleAdapter adapter = new ListRoomRecycleAdapter(R.layout.card_view_list_room_layout, listRoomCardViewModels);
        listRoomRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListRoomRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ListRoomsActivity.this, TypeEquipmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
