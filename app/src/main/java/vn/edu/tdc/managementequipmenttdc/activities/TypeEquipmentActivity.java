package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.TypeEquipmentRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;
import vn.edu.tdc.managementequipmenttdc.tools.Room_Provider;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class TypeEquipmentActivity extends AppCompatActivity {
    private Vector<ListEquipmentCardViewModel> listTypeEquipmentCardViewModels = new Vector<ListEquipmentCardViewModel>();
    RecyclerView listequipmentrecycleview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBarLoading;
    private LinearLayout linearLayoutContainSearch;
    private EditText edtSearch;

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

        //Gets view from layout
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.ListEquipmentScreenRecycleViewFunction);
        linearLayoutContainSearch = findViewById(R.id.typeEquipmentLinearlayoutContainSearch);
        swipeRefreshLayout = findViewById(R.id.typeEquipmentSwipeRefresh);
        progressBarLoading = findViewById(R.id.typeEquipmentProgressBar);
        edtSearch = findViewById(R.id.typeEquipmentEdtSearch);

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
    }

    private void refreshList() {
        listTypeEquipmentCardViewModels.clear();
        getDataOfTypeEquipment();
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_search: //Xu ly item xoa
                linearLayoutContainSearch.setVisibility(View.VISIBLE);
                listequipmentrecycleview.setVisibility(View.GONE);
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
                                Toast.makeText(TypeEquipmentActivity.this, "Hãy nhập nội dung tìm kiếm", Toast.LENGTH_SHORT).show();
                                edtSearch.requestFocus();
                                return false;
                            }

                            listTypeEquipmentCardViewModels.clear();
                            for (Equipment equipment : listTypeEquipments) {
                                if (equipment.getEquipmentName().contains(contentSearch)) {
                                    listTypeEquipmentCardViewModels.add(new ListEquipmentCardViewModel(equipment.getEquipmentName()));
                                }
                            }

                            if (listTypeEquipmentCardViewModels.size() < 1) {
                                Toast.makeText(TypeEquipmentActivity.this, "Không tồn tại loại thiết bị bạn cần tìm", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            listequipmentrecycleview.setVisibility(View.VISIBLE);
                            linearLayoutContainSearch.setVisibility(View.GONE);
                            displayListTypeEquipment();
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

    private void getDataOfTypeEquipment() {
        progressBarLoading.setVisibility(View.VISIBLE);
        listequipmentrecycleview.setVisibility(View.GONE);
        //Lay du lieu cua nhung thiet bi co parentID la NULL(lay loai thiet bi)
        Query query = databaseReference.child("equipments").orderByChild("parentID").equalTo("NULL");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                listequipmentrecycleview.setVisibility(View.VISIBLE);
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
