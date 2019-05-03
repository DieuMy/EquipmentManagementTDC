package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListRoomRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;

public class EquipmentsActivity extends AppCompatActivity {
    //List Equipment
    private Vector<ListRoomCardViewModel> listEquipmentsModels = new Vector<ListRoomCardViewModel>();
    RecyclerView listequipmentrecycleview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBarLoading;
    private LinearLayout linearLayoutContainSearch, linearLayoutContainChkAll;
    private EditText edtSearch;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    ToolUtils toolUtils;
    ArrayList<Equipment> listEquipments = new ArrayList<Equipment>();
    ArrayList<Equipment> listEquipmentArcodingType = new ArrayList<Equipment>();
    String roomID;

    private TextView txtReportAll;
    private CheckBox chkAll;
    private String equipmentID = "", equipmentName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_equipment_flagment);

        //Gets view from layout
        progressBarLoading = findViewById(R.id.listComputerProgressBar);
        swipeRefreshLayout = findViewById(R.id.listComputerwipeRefresh);
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.listComputersRecycleView);
        linearLayoutContainSearch = findViewById(R.id.listComputerLinearlayoutContainSearch);
        edtSearch = findViewById(R.id.listComputerEdtSearch);
        linearLayoutContainChkAll = findViewById(R.id.listComputerLinearlayoutContainChkAll);

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

        getSupportActionBar().setTitle("Danh sách " + equipmentName + " phòng " + Room_Provider.room.getRoomName());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDataEquipmentOfCorrespondingRoomID();

//        chkAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (chkAll.isChecked()) {
//                    txtReportAll.setVisibility(View.VISIBLE);
//                } else {
//                    txtReportAll.setVisibility(View.GONE);
//                }
//            }
//        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
        listequipmentrecycleview.setVisibility(View.VISIBLE);
        linearLayoutContainSearch.setVisibility(View.GONE);
        linearLayoutContainChkAll.setVisibility(View.GONE);
    }

    private void refreshList() {
        listEquipments.clear();
        listEquipmentsModels.clear();
        listEquipmentArcodingType.clear();
        getDataEquipmentOfCorrespondingRoomID();
        linearLayoutContainSearch.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
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
                linearLayoutContainSearch.setVisibility(View.VISIBLE);
                listequipmentrecycleview.setVisibility(View.GONE);
                linearLayoutContainChkAll.setVisibility(View.GONE);
                edtSearch.requestFocus();

                //Display softkey
                InputMethodManager imgr = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(edtSearch, 0);
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                //Change symbol for softkey
                edtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

                edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            performSearch();
                            String contentSearch = edtSearch.getText().toString().trim();
                            if (contentSearch.isEmpty()) {
                                Toast.makeText(EquipmentsActivity.this, "Hãy nhập nội dung tìm kiếm", Toast.LENGTH_SHORT).show();
                                edtSearch.requestFocus();
                                return false;
                            }
                            listEquipmentsModels.clear();
                            for (Equipment equipment : listEquipmentArcodingType) {
                                if (equipment.getEquipmentName().contains(contentSearch)) {
                                    listEquipmentsModels.add(new ListRoomCardViewModel(equipment.getEquipmentName()));
                                }
                            }
                            if (listEquipmentsModels.size() < 1) {
                                Toast.makeText(EquipmentsActivity.this, "Không tồn tại thiết bị bạn đang tìm", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            listequipmentrecycleview.setVisibility(View.VISIBLE);
                            displayListEquipments();
                            return true;
                        }
                        return false;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performSearch() {
        edtSearch.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
    }

    //Lay danh sach cac may theo id phong
    private void getDataEquipmentOfCorrespondingRoomID() {
        progressBarLoading.setVisibility(View.VISIBLE);
        listequipmentrecycleview.setVisibility(View.GONE);
        //Lay danh sach thiet bi theo id loai thiet bi va id phong .orderByChild("parentID").equalTo(equipmentID)
        Query query = databaseReference.child("equipments").orderByChild("roomID").equalTo(Room_Provider.room.getRoomID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                listequipmentrecycleview.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {

                    //Duyet de lay danh sach
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Equipment equipment = item.getValue(Equipment.class);
                        listEquipments.add(equipment);//them room vao danh sach
                    }

                    //Duyet lay danh sach cac thiet bị thuoc loai thiet bi duoc chon
                    for (Equipment equipment : listEquipments) {
                        if (equipment.getParentID().equals(equipmentID)) {
                            listEquipmentsModels.add(new ListRoomCardViewModel(equipment.getEquipmentName()));
                            listEquipmentArcodingType.add(equipment);
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
