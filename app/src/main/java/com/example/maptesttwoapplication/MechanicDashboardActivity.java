package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.maptesttwoapplication.Fragment.CurrentServiceFragment;
import com.example.maptesttwoapplication.Fragment.ServiceRequestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MechanicDashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        bottomNavigationView=findViewById(R.id.mechanic_dashboard_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.mechanic_dashboard_fragment,new ServiceRequestFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;

                    switch(menuItem.getItemId()) {
                        case R.id.nav_service_request:
                            selectedFragment = new ServiceRequestFragment();
                            break;
                        case R.id.nav_current_service:
                            selectedFragment = new CurrentServiceFragment();
                            break;



                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.mechanic_dashboard_fragment,selectedFragment).commit();
                    return true;
                }
            };

}
