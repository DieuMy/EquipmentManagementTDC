package vn.edu.tdc.managementequipmenttdc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.activities.DetailMalfunctionActivity;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;
import vn.edu.tdc.managementequipmenttdc.data_models.Rooms;

public class NotificationPageFragment extends Fragment {
    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayListNotifycationCardViewModels;
    RecyclerView displayListNotifycationRecycleView;
    private ArrayList<RepairDiary> listNotifications = new ArrayList<RepairDiary>();
    private ArrayList<RepairDiary> listNotificationsSearch = new ArrayList<RepairDiary>();
    private ArrayList<Rooms> listOfRoomsManagedByCurrentUser = new ArrayList<Rooms>();

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayoutContainSearch, linearLayoutContainTxtNotification;
    private EditText edtSearch;
    private TextView txtNofication;
    private String TAG = "NotificationPage";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.display_list_notifycation_flagment, container, false);

        //Get views layout
        displayListNotifycationRecycleView = (RecyclerView) view.findViewById(R.id.listNotifycationRecycleView);
        progressBarLoading = view.findViewById(R.id.listNotifycationProgressBar);
        swipeRefreshLayout = view.findViewById(R.id.listNotifycationSwipeRefresh);
        linearLayoutContainSearch = view.findViewById(R.id.listNotifycationLinearlayoutContainSearch);
        edtSearch = view.findViewById(R.id.listNotifycationEdtSearch);
        linearLayoutContainTxtNotification = view.findViewById(R.id.listNotifycationLinearlayoutTextView);
        txtNofication = view.findViewById(R.id.listNotifycationTxtNotification);

        //Initial
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        list_displayListNotifycationCardViewModels = new Vector<DisplayListNotifycationCardViewModel>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        listOfRoomsManagedByCurrentUser.clear();
        listNotifications.clear();
        list_displayListNotifycationCardViewModels.clear();
        getDataNotificationOfUser();
    }

    private void refreshList() {
        listOfRoomsManagedByCurrentUser.clear();
        listNotifications.clear();
        list_displayListNotifycationCardViewModels.clear();

        getDataNotificationOfUser();
        swipeRefreshLayout.setRefreshing(false);
    }

    //Hien thi danh sach thong bao
    private void displayListNotifycationOfDispayListNotifycationScreen() {
        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        displayListNotifycationRecycleView.setLayoutManager(layoutManager);

        DisplayListNotifycationRecycleViewAdapter adapter = new DisplayListNotifycationRecycleViewAdapter(R.layout.card_view_display_list_notifycation_layout, list_displayListNotifycationCardViewModels);
        displayListNotifycationRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DisplayListNotifycationRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMalfunctionActivity.class);
                DetailMalfunctionActivity.REPAIR_DIARY = listNotifications.get(position);
                startActivity(intent);
            }
        });
    }

    private void getDataNotificationOfUser() {
        Query query = databaseReference.child("rooms").orderByChild("userID").equalTo(firebaseAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Rooms rooms = item.getValue(Rooms.class);
                        listOfRoomsManagedByCurrentUser.add(rooms);
                    }

                    getDataHistoryManipulationOfUser();

                } else {
                    Log.d(TAG, "Không có phòng thực hành thuộc quyền quản lý của user này");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDataHistoryManipulationOfUser() {
        progressBarLoading.setVisibility(View.VISIBLE);
        displayListNotifycationRecycleView.setVisibility(View.GONE);

        Query query = databaseReference.child("repairDiarys").orderByChild("processingStatus").equalTo(false);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                displayListNotifycationRecycleView.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        RepairDiary repairDiary = item.getValue(RepairDiary.class);
                        listNotifications.add(repairDiary);
                    }

                    //Duyet de lay danh sach cong viec
                    for (Rooms room : listOfRoomsManagedByCurrentUser) {
                        for (RepairDiary repairDiary : listNotifications) {
                            if (room.getRoomID().equals(repairDiary.getRoomID())) {
                                list_displayListNotifycationCardViewModels.add(new DisplayListNotifycationCardViewModel(
                                        "Phòng " + room.getRoomName() + " - Máy " + repairDiary.getEquipmentID()
                                                + "\n" + repairDiary.getIncident_content() + "\n",
                                        repairDiary.getDateReport()));
                            }
                        }
                    }

                    displayListNotifycationOfDispayListNotifycationScreen();
                } else {
                    Log.d(TAG, "Không lấy được dữ liệu của repairDiary");
                    displayListNotifycationRecycleView.setVisibility(View.GONE);
                    linearLayoutContainTxtNotification.setVisibility(View.VISIBLE);
                    txtNofication.setText("Hiện tại bạn không có thông báo nào");
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
