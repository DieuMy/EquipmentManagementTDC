package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
import vn.edu.tdc.managementequipmenttdc.data_adapter.TypeEquipmentRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;

public class TypeEquipmentActivity extends AppCompatActivity {
    //List Equipment

    private Vector<ListEquipmentCardViewModel> listTypeEquipmentCardViewModels = new Vector<ListEquipmentCardViewModel>();
    RecyclerView listequipmentrecycleview;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ToolUtils toolUtils;
    ArrayList<Equipment> listTypeEquipments = new ArrayList<Equipment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_type_equipment_screen_flagment);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        toolUtils = new ToolUtils();

        getDataOfTypeEquipment();
    }

    public void create(Equipment equipment) {
//        databaseReference.child("equipments").push().setValue(equipment);
        databaseReference.child("equipments").child(equipment.getEquipmentID()).setValue(equipment);
        Toast.makeText(TypeEquipmentActivity.this, "Insert successfully!", Toast.LENGTH_SHORT).show();
    }

    private void getDataOfTypeEquipment() {
        //Lay du lieu cua nhung thiet bi co parentID la NULL(lay loai thiet bi)
        Query query = databaseReference.child("equipments").orderByChild("parentID").equalTo("NULL");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot typeEquipment : dataSnapshot.getChildren()) {
                        Equipment equipment = typeEquipment.getValue(Equipment.class);
                        listTypeEquipments.add(equipment);
                        listTypeEquipmentCardViewModels.add(new ListEquipmentCardViewModel(equipment.getEquipmentName()));
                    }
                    displayListTypeEquipment();
                } else {
                    Toast.makeText(TypeEquipmentActivity.this, "Không tồn tại loại thiết bị nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi danh sach loai thiet bi len layout
    private void displayListTypeEquipment() {
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.ListEquipmentScreenRecycleViewFunction);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);//chia recycleview thanh cot
        listequipmentrecycleview.setLayoutManager(gridLayoutManager);
        TypeEquipmentRecycleViewFunctionAdapter adapter = new TypeEquipmentRecycleViewFunctionAdapter(R.layout.card_view_type_equipment_screen_layout, listTypeEquipmentCardViewModels);
        listequipmentrecycleview.setAdapter(adapter);

        //Set event OnItemClickListener
        adapter.setOnItemClickListener(new TypeEquipmentRecycleViewFunctionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(TypeEquipmentActivity.this, EquipmentsActivity.class);
                startActivity(intent);
            }
        });
    }
}
