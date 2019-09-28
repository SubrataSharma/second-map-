package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MechanicLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);
    }

    public void mechanicLoginSubmitButton(View view) {
    }

    public void mechanicSignUpOption(View view) {
        startActivity(new Intent(MechanicLoginActivity.this,MechanicRegistrationActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MechanicLoginActivity.this,MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
