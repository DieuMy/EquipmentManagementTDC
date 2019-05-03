package vn.edu.tdc.managementequipmenttdc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.activities.DetailMalfunctionActivity;
import vn.edu.tdc.managementequipmenttdc.activities.ListRoomsActivity;
import vn.edu.tdc.managementequipmenttdc.activities.SplashActivity;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HomeScreenRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.Function;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Permissions;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class HomePageFragment extends Fragment {

    //Home Screen
    private Vector<HomeScreenCardViewModel> listFunctionsHomeScreen = new Vector<HomeScreenCardViewModel>();
    RecyclerView homeScreenrecyclerViewFunctions;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Intent intent;

    private ArrayList<Function> listFunctions = new ArrayList<Function>();
    private ArrayList<Permissions> listPermissions = new ArrayList<Permissions>();
    private ArrayList<Function> listFunctionsOfCurrentUser = new ArrayList<Function>();
    private HomeScreenRecycleViewFunctionAdapter adapter;

    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        progressBarLoading = (ProgressBar) view.findViewById((R.id.homeScreenProgressBar));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.homeScreenswipeRefresh);
        adapter = new HomeScreenRecycleViewFunctionAdapter(R.layout.card_view_home_screen_layout, listFunctionsHomeScreen);

        //Check user login
        if (null == firebaseAuth.getCurrentUser()) {
            intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {

            //Hien thi trang chu
            getDataPermissionForCurrentUser();

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshList();
                }
            });
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
    }

    private void refreshList() {
        listFunctions.clear();
        listPermissions.clear();
        listFunctionsHomeScreen.clear();
        getDataPermissionForCurrentUser();
        swipeRefreshLayout.setRefreshing(false);
    }

    //Lay danh sach chuc nang cho user dang dang nhap
    private void getDataPermissionForCurrentUser() {
        progressBarLoading.setVisibility(View.VISIBLE);
        homeScreenrecyclerViewFunctions.setVisibility(View.GONE);
        listFunctions = getDataFunctionsForCurrentUser();
        //lay quyen cua user dang dang nhap hien tai
        Query query = databaseReference.child("permissions").orderByChild("userID").equalTo(firebaseAuth.getCurrentUser().getUid());//Lay danh sach quyen cua user dang dang nhap
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                homeScreenrecyclerViewFunctions.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {//kiem tra ton tai du lieu khong
                    //Duyet de lay danh sach
                    for (DataSnapshot func : dataSnapshot.getChildren()) {
                        Permissions permissions = func.getValue(Permissions.class);
                        listPermissions.add(permissions);//them function vao danh sach
                    }

                    // Toast.makeText(getActivity(), "Size: " + listFunctions.size(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < listPermissions.size(); i++) {
                        for (int j = 0; j < listFunctions.size(); j++) {
                            if (listPermissions.get(i).getFunctionID().equals(listFunctions.get(j).getFunctionID())) {
                                listFunctionsHomeScreen.add(new HomeScreenCardViewModel(R.drawable.ic_function, listFunctions.get(j).getFunctionName()));
                                listFunctionsOfCurrentUser.add(listFunctions.get(j));
                            }
                        }
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

    private ArrayList<Function> getDataFunctionsForCurrentUser() {
        // final ArrayList<Function> listData = new ArrayList<Function>();
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
                    }
                } else {
                    Toast.makeText(getActivity(), "Không hiển thị tên chức năng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return listFunctions;
    }

    //Display list functions of users
    private void displayListFunctionOfUsersAtHomePage() {

        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        homeScreenrecyclerViewFunctions.setLayoutManager(layoutManager);

        homeScreenrecyclerViewFunctions.setAdapter(adapter);

        //Set event OnItemClickListener
        adapter.setOnItemClickListener(new HomeScreenRecycleViewFunctionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String activityClass = listFunctionsOfCurrentUser.get(position).getPackageClass() + "." + listFunctionsOfCurrentUser.get(position).getActivityClass();
                //Toast.makeText(getActivity(), activityClass, Toast.LENGTH_LONG).show();
                try {
                    Class<?> myClass = Class.forName(activityClass);
                    Intent intent = new Intent(getActivity(), myClass);

                    ListRoomsActivity.FUNCTIONNAME = listFunctionsOfCurrentUser.get(position).getActivityClass();

                    startActivity(intent);

                } catch (ClassNotFoundException ignored) {
                    Toast.makeText(getActivity(), "Chức năng đang được phát triển", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
