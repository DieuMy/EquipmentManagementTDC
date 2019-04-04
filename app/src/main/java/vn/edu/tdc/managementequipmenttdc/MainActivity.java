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
import vn.edu.tdc.managementequipmenttdc.data_models.HomeScreenCardViewModel;

public class MainActivity extends AppCompatActivity {

//    Home Screen
    private Vector<HomeScreenCardViewModel> homeScreenListFunctions;
    RecyclerView homeScreenrecyclerViewFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_flagment);


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
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(this, 3);//chia recycleview thanh cot
        homeScreenrecyclerViewFunctions.setLayoutManager(gridLayoutManager);
        HomeScreenRecycleViewFunctionAdapter adapter = new HomeScreenRecycleViewFunctionAdapter(R.layout.card_view_home_screen_layout, homeScreenListFunctions);
        homeScreenrecyclerViewFunctions.setAdapter(adapter);

    }
}
