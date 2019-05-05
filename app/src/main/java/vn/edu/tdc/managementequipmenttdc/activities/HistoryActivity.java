package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.data_adapter.DisplayListNotifycationRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_adapter.HistoryRecycleViewAdapter;
import vn.edu.tdc.managementequipmenttdc.data_models.DisplayListNotifycationCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Equipment;
import vn.edu.tdc.managementequipmenttdc.data_models.ListEquipmentCardViewModel;
import vn.edu.tdc.managementequipmenttdc.data_models.Log;
import vn.edu.tdc.managementequipmenttdc.data_models.RepairDiary;
import vn.edu.tdc.managementequipmenttdc.tools.User_Provider;

public class HistoryActivity extends AppCompatActivity {

    //Display list notifycation
    private Vector<DisplayListNotifycationCardViewModel> list_displayHistoryCardViewModels = new Vector<DisplayListNotifycationCardViewModel>();
    RecyclerView historyRecycleView;
    private ArrayList<Log> listHistoryOfUser = new ArrayList<Log>();
    private ArrayList<Log> listHistorySearchOfUser = new ArrayList<Log>();

    private ProgressBar progressBarLoading;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayoutContainSearch;
    private EditText edtSearch;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        getSupportActionBar().setTitle("Lịch sử");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        progressBarLoading = findViewById(R.id.historyProgressBar);
        historyRecycleView = findViewById(R.id.historyRecycleView);
        swipeRefreshLayout = findViewById(R.id.historySwipeRefresh);
        linearLayoutContainSearch = findViewById(R.id.historyLinearlayoutContainSearch);
        edtSearch = findViewById(R.id.historyEdtSearch);

        getDataHistoryOfUser();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBarLoading.setVisibility(View.GONE);
        historyRecycleView.setVisibility(View.VISIBLE);
    }

    private void refreshList() {
        list_displayHistoryCardViewModels.clear();
        listHistoryOfUser.clear();
        getDataHistoryOfUser();
        swipeRefreshLayout.setRefreshing(false);
    }

    //Gan layout menu vua tao(menu_layout) vao menu cha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Gan layout menu vua tao vao menu
        getMenuInflater().inflate(R.menu.menu_search, menu);//Hien thi ra man hinh co menu tren thanh cong cu
        return super.onCreateOptionsMenu(menu);
    }

    //Xu ly su kien cho item trong menu khi click vao item nao do trong menu
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int indexItem = item.getItemId();//Tra ve vi tri cua item duoc click
        //Kiem tra xem da click vao item nào
        switch (indexItem) {
            case R.id.menu_item_search: //Xu ly item xoa
                linearLayoutContainSearch.setVisibility(View.VISIBLE);
                historyRecycleView.setVisibility(View.GONE);
                edtSearch.requestFocus();

                //Display softkey
                InputMethodManager imgr = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(edtSearch, 0);
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                //Change symbol for softkey
                edtSearch.setInputType(InputType.TYPE_CLASS_TEXT);
                edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

                edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            performSearch();

                            String contentSearch = edtSearch.getText().toString().trim();
                            if (contentSearch.isEmpty()) {
                                Toast.makeText(HistoryActivity.this, "Hãy nhập nội dung tìm kiếm", Toast.LENGTH_SHORT).show();
                                edtSearch.requestFocus();
                                return false;
                            }

                            list_displayHistoryCardViewModels.clear();
                            listHistoryOfUser.clear();

                            int soThuTu = 1;
                            for (Log log : listHistorySearchOfUser) {
                                if (log.getDateManipulation().contains(contentSearch)) {
                                    listHistoryOfUser.add(log);
                                    list_displayHistoryCardViewModels.add(new DisplayListNotifycationCardViewModel(soThuTu++ + ". " + log.getManipulation(), log.getDateManipulation()));
                                }
                            }

                            if (list_displayHistoryCardViewModels.size() < 1) {
                                Toast.makeText(HistoryActivity.this, "Bạn không thao tác trong thời gian này", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            historyRecycleView.setVisibility(View.VISIBLE);
                            linearLayoutContainSearch.setVisibility(View.GONE);
                            edtSearch.setText("");
                            displayHistoryManipulationOfUser();
                            return true;
                        }
                        return false;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void performSearch() {
        edtSearch.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
    }

    private void getDataHistoryOfUser() {
        progressBarLoading.setVisibility(View.VISIBLE);
        historyRecycleView.setVisibility(View.GONE);
        Query query = databaseReference.child("log").orderByChild("userID").equalTo(firebaseAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarLoading.setVisibility(View.GONE);
                historyRecycleView.setVisibility(View.VISIBLE);
                if (dataSnapshot.exists()) {
                    int soThuTu = 1;
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Log log = item.getValue(Log.class);
                        list_displayHistoryCardViewModels.add(new DisplayListNotifycationCardViewModel(soThuTu ++ + ". " + log.getManipulation(), log.getDateManipulation()));
                        listHistoryOfUser.add(log);
                    }

                    listHistorySearchOfUser.addAll(listHistoryOfUser);
                    displayHistoryManipulationOfUser();
                } else {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Hien thi lich su
    private void displayHistoryManipulationOfUser() {
        //Setup RecycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycleView.setLayoutManager(layoutManager);

        HistoryRecycleViewAdapter adapter = new HistoryRecycleViewAdapter(R.layout.card_view_history_layout, list_displayHistoryCardViewModels);
        historyRecycleView.setAdapter(adapter);
    }
}
