package vn.edu.tdc.managementequipmenttdc;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.Vector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HomeScreenRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListEquipmentRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;

public class MainActivity extends AppCompatActivity {

//    Home Screen
//    private Vector<HomeScreenCardViewModel> homeScreenListFunctions;
//    RecyclerView homeScreenrecyclerViewFunctions;
//List Equipment
    private Vector<ListEquipmentCardViewModel> listEquipmentCardViewModels;
    RecyclerView listequipmentrecycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_equipment_screen_flagment);

        ////////////////////////////// Start Home Screen////////////////////////////////////////////////

//        //Get views layout
//        homeScreenrecyclerViewFunctions = (RecyclerView) findViewById(R.id.homeScreenRecycleViewFunctions);
//
//
//        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
//        //Khoi tao gia tri
//        homeScreenListFunctions = new Vector<HomeScreenCardViewModel>();
//        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Tra cứu phòng máy"));
//        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Báo cáo sự cố"));
//        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Nhật ký"));
//        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Ghi chú"));
//        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Đăng xuất"));
//
//        //Setup RecycleView
//        GridLayoutManager gridLayoutManager =  new GridLayoutManager(this, 3);//chia recycleview thanh cot
//        homeScreenrecyclerViewFunctions.setLayoutManager(gridLayoutManager);
//        HomeScreenRecycleViewFunctionAdapter adapter = new HomeScreenRecycleViewFunctionAdapter(R.layout.card_view_home_screen_layout, homeScreenListFunctions);
//        homeScreenrecyclerViewFunctions.setAdapter(adapter);

        //////////////////////////////End Home Screen///////////////////////////////////////

        ///////////////////////////////Start List Equipment Screen/////////////////////////
        listequipmentrecycleview =(RecyclerView) findViewById(R.id.ListEquipmentScreenRecycleViewFunction);
        listEquipmentCardViewModels = new Vector<ListEquipmentCardViewModel>();
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy tính"));
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy lạnh"));
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Đèn"));
        listEquipmentCardViewModels.add(new ListEquipmentCardViewModel("Máy chiếu"));

        GridLayoutManager gridLayoutManager =  new GridLayoutManager(this, 1);//chia recycleview thanh cot
        listequipmentrecycleview.setLayoutManager(gridLayoutManager);
        ListEquipmentRecycleViewFunctionAdapter adapter = new ListEquipmentRecycleViewFunctionAdapter(R.layout.card_view_list_equipment_screen_layout, listEquipmentCardViewModels);
        listequipmentrecycleview.setAdapter(adapter);
        ///////////////////////////////End List Equipment Screen/////////////////////////

    }
}
