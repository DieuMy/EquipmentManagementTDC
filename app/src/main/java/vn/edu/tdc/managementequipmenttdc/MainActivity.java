package vn.edu.tdc.managementequipmenttdc;

import android.os.Bundle;

import java.util.Vector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.tdc.managementequipmenttdc.data_adapter.AreaBuildingRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HomeScreenRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListRoomRecycleAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.AreaBuildingCardviewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListRoomCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_adapter.ListEquipmentRecycleViewFunctionAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;

public class MainActivity extends AppCompatActivity {

    //    Home Screen
    private Vector<HomeScreenCardViewModel> homeScreenListFunctions;
    RecyclerView homeScreenrecyclerViewFunctions;

    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayListNotifycationCardViewModels;
    RecyclerView displayListNotifycationRecycleView;

    //Display list area
    private Vector<AreaBuildingCardviewModel> list_areaBuildingCardviewModels;
    RecyclerView areaBuildingRecycleView;

    //Display room
    private Vector<ListRoomCardViewModel> listRoomCardViewModels;
    RecyclerView listRoomRecycleView;
    //List Equipment
    private Vector<ListEquipmentCardViewModel> listEquipmentCardViewModels;
    RecyclerView listequipmentrecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_report_malfuntion_screen_flagment);


    }

    //Hien thi danh sach cac phong
    private void displayListRooms() {
        //Get views layout
        listRoomRecycleView = (RecyclerView) findViewById(R.id.listRoomRecycleView);

        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
        //Khoi tao gia tri
        listRoomCardViewModels = new Vector<ListRoomCardViewModel>();
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
        listRoomCardViewModels.add(new ListRoomCardViewModel("B002A"));
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

    private void displayListFunctionOfUsersAtHomePage() {
        //Get views layout
        homeScreenrecyclerViewFunctions = (RecyclerView) findViewById(R.id.homeScreenRecycleViewFunctions);

        //Tat ca khai bao het, rieng noi dung list nayf thif add vaof de test sau nayf thay baang APIs
        //Khoi tao gia tri
        homeScreenListFunctions = new Vector<HomeScreenCardViewModel>();
        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Tra cứu phòng máy"));
        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Báo cáo sự cố"));
        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Nhật ký"));
        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Ghi chú"));
        homeScreenListFunctions.add(new HomeScreenCardViewModel(R.drawable.teamwork, "Đăng xuất"));

        //Setup RecycleView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//chia recycleview thanh cot
        homeScreenrecyclerViewFunctions.setLayoutManager(gridLayoutManager);
        HomeScreenRecycleViewFunctionAdapter adapter = new HomeScreenRecycleViewFunctionAdapter(R.layout.card_view_home_screen_layout, homeScreenListFunctions);
        homeScreenrecyclerViewFunctions.setAdapter(adapter);

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
}
