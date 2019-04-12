package vn.edu.tdc.managementequipmenttdc.activities;

import android.app.DownloadManager;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.AreaBuildingRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuilding;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuildingCardviewModel;
import vn.edu.tdc.managementequipmenttdc.tools.ToolUtils;

public class ListAreaOrBuildingActivity extends AppCompatActivity {
    //Display list area
    private Vector<AreaBuildingCardviewModel> list_areaBuildingCardviewModels = new Vector<AreaBuildingCardviewModel>();;
    RecyclerView areaBuildingRecycleView;

    private ArrayList<AreaBuilding> listAreaBuilding = new ArrayList<AreaBuilding>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ToolUtils toolUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areabuilding_screen_flagment);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        toolUtils = new ToolUtils();

        getAllDataOfAreaBuilding();

    }

    //Lay danh sach ca khu vuc/ toa nha
    private void getAllDataOfAreaBuilding(){
        Query query = databaseReference.child("area_buildings");//Lay toa bo du lieu bang are_buildings
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())){
                    //Duyet de lay danh sach
                    for (DataSnapshot area : dataSnapshot.getChildren()) {
                        AreaBuilding areaBuilding = area.getValue(AreaBuilding.class);
                        listAreaBuilding.add(areaBuilding);//them function vao danh sach
                        list_areaBuildingCardviewModels.add(new AreaBuildingCardviewModel(R.drawable.ic_building, areaBuilding.getAreaName()));
                    }
                    displayListAreaBuilding();
                } else{
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
        //Get views layout
        areaBuildingRecycleView = (RecyclerView) findViewById(R.id.areaBuildingRecycleView);

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        areaBuildingRecycleView.setLayoutManager(layoutManager);

        AreaBuildingRecycleAdapter adapter = new AreaBuildingRecycleAdapter(R.layout.card_view_areabuilding_layout, list_areaBuildingCardviewModels);
        areaBuildingRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AreaBuildingRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ListAreaOrBuildingActivity.this, ListRoomsActivity.class);
                startActivity(intent);
            }
        });

    }
}
