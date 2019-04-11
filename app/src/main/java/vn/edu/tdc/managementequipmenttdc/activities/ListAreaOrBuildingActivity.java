package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;

import java.util.Vector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.AreaBuildingRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuildingCardviewModel;

public class ListAreaOrBuildingActivity extends AppCompatActivity {
    //Display list area
    private Vector<AreaBuildingCardviewModel> list_areaBuildingCardviewModels;
    RecyclerView areaBuildingRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.areabuilding_screen_flagment);
        displayListAreaBuilding();
    }

    //Hien thi danh sach cac khu vuc/toa nha
    private void displayListAreaBuilding() {
        //Get views layout
        areaBuildingRecycleView = (RecyclerView) findViewById(R.id.areaBuildingRecycleView);

        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
        //Khoi tao gia tri
        list_areaBuildingCardviewModels = new Vector<AreaBuildingCardviewModel>();
        list_areaBuildingCardviewModels.add(new AreaBuildingCardviewModel("Khu A"));
        list_areaBuildingCardviewModels.add(new AreaBuildingCardviewModel("Khu B"));
        list_areaBuildingCardviewModels.add(new AreaBuildingCardviewModel("Khu C"));
        list_areaBuildingCardviewModels.add(new AreaBuildingCardviewModel("Khu H"));

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        areaBuildingRecycleView.setLayoutManager(layoutManager);

        AreaBuildingRecycleAdapter adapter = new AreaBuildingRecycleAdapter(R.layout.card_view_areabuilding_layout, list_areaBuildingCardviewModels);
        areaBuildingRecycleView.setAdapter(adapter);

    }
}
