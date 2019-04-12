package vn.edu.tdc.managementequipmenttdc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.activities.MainActivity;
import vn.edu.tdc.managementequipmenttdc.activities.SplashActivity;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HomeScreenRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Function;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Log;
import vn.edu.tdc.managementequipmenttdc.data_models.Permissions;

public class HomePageFragment extends Fragment {

    //    Home Screen
    private Vector<HomeScreenCardViewModel> listFunctionsHomeScreen = new Vector<HomeScreenCardViewModel>();
    RecyclerView homeScreenrecyclerViewFunctions;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private ArrayList<Function> listFunctions = new ArrayList<Function>();
    private ArrayList<Permissions> listPermissions = new ArrayList<Permissions>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Khoi tao
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        View view = null;
        view = inflater.inflate(R.layout.home_screen_flagment, container, false);
        //Get views layout
        homeScreenrecyclerViewFunctions = (RecyclerView) view.findViewById(R.id.homeScreenRecycleViewFunctions);

        //Check user login
        if (null == firebaseAuth.getCurrentUser()) {
            intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        //Hien thi trang chu
        getDataFunctionsForCurrentUser();
        return view;
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
                    Toast.makeText(getActivity(), "Không có chức năng gì", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //Display list functions of users
    private void displayListFunctionOfUsersAtHomePage() {

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
                    Intent intent = new Intent(getActivity(), myClass);
                    startActivity(intent);

                } catch (ClassNotFoundException ignored) {
                    Toast.makeText(getActivity(), "Chức năng đang được phát triển", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
