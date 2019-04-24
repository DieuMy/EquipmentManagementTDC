package vn.edu.tdc.managementequipmenttdc.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class DetailMyWorkActivity extends AppCompatActivity {
    private LinearLayout detailReportLinnearLayoutLoiDaKhacPhuc;
    private boolean statusReceive ;
    private FloatingActionButton floatingActionButtonTiepNhan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_mywork_screen_flagment);

        getSupportActionBar().setTitle("Chi tiết");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Gets view from layout
        detailReportLinnearLayoutLoiDaKhacPhuc = findViewById(R.id.detailReportLinnearLayoutLoiDaKhacPhuc);
        floatingActionButtonTiepNhan = findViewById(R.id.myWWorkFloatButtonViewListMalfunction);

        checkUI();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //Gan layout menu vua tao(menu_layout) vao menu cha
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        //Gan layout menu vua tao vao menu
        getMenuInflater().inflate(R.menu.menu_complete, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        if (statusReceive == false) {
            MenuItem item = menu.findItem(R.id.menu_item_complete);
            item.setVisible(false);

            floatingActionButtonTiepNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Bat nut hoan tat
                    detailReportLinnearLayoutLoiDaKhacPhuc.setVisibility(View.VISIBLE);
                    MenuItem item = menu.findItem(R.id.menu_item_complete);
                    item.setVisible(true);
                    floatingActionButtonTiepNhan.setVisibility(View.GONE);
                }
            });

        } else {
            floatingActionButtonTiepNhan.setVisibility(View.GONE);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_complete: //Xu ly item xoa
                Toast.makeText(this, "Yeah", Toast.LENGTH_SHORT).show();
                //Thay doi trang thai cua loi tren co so du lieu
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkUI() {
        statusReceive = false;
        //Kiem tra trang thai cua loi(da tiep nhan hay chua?: neu tiep nhan roi thi an button tiep nhan,
        // hien thi edt nhap noi dung loi, hien thi tool bar right xac nhan hoan tat sua chua)
    }
}
