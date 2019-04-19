package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;

public class MyWorkActivity extends AppCompatActivity {

    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayListNotifycationCardViewModels;
    RecyclerView displayListNotifycationRecycleView;

    private TextView txtScreenName;
    private LinearLayout linearLayoutCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_notifycation_flagment);

        //Gets view from layout
        displayListNotifycationRecycleView = findViewById(R.id.displayNotifycationRecycleView);
        txtScreenName = findViewById(R.id.displayNotifycationScreenName);
        txtScreenName.setText("Công việc của tôi");

        linearLayoutCheckBox = findViewById(R.id.displayNotifycationLinnearLayoutCheckBox);
        linearLayoutCheckBox.setVisibility(View.VISIBLE);


        displayListNotifycationOfDispayListNotifycationScreen();
    }

    private void displayListNotifycationOfDispayListNotifycationScreen() {
        //Khoi tao gia tri
        list_displayListNotifycationCardViewModels = new Vector<DisplayListNotifycationCardViewModel>();

        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));
        list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel("Sự cố #BH005 đã được xử lý", "01/04/2019 09:00:15"));

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        displayListNotifycationRecycleView.setLayoutManager(layoutManager);

        DisplayListNotifycationRecycleViewAdapter adapter = new DisplayListNotifycationRecycleViewAdapter(R.layout.card_view_display_list_notifycation_layout, list_displayListNotifycationCardViewModels);
        displayListNotifycationRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DisplayListNotifycationRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MyWorkActivity.this, DetailMalfunctionActivity.class);
                startActivity(intent);
            }
        });

    }
}
