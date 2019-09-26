package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }

    public void userRegisterSubmitButton(View view) {
        startActivity(new Intent(this,MapsActivity.class));

    }
}
