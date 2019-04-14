package vn.edu.tdc.managementequipmenttdc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.activities.DetailMalfunctionActivity;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;

public class NotificationPageFragment extends Fragment {
    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayListNotifycationCardViewModels;
    RecyclerView displayListNotifycationRecycleView;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.display_list_notifycation_flagment, container, false);
        //Get views layout
        displayListNotifycationRecycleView = (RecyclerView) view.findViewById(R.id.displayNotifycationRecycleView);

        displayListNotifycationOfDispayListNotifycationScreen();
        return view;
    }


    //Hien thi danh sach thong bao
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        displayListNotifycationRecycleView.setLayoutManager(layoutManager);

        DisplayListNotifycationRecycleViewAdapter adapter = new DisplayListNotifycationRecycleViewAdapter(R.layout.card_view_display_list_notifycation_layout, list_displayListNotifycationCardViewModels);
        displayListNotifycationRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DisplayListNotifycationRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intent = new Intent(getActivity(), DetailMalfunctionActivity.class);
                startActivity(intent);
            }
        });


    }
}
