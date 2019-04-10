package vn.edu.tdc.managementequipmenttdc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.tdc.managementequipmenttdc.R;

public class SplashActivity  extends AppCompatActivity {

    private  static  int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);
    }
}