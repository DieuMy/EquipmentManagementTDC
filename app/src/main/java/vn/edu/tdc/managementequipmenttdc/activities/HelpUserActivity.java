package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HelpUserExpanableListViewAdapter;

public class HelpUserActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private HelpUserExpanableListViewAdapter expanableListViewAdapter;
    private List<String> listQuestion;
    private HashMap<String, List<String>> listAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_users_layout);

        getSupportActionBar().setTitle("Trợ giúp");

        expandableListView = findViewById(R.id.helpUsersExpandableListView);
        initialData();
        expanableListViewAdapter = new HelpUserExpanableListViewAdapter(this, listQuestion, listAnswer);
        expandableListView.setAdapter(expanableListViewAdapter);
    }


    //Gan layout menu vua tao(menu_layout) vao menu cha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Gan layout menu vua tao vao menu
        getMenuInflater().inflate(R.menu.menu_close, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_close: //Xu ly item xoa
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initialData(){
        listQuestion = new ArrayList<>();
        listAnswer = new HashMap<>();

        listQuestion.add("1. Tại sao tôi lại không đăng nhập được?");
        listQuestion.add("2. Sự cố đã được báo cáo nhưng chưa sửa chữa tôi có nên tiếp tục báo cáo không?");
        listQuestion.add("3. Làm sao để lấy lại mật khẩu?");
        listQuestion.add("4. Tại sao tài khoản của tôi lại bị khóa?");
        listQuestion.add("5. Muốn báo cáo sự cố mới phải làm như thế nào?");

        List<String> listQuestion_1 = new ArrayList<>();
        listQuestion_1.add("1. Kiểm tra internet đã được kết nối chưa");
        listQuestion_1.add("2. Kiểm tra tài khoản và mật khẩu đăng nhập đã đúng chưa");

        List<String> listQuestion_2 = new ArrayList<>();
        listQuestion_2.add("Không nên vì nhân viên kỹ thuật đang xử lý sự cố trước đó.");

        List<String> listQuestion_3 = new ArrayList<>();
        listQuestion_3.add("Cách 1: Nhấn nút quên mật khẩu ở màn hình đăng nhập. " +
                "Ứng dụng sẽ gửi cho bạn mật khẩu mới qua số điện thoại hoặc email bạn đã đăng ký");
        listQuestion_3.add("Cách 2: Liên hệ phòng kỹ thuật để cấp lại mật khẩu");

        List<String> listQuestion_4 = new ArrayList<>();
        listQuestion_4.add("1. Báo cáo sai phạm nhiều lần");
        listQuestion_4.add("2. Cố ý báo cáo giả");
        listQuestion_4.add("3. Cố ý làm rối loạn thông tin");

        List<String> listQuestion_5 = new ArrayList<>();
        listQuestion_5.add("1. Chọn chức năng báo cáo sự cố trên màn hình trang chủ");
        listQuestion_5.add("2. Ở màn hình hiển thị danh sách tất cả các phòng máy bạn hãy chọn phòng mà bạn cần báo cáo.\n " +
                "Hoặc bạn có thể dùng thanh tìm kiếm ở góc phải màn hình để tìm kiêm snhanh phòng mà bạn cần báo cáo");
        listQuestion_5.add("3. Chọn loại thiết bị trong phòng bạn cần báo cáo");
        listQuestion_5.add("4. Chọn thiết bị bạn cần báo cáo");
        listQuestion_5.add("Hoặc: ");
        listQuestion_5.add("1. Chọn chức năng tra cứu phòng máy trên màn hình trang chủ");
        listQuestion_5.add("2. Chọn khu vực/ tòa nhà bạn cần báo cáo");
        listQuestion_5.add("3. Chọn loại thiết bị trong phòng bạn cần báo cáo");
        listQuestion_5.add("4. Chọn thiết bị bạn cần báo cáo");

        listAnswer.put(listQuestion.get(0), listQuestion_1);
        listAnswer.put(listQuestion.get(1), listQuestion_2);
        listAnswer.put(listQuestion.get(2), listQuestion_3);
        listAnswer.put(listQuestion.get(3), listQuestion_4);
        listAnswer.put(listQuestion.get(4), listQuestion_5);
    }
}
