package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.AreaBuildingRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuilding;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuildingCardviewModel;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;

public class ListAreaOrBuildingActivity extends AppCompatActivity {
    //Display list area
    private Vector<AreaBuildingCardviewModel> list_areaBuildingCardviewModels = new Vector<AreaBuildingCardviewModel>();
    RecyclerView areaBuildingRecycleView;

    private ArrayList<AreaBuilding> listAreaBuilding = new ArrayList<AreaBuilding>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ToolUtils toolUtils;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areabuilding_screen_flagment);

        //Get views layout
        areaBuildingRecycleView = (RecyclerView) findViewById(R.id.areaBuildingRecycleView);
        progressBarLoading = findViewById(R.id.areaBuildingProgressBar);
        swipeRefreshLayout = findViewById(R.id.areaBuildingswipeRefresh);

        getSupportActionBar().setTitle("Khu vực/ tòa nhà");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolUtils = new ToolUtils();

        getAllDataOfAreaBuilding();

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
        areaBuildingRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void refreshList() {
        listAreaBuilding.clear();
        list_areaBuildingCardviewModels.clear();
        getAllDataOfAreaBuilding();
        swipeRefreshLayout.setRefreshing(false);
    }

    //Lay danh sach ca khu vuc/ toa nha
    private void getAllDataOfAreaBuilding() {
        progressBarLoading.setVisibility(View.VISIBLE);
        areaBuildingRecycleView.setVisibility(View.GONE);
        Query query = databaseReference.child("area_buildings");//Lay toa bo du lieu bang are_buildings
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                areaBuildingRecycleView.setVisibility(View.VISIBLE);
                if ((dataSnapshot.exists())) {
                    //Duyet de lay danh sach
                    for (DataSnapshot area : dataSnapshot.getChildren()) {
                        AreaBuilding areaBuilding = area.getValue(AreaBuilding.class);
                        listAreaBuilding.add(areaBuilding);//them function vao danh sach
                        list_areaBuildingCardviewModels.add(new AreaBuildingCardviewModel(R.drawable.ic_building, areaBuilding.getAreaName()));
                    }
                    displayListAreaBuilding();
                } else {
                    Toast.makeText(ListAreaOrBuildingActivity.this, "Không tồn tại khu vực/ tòa nhà nào!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi danh sach cac khu vuc/toa nha
    private void displayListAreaBuilding() {
        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        areaBuildingRecycleView.setLayoutManager(layoutManager);

        AreaBuildingRecycleAdapter adapter = new AreaBuildingRecycleAdapter(R.layout.card_view_areabuilding_layout, list_areaBuildingCardviewModels);
        areaBuildingRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AreaBuildingRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ListAreaOrBuildingActivity.this, ListRoomsActivity.class);
                //Truyen du lieu areaID sang listRoom
                Bundle bundle = new Bundle();
                bundle.putString("areaID", listAreaBuilding.get(position).getAreaID());
                bundle.putString("areaName", listAreaBuilding.get(position).getAreaName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
