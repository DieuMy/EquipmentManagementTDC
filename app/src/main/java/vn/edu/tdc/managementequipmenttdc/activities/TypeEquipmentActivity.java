package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;

import java.util.Vector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListEquipmentRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;

public class TypeEquipmentActivity extends AppCompatActivity {
    //List Equipment
    private Vector<ListEquipmentCardViewModel> listTypeEquipmentCardViewModels;
    RecyclerView listequipmentrecycleview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_type_equipment_screen_flagment);

        displayListTypeEquipment();
    }


    private void displayListTypeEquipment() {
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.ListEquipmentScreenRecycleViewFunction);

        listTypeEquipmentCardViewModels = new Vector<ListEquipmentCardViewModel>();
        listTypeEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy tính"));
        listTypeEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy in"));
        listTypeEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy photo"));
        listTypeEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy scan"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);//chia recycleview thanh cot
        listequipmentrecycleview.setLayoutManager(gridLayoutManager);
        ListEquipmentRecycleViewFunctionAdapter adapter = new ListEquipmentRecycleViewFunctionAdapter(R.layout.card_view_type_equipment_screen_layout, listTypeEquipmentCardViewModels);
        listequipmentrecycleview.setAdapter(adapter);
    }
}
