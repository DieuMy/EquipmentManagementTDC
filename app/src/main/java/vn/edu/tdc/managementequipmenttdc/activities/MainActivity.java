package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HomeScreenRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListRoomRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Function;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListEquipmentRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Permissions;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    //    Home Screen
    private Vector<HomeScreenCardViewModel> listFunctionsHomeScreen = new Vector<HomeScreenCardViewModel>();
    RecyclerView homeScreenrecyclerViewFunctions;

    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayListNotifycationCardViewModels;
    RecyclerView displayListNotifycationRecycleView;

    //Display room
    private Vector<ListRoomCardViewModel> listRoomCardViewModels;
    RecyclerView listRoomRecycleView;
    //List Equipment
    private Vector<ListEquipmentCardViewModel> listEquipmentCardViewModels;
    RecyclerView listequipmentrecycleview;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private ArrayList<Function> listFunctions = new ArrayList<Function>();
    private ArrayList<Permissions> listPermissions = new ArrayList<Permissions>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Khoi tao
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Check user login
        if (null == firebaseAuth.getCurrentUser()) {
            intent = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

        //Hien thi trang chu
        setContentView(R.layout.home_screen_flagment);
        getDataFunctionsForCurrentUser();
        //Toast.makeText(MainActivity.this, User_Provider.username, Toast.LENGTH_SHORT).show();

    }

    //Lay danh sach chuc nang cho user dang dang nhap
    private void getDataFunctionsForCurrentUser() {
        //lay quyen cua user dang dang nhap hien tai
        Query query = databaseReference.child("functions");//Lay danh sach quyen cua user dang dang nhap
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {//kiem tra ton tai du lieu khong
                    //Duyet de lay danh sach
                    for (DataSnapshot func : dataSnapshot.getChildren()) {
                        Function function = func.getValue(Function.class);
                        listFunctions.add(function);//them function vao danh sach
                        listFunctionsHomeScreen.add(new HomeScreenCardViewModel(R.drawable.ic_login, function.getFunctionName()));
                    }
                    displayListFunctionOfUsersAtHomePage();
                } else {
                    Toast.makeText(MainActivity.this, "Không có chức năng gì", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //Display list functions of users
    private void displayListFunctionOfUsersAtHomePage() {
        //Get views layout
        homeScreenrecyclerViewFunctions = (RecyclerView) findViewById(R.id.homeScreenRecycleViewFunctions);

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        homeScreenrecyclerViewFunctions.setLayoutManager(layoutManager);

        HomeScreenRecycleViewFunctionAdapter adapter = new HomeScreenRecycleViewFunctionAdapter(R.layout.card_view_home_screen_layout, listFunctionsHomeScreen);
        homeScreenrecyclerViewFunctions.setAdapter(adapter);

        //Set event OnItemClickListener
        adapter.setOnItemClickListener(new HomeScreenRecycleViewFunctionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String activityClass = listFunctions.get(position).getPackageClass() + listFunctions.get(position).getActivityClass();

                try {
                    Class<?> myClass = Class.forName(activityClass);
                    Intent intent = new Intent(MainActivity.this, myClass);
                    startActivity(intent);

                } catch (ClassNotFoundException ignored) {
                    Toast.makeText(MainActivity.this, "Chức năng đang được phát triển", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //-------------------------------------------------------------------------------------
    //Hien thi danh sach cac phong
    private void displayListRooms() {
        //Get views layout
        listRoomRecycleView = (RecyclerView) findViewById(R.id.listComputersRecycleView);

        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
        //Khoi tao gia tri
        listRoomCardViewModels = new Vector<ListRoomCardViewModel>();
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));

        //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        listRoomRecycleView.setLayoutManager(gridLayoutManager);
        ListRoomRecycleAdapter adapter = new ListRoomRecycleAdapter(R.layout.card_view_list_room_layout, listRoomCardViewModels);
        listRoomRecycleView.setAdapter(adapter);
    }

    private void displayListEquipment() {
        listequipmentrecycleview = (RecyclerView) findViewById(R.id.ListEquipmentScreenRecycleViewFunction);
        listEquipmentCardViewModels = new Vector<ListEquipmentCardViewModel>();
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy tính"));
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy lạnh"));
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Đèn"));
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy chiếu"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);//chia recycleview thanh cot
        listequipmentrecycleview.setLayoutManager(gridLayoutManager);
        ListEquipmentRecycleViewFunctionAdapter adapter = new ListEquipmentRecycleViewFunctionAdapter(R.layout.card_view_list_equipment_screen_layout, listEquipmentCardViewModels);
        listequipmentrecycleview.setAdapter(adapter);
    }

    //Hien thi danh sach thong bao
    private void displayListNotifycationOfDispayListNotifycationScreen() {
        //Get views layout
        displayListNotifycationRecycleView = (RecyclerView) findViewById(R.id.displayNotifycationRecycleView);

        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
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

    }

    //    private void updateUI(){
//        //Gan fragment
//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        AbstractFragment fragment = new AbstractFragment();
//        fragment = new LoginFragment();
//        //fragmentTransaction.replace(R.id.fragmentContainer, fragment);
//        fragmentTransaction.commit();
//
//    }
}
