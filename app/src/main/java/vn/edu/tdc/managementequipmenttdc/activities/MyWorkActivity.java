package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class MyWorkActivity extends AppCompatActivity {

    private final String TAG = "MyWorkActivity";
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayListNotifycationCardViewModels;
    RecyclerView recycleViewDisplayListNotifycation;

    private LinearLayout linearLayoutNotifycation;
    private TextView txtNotification;
    private LinearLayout linearLayoutCheckBox;
    private ProgressBar progressBarLoading;
    private ArrayList<Rooms> listOfRoomsManagedByCurrentUser = new ArrayList<Rooms>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_notifycation_flagment);

        getSupportActionBar().setTitle("Công việc của tôi");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initial
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        list_displayListNotifycationCardViewModels = new Vector<DisplayListNotifycationCardViewModel>();

        //Gets view from layout
        recycleViewDisplayListNotifycation = findViewById(R.id.displayNotifycationRecycleView);
        progressBarLoading = findViewById(R.id.listNotifycationProgressBar);

        linearLayoutNotifycation = findViewById(R.id.listNotifycationLinearlayoutTextView);
        txtNotification = findViewById(R.id.listNotifycationTxtNotification);
        linearLayoutCheckBox = findViewById(R.id.displayNotifycationLinnearLayoutCheckBox);
        linearLayoutCheckBox.setVisibility(View.VISIBLE);

        getDataMyWorkOfCurrentUserWithRoleIsEmployee();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
    }

    private void getDataMyWorkOfCurrentUserWithRoleIsEmployee() {
        Query query = databaseReference.child("rooms").orderByChild("userID").equalTo(firebaseAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Rooms rooms = item.getValue(Rooms.class);
                        listOfRoomsManagedByCurrentUser.add(rooms);
                    }

                    getDataRepairDiary();

                } else {
                    recycleViewDisplayListNotifycation.setVisibility(View.GONE);
                    linearLayoutNotifycation.setVisibility(View.VISIBLE);
                    linearLayoutCheckBox.setVisibility(View.GONE);
                    txtNotification.setText("Không có phòng thực hành nào thuộc quyền quản lý của bạn");
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataRepairDiary() {
        progressBarLoading.setVisibility(View.VISIBLE);
        recycleViewDisplayListNotifycation.setVisibility(View.GONE);
        final ArrayList<RepairDiary> listRepairDiary = new ArrayList<RepairDiary>();
        Query query = databaseReference.child("repairDiarys");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                recycleViewDisplayListNotifycation.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        RepairDiary repairDiary = item.getValue(RepairDiary.class);
                        listRepairDiary.add(repairDiary);
                    }

                    //Duyet de lay danh sach cong viec
                    for (Rooms room : listOfRoomsManagedByCurrentUser) {
                        for (RepairDiary repairDiary : listRepairDiary) {
                            if (room.getRoomID().equals(repairDiary.getRoomID())) {
                                list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel(
                                        "Phòng " + room.getRoomName() + " - Máy " + repairDiary.getEquipmentID()
                                                + "\n" + repairDiary.getIncident_content() + "\n",
                                        repairDiary.getDateReport()));
                            }
                        }
                    }

                    if (list_displayListNotifycationCardViewModels.size() == 0) {
                        recycleViewDisplayListNotifycation.setVisibility(View.GONE);
                        linearLayoutNotifycation.setVisibility(View.VISIBLE);
                        linearLayoutCheckBox.setVisibility(View.GONE);
                        txtNotification.setText("Hiện tại bạn không có công việc nào cần xử lý");
                        return;
                    }

                    displayListMyWorkOfUserIsEmployee();
                } else {
                    Log.d(TAG, "Không lấy được dữ liệu của repairDỉay");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayListMyWorkOfUserIsEmployee() {
        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleViewDisplayListNotifycation.setLayoutManager(layoutManager);

        DisplayListNotifycationRecycleViewAdapter adapter = new DisplayListNotifycationRecycleViewAdapter(R.layout.card_view_display_list_notifycation_layout, list_displayListNotifycationCardViewModels);
        recycleViewDisplayListNotifycation.setAdapter(adapter);
        adapter.setOnItemClickListener(new DisplayListNotifycationRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MyWorkActivity.this, DetailMyWorkActivity.class);
                startActivity(intent);
            }
        });

    }
}
