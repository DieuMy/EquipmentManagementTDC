package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListMalfunctionEquipmentAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListMalfunctionEquipmentModels;

public class ListMalfunctionEquipmentActivity extends AppCompatActivity {

    //Display list notifycation
    private Vector<ListMalfunctionEquipmentModels> list_displayListMalfunctionCardViewModels;
    RecyclerView recycleViewListMalfunction;

    private TextView txtScreenName;
    private ImageView imgToolBar;
    private Button btnOK;
    Intent intent;
    private String equipmentID ="", equipmentName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malfunction_equipment_layout);

        recycleViewListMalfunction = findViewById(R.id.listMalfunctionRecycleView);
        btnOK = findViewById(R.id.listMalfuntionBtnOK);

        txtScreenName = findViewById(R.id.listMalfunctionTxtScreenName);
//        //Nhan du lieu
//        intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null) {
//            equipmentID = bundle.getString("equipmentIDMal");
//            equipmentName = bundle.getString("equipmentNameMal");
//        }
        txtScreenName.setText("Danh sách sự cố của máy " + equipmentName);
        imgToolBar = findViewById(R.id.listMalfunctionToolBarBack);

        displayListMalfunctionOfEquipment();

        //Proccessing event for button OK
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ListMalfunctionEquipmentActivity.this, ReportMalfunctionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Proccessing event tool bar back
        imgToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Hien thi lich su
    private void displayListMalfunctionOfEquipment() {
        //Khoi tao gia tri
        list_displayListMalfunctionCardViewModels = new Vector<ListMalfunctionEquipmentModels>();

        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));

        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListMalfunctionCardViewModels.add(new ListMalfunctionEquipmentModels("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleViewListMalfunction.setLayoutManager(layoutManager);

        ListMalfunctionEquipmentAdapter adapter = new ListMalfunctionEquipmentAdapter(R.layout.card_view_popup_malfunction_equipment_layout, list_displayListMalfunctionCardViewModels);
        recycleViewListMalfunction.setAdapter(adapter);

    }

}
