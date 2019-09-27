package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText login_name,login_pass;

    FirebaseUser firebaseUser;

    private FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //check if user is null
        if(firebaseUser != null){
            Intent intent = new Intent(this,MapsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }



    public void userLoginSubmitButton(View view) {
        login_name=findViewById(R.id.user_login_name);
        login_pass=findViewById(R.id.user_login_pass);
        String txt_email = login_name.getText().toString();
        String txt_password = login_pass.getText().toString();

        if(TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
            Toast.makeText(LoginActivity.this, "fill all fileds", Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(txt_email,txt_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this,MapsActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void signUpOption(View view) {
        startActivity(new Intent(this,UserRegisterActivity.class));
    }
}
