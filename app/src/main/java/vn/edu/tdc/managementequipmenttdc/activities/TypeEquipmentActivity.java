package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class TypeEquipmentActivity extends AppCompatActivity {
    //public static String ROOMNAME = "";

    private Vector<ListEquipmentCardViewModel> listTypeEquipmentCardViewModels = new Vector<ListEquipmentCardViewModel>();
    RecyclerView listequipmentrecycleview;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ToolUtils toolUtils;
    ArrayList<Equipment> listTypeEquipments = new ArrayList<Equipment>();
    private FloatingActionButton floatingActionButtonViewListMalfunction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_type_equipment_screen_flagment);

        getSupportActionBar().setTitle("Danh sách thiết bị phòng " + Room_Provider.room.getRoomName());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        toolUtils = new ToolUtils();
        floatingActionButtonViewListMalfunction = findViewById(R.id.ListEquipmentScreenFloatButtonViewListMalfunction);

        getDataOfTypeEquipment();

        floatingActionButtonViewListMalfunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeEquipmentActivity.this, ListMalfunctionOfRoomActivity.class);
                startActivity(intent);

            }
        });
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

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("equipmentID", listTypeEquipments.get(position).getEquipmentID());
                bundle.putString("equipmentName", listTypeEquipments.get(position).getEquipmentName());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
