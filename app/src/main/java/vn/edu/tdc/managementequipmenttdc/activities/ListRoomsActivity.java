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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListRoomRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;

public class ListRoomsActivity extends AppCompatActivity {

    public static String FUNCTIONNAME = "";
    //Display room
    private Vector<ListRoomCardViewModel> listRoomCardViewModels;
    RecyclerView listRoomRecycleView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private String areaID, areaName;

    private ArrayList<Rooms> listRooms = new ArrayList<Rooms>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBarLoading;
    private LinearLayout linearLayoutContainSearch;
    private EditText edtSearch;

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

        //Gets view from layout
        progressBarLoading = findViewById(R.id.listRoomProgressBar);
        swipeRefreshLayout = findViewById(R.id.listRoomswipeRefresh);
        listRoomRecycleView = (RecyclerView) findViewById(R.id.listRoomRecycleView);
        //Gets view from layout
        linearLayoutContainSearch = findViewById(R.id.listRoomsLinearlayoutContainSearch);
        edtSearch = findViewById(R.id.listRoomsEdtSearch);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        listRoomCardViewModels = new Vector<ListRoomCardViewModel>();

        if(FUNCTIONNAME.equals("ListRoomsActivity")){
            getDataAllRooms();
            getSupportActionBar().setTitle("Danh sách tất cả các phòng thực hành");
        } else {
            getDataRoomsOfCorrespondingArea();
            getSupportActionBar().setTitle("Danh sách phòng " + areaName);
        }

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
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
                linearLayoutContainSearch.setVisibility(View.VISIBLE);
                edtSearch.requestFocus();

                //Display softkey
                InputMethodManager imgr = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(edtSearch, 0);
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                //Change symbol for softkey
                edtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

                edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(actionId == EditorInfo.IME_ACTION_SEARCH){
                            performSearch();
                            String contentSearch = edtSearch.getText().toString().trim();
                            if(contentSearch.isEmpty()){
                                Toast.makeText(ListRoomsActivity.this, "Hãy nhập nội dung tìm kiếm", Toast.LENGTH_SHORT).show();
                                edtSearch.requestFocus();
                                return false;
                            }
                            listRoomCardViewModels.clear();
                            for (Rooms room : Room_Provider.listAllRoom) {
                                if (room.getRoomName().contains(contentSearch)) {
                                    listRoomCardViewModels.add(new ListRoomCardViewModel(room.getRoomName()));
                                }
                            }
                            if(listRoomCardViewModels.size() < 1){
                                Toast.makeText(ListRoomsActivity.this, "Không tồn tại phòng thực hành bạn đang tìm", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            displayListRooms();
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
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
        listRoomRecycleView.setVisibility(View.VISIBLE);
        linearLayoutContainSearch.setVisibility(View.GONE);
    }

    private void refreshList() {
        listRoomCardViewModels.clear();
        if(FUNCTIONNAME.equals("ListRoomsActivity")){
            getDataAllRooms();
        } else {
            getDataRoomsOfCorrespondingArea();
        }
        linearLayoutContainSearch.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    //Lay danh sach cac phong thuc hanh theo khu vuc tuong ưng
    private void getDataRoomsOfCorrespondingArea() {
        progressBarLoading.setVisibility(View.VISIBLE);
        listRoomRecycleView.setVisibility(View.GONE);
        //Lay danh sach phòng theo id cua khu vuc
        Query query = databaseReference.child("rooms").orderByChild("areaID").equalTo(areaID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                listRoomRecycleView.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    //Duyet de lay danh sach
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Rooms room = item.getValue(Rooms.class);
                        listRooms.add(room);//them room vao danh sach
                        listRoomCardViewModels.add(new ListRoomCardViewModel(room.getRoomName()));
                    }

                    Room_Provider.listAllRoom = listRooms;
                    displayListRooms();
                } else {
                    final Dialog dialog = new Dialog(ListRoomsActivity.this);
                    dialog.setContentView(R.layout.popup_notifycation_layout);

                    ImageView imgCloseDialog = dialog.findViewById(R.id.popup_close_dialog);
                    Button btnOKDialog = dialog.findViewById(R.id.popup_dialog_buttonOK);
                    TextView txtNofication = dialog.findViewById(R.id.popup_dialog_notification);

                    txtNofication.setText("Không có phòng thực hành trong khu vực/ tòa nhà này!");

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
                   // Toast.makeText(ListRoomsActivity.this, "Không có phòng thực hành trong khu vực/ tòa nhà này!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi danh sach cac phong
    private void displayListRooms() {
         //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        listRoomRecycleView.setLayoutManager(gridLayoutManager);
        ListRoomRecycleAdapter adapter = new ListRoomRecycleAdapter(R.layout.card_view_list_room_layout, listRoomCardViewModels);
        listRoomRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListRoomRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(ListRoomsActivity.this, TypeEquipmentActivity.class);
                Room_Provider.room = listRooms.get(position);
                startActivity(intent);
            }
        });
    }

    //Lay danh sach tat ca cac phong thuc hanh
    private void getDataAllRooms() {
        progressBarLoading.setVisibility(View.VISIBLE);
        listRoomRecycleView.setVisibility(View.GONE);
        Query query = databaseReference.child("rooms");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                listRoomRecycleView.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    //Duyet de lay danh sach
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Rooms room = item.getValue(Rooms.class);
                        listRooms.add(room);//them room vao danh sach
                        listRoomCardViewModels.add(new ListRoomCardViewModel(room.getRoomName()));
                    }
                    Room_Provider.listAllRoom = listRooms;
                    displayListRooms();
                } else {
                    Toast.makeText(ListRoomsActivity.this, "Không tồn tại phòng thực hành nào!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
