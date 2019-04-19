package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

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
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;
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
    private TextView txtReportAll;
    private ImageView imgToolBar;
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

        txtScreenName = findViewById(R.id.listComputersScreenName);
        imgToolBar = findViewById(R.id.listComputersToolBarBack);

        //Nhan du lieu
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            equipmentID = bundle.getString("equipmentID");
            equipmentName = bundle.getString("equipmentName");
        }

        txtReportAll = findViewById(R.id.listComputerTxtReportAll);
        chkAll = findViewById(R.id.listComputerChkAll);
        txtScreenName.setText("Danh sách " + equipmentName + " phòng " + ROOMNAME);

        getDataEquipmentOfCorrespondingRoomID();

        //Processing event tool bar
        imgToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    //Lay danh sach cac may theo id phong
    private void getDataEquipmentOfCorrespondingRoomID() {
        //Lay danh sach thiet bi theo id loai thiet bi va id phong .orderByChild("parentID").equalTo(equipmentID)
        Query query = databaseReference.child("equipments").orderByChild("roomID").equalTo(ROOMID);
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
