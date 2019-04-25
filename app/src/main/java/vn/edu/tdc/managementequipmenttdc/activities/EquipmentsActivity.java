package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

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
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;

public class EquipmentsActivity extends AppCompatActivity {
    //List Equipment
    private Vector<ListRoomCardViewModel> listEquipmentsModels = new Vector<ListRoomCardViewModel>();
    RecyclerView listequipmentrecycleview;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    ToolUtils toolUtils;
    ArrayList<Equipment> listEquipments = new ArrayList<Equipment>();
    String roomID;

    private TextView txtReportAll;
    private CheckBox chkAll;
    private String equipmentID = "", equipmentName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_equipment_flagment);

        //Khoi tao
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //Khoi tao gia tri
        listEquipmentsModels = new Vector<ListRoomCardViewModel>();

        //Nhan du lieu
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            equipmentID = bundle.getString("equipmentID");
            equipmentName = bundle.getString("equipmentName");
        }

        txtReportAll = findViewById(R.id.listComputerTxtReportAll);
        chkAll = findViewById(R.id.listComputerChkAll);

        getSupportActionBar().setTitle("Danh sách " + equipmentName + " phòng " + Room_Provider.ROOMNAME);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDataEquipmentOfCorrespondingRoomID();

        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkAll.isChecked()) {
                  txtReportAll.setVisibility(View.VISIBLE);
                }
                else{
                    txtReportAll.setVisibility(View.GONE);
                }
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

    //Lay danh sach cac may theo id phong
    private void getDataEquipmentOfCorrespondingRoomID() {
        //Lay danh sach thiet bi theo id loai thiet bi va id phong .orderByChild("parentID").equalTo(equipmentID)
        Query query = databaseReference.child("equipments").orderByChild("roomID").equalTo(Room_Provider.ROOMID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Toast.makeText(EquipmentsActivity.this, "Oh yeah", Toast.LENGTH_SHORT).show();
                    //Duyet de lay danh sach
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Equipment equipment = item.getValue(Equipment.class);
                        listEquipments.add(equipment);//them room vao danh sach
                        //listEquipmentsModels.add(new ListRoomCardViewModel(equipment.getEquipmentName()));
                    }

                    //Duyet lay danh sach cac thiet bị thuoc loai thiet bi duoc chon
                    for (Equipment equipment : listEquipments) {
                        if (equipment.getParentID().equals(equipmentID)) {
                            listEquipmentsModels.add(new ListRoomCardViewModel(equipment.getEquipmentName()));
                        } else {
                            final Dialog dialog = new Dialog(EquipmentsActivity.this);
                            dialog.setContentView(R.layout.popup_notifycation_layout);

                            ImageView imgCloseDialog = dialog.findViewById(R.id.popup_close_dialog);
                            Button btnOKDialog = dialog.findViewById(R.id.popup_dialog_buttonOK);
                            TextView txtNofication = dialog.findViewById(R.id.popup_dialog_notification);

                            txtNofication.setText("Không có thiết bị thuộc loại " + equipmentName + " trong phòng này!");

                            //Processing event for close dialog
                            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });

                            btnOKDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    }

                    displayListEquipments();
                } else {
                    final Dialog dialog = new Dialog(EquipmentsActivity.this);
                    dialog.setContentView(R.layout.popup_notifycation_layout);

                    ImageView imgCloseDialog = dialog.findViewById(R.id.popup_close_dialog);
                    Button btnOKDialog = dialog.findViewById(R.id.popup_dialog_buttonOK);
                    TextView txtNofication = dialog.findViewById(R.id.popup_dialog_notification);

                    txtNofication.setText("Không có thiết bị thuộc loại " + equipmentName + " trong phòng này!");

                    //Processing event for close dialog
                    imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    btnOKDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    //Toast.makeText(EquipmentsActivity.this, "Không có thiết bị thuộc loại " + equipmentName + " trong phòng này!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi danh sach thiet bi theo phan loai
    private void displayListEquipments() {
        //Get views layout
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.listComputersRecycleView);

        //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        listequipmentrecycleview.setLayoutManager(gridLayoutManager);
        ListRoomRecycleAdapter adapter = new ListRoomRecycleAdapter(R.layout.card_view_list_room_layout, listEquipmentsModels);
        listequipmentrecycleview.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListRoomRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(EquipmentsActivity.this, ListMalfunctionOneEquipmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("equipmentIDMal", listEquipments.get(position).getEquipmentID());
                bundle.putString("equipmentNameMal", listEquipments.get(position).getEquipmentName());

                intent.putExtras(bundle);

                ViewReportMalfunctionActivity.EQUIPMENTID = listEquipments.get(position).getEquipmentID();
                ViewReportMalfunctionActivity.EQUIPMENTNAME = listEquipments.get(position).getEquipmentName();
                //Truyen du lieu areaID sang listRoom
                startActivity(intent);
            }
        });
    }
}
