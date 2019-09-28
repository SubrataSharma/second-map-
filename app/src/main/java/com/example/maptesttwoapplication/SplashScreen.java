package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();

        }*/





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(in);
                finish();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {

    }

}
