package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText login_name,login_pass;
    private Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_name=findViewById(R.id.user_login_name);
        login_pass=findViewById(R.id.user_login_pass);

    }

    public void userLoginSubmitButton(View view) {

        startActivity(new Intent(this,UserRegisterActivity.class));
    }
}
