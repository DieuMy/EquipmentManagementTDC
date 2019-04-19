package vn.edu.tdc.managementequipmenttdc.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.edu.tdc.managementequipmenttdc.R;
import vn.edu.tdc.managementequipmenttdc.fragments.HomePageFragment;
import vn.edu.tdc.managementequipmenttdc.fragments.NotificationPageFragment;
import vn.edu.tdc.managementequipmenttdc.fragments.PersonalPageFragment;
import vn.edu.tdc.managementequipmenttdc.tools.ConnectionDetector;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_Activity extends AppCompatActivity {
    ConnectionDetector connectionDetector;
    private SwipeRefreshLayout swipeRefreshLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navBottom_home:
                    fragment = new HomePageFragment();
                    break;
                case R.id.navBottom_notification:
                    fragment = new NotificationPageFragment();
                    break;
                case R.id.navBottom_personal:
                    fragment = new PersonalPageFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check internet
        connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnected()) {
            setContentView(R.layout.activity_main_layout);

            //Gets view from layout
            BottomNavigationView bottomNavView = findViewById(R.id.bottom_navigation);
            bottomNavView.setOnNavigationItemSelectedListener(navListener);

            //Khoi dong man hinh home
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePageFragment()).commit();
        } else {
            setContentView(R.layout.login_flagment);
            final Dialog dialog = new Dialog(Main_Activity.this);
            dialog.setContentView(R.layout.popup_notifycation_layout);

            ImageView imgCloseDialog = dialog.findViewById(R.id.popup_close_dialog);
            Button btnOKDialog = dialog.findViewById(R.id.popup_dialog_buttonOK);
            TextView txtNofication = dialog.findViewById(R.id.popup_dialog_notification);

            txtNofication.setText("Vui lòng kết nối internet");

            //Processing event for close dialog
            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            });

            btnOKDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    finish();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }

    }


}
