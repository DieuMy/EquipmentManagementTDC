package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;

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

    //List Equipment
    private Vector<ListRoomCardViewModel> listEquipmentsModels = new Vector<ListRoomCardViewModel>();
    RecyclerView listequipmentrecycleview;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ToolUtils toolUtils;
    ArrayList<Equipment> listEquipments = new ArrayList<Equipment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_equipment_flagment);
        displayListEquipments();
    }

    //Hien thi danh sach thiet bi theo phan loai
    private void displayListEquipments() {
        //Get views layout
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.listComputersRecycleView);

        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
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
    }
}
