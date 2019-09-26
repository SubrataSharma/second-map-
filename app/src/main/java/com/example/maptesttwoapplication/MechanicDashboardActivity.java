package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MechanicDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);
    }

    public void gobackButton(View view) {
        startActivity(new Intent(this,MapsActivity.class));
    }
}
