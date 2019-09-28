package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Toast;


import com.example.maptesttwoapplication.Fragment.ActivityFragment;
import com.example.maptesttwoapplication.Fragment.BuyerFragment;
import com.example.maptesttwoapplication.Fragment.MapFragment;
import com.example.maptesttwoapplication.Fragment.SellerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MapsActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {



    //navigation
    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // bottom navigation


        // navigation code
        toolbar = findViewById(R.id.toolbar);
        drawerLayout =findViewById(R.id.drawer);
        NavigationView navigationView= findViewById(R.id.work_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // navigation code end

        //bottom navigation start
        bottomNavigationView=findViewById(R.id.work_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.work_fragment,new MapFragment()).commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch(item.getItemId()){
                        case R.id.nav_map:
                            selectedFragment = new MapFragment();
                            break;
                        case R.id.nav_buyer:
                            selectedFragment = new BuyerFragment();
                            break;
                        case R.id.nav_seller:
                            selectedFragment = new SellerFragment();
                            break;
                        case R.id.nav_activity:
                            selectedFragment = new ActivityFragment();
                            break;


                    }
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.work_fragment,selectedFragment).commit();
                    return true;
                }
            };


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.option_mechanic:
                Intent intent=new Intent(MapsActivity.this,MechanicLoginActivity.class);
                startActivity(intent);
                finish();


                break;
            case R.id.option_delivery_boy:
                Toast.makeText(this, "this option not available", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,LoginActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
                break;
            default:
                Toast.makeText(this, "not selected", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    return true;
    }


}
