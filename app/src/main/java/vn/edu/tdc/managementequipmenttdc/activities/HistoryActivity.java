package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class HistoryActivity extends AppCompatActivity {

    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayHistoryCardViewModels = new Vector<DisplayListNotifycationCardViewModel>();
    RecyclerView displayListNotifycationRecycleView;
    private ArrayList<RepairDiary> listRepairDiaryArray = new ArrayList<RepairDiary>();

    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_notifycation_flagment);

        getSupportActionBar().setTitle("Lịch sử");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        progressBarLoading = findViewById(R.id.listNotifycationProgressBar);
        displayListNotifycationRecycleView = findViewById(R.id.displayNotifycationRecycleView);
        swipeRefreshLayout = findViewById(R.id.listNotifycationSwipeRefresh);

        getDataHistoryManipulationOfUser();

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

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
        displayListNotifycationRecycleView.setVisibility(View.VISIBLE);
    }

    private void refreshList() {
        list_displayHistoryCardViewModels.clear();
        getDataHistoryManipulationOfUser();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getDataHistoryManipulationOfUser() {
        progressBarLoading.setVisibility(View.VISIBLE);
        displayListNotifycationRecycleView.setVisibility(View.GONE);
        Query query = databaseReference.child("repairDiarys").orderByChild("userIDReport").equalTo(User_Provider.user.getUserID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                displayListNotifycationRecycleView.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        RepairDiary repairDiary = item.getValue(RepairDiary.class);
                        listRepairDiaryArray.add(repairDiary);
                        list_displayHistoryCardViewModels.add(new DisplayListNotifycationCardViewModel(repairDiary.getEquipmentID() + "\n" + repairDiary.getIncident_content(), repairDiary.getDateReport()));
                    }
                    displayHistoryManipulationOfUser();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi lich su
    private void displayHistoryManipulationOfUser() {
        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        displayListNotifycationRecycleView.setLayoutManager(layoutManager);

        DisplayListNotifycationRecycleViewAdapter adapter = new DisplayListNotifycationRecycleViewAdapter(R.layout.card_view_display_list_notifycation_layout, list_displayHistoryCardViewModels);
        displayListNotifycationRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DisplayListNotifycationRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(HistoryActivity.this, DetailMalfunctionActivity.class);
                //Toast.makeText(HistoryActivity.this, listRepairDiaryArray.get(position).getIncident_content(), Toast.LENGTH_SHORT).show();
                DetailMalfunctionActivity.REPAIR_DIARY = listRepairDiaryArray.get(position);
                startActivity(intent);
            }
        });

    }
}
