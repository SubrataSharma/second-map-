package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserRegisterActivity extends AppCompatActivity {
    private EditText Username,userEmail,Pass,conPass;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }

    public void userRegisterSubmitButton(View view) {
        Username=findViewById(R.id.user_registration_name);
        userEmail=findViewById(R.id.user_registration_email);
        Pass=findViewById(R.id.user_registration_pass);
        conPass=findViewById(R.id.user_registration_con_pass);

        String txt_username = Username.getText().toString();
        String txt_email = userEmail.getText().toString();
        String txt_pass = Pass.getText().toString();
        String txt_conpass = conPass.getText().toString();

        if(TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_pass)||TextUtils.isEmpty(txt_conpass)){
            Toast.makeText(UserRegisterActivity.this,"All fields are requireds ",Toast.LENGTH_LONG).show();
        }else if(txt_pass.length() < 6){
            Toast.makeText(UserRegisterActivity.this, "Password must be atleast 6 digits", Toast.LENGTH_SHORT).show();
        }else if(!txt_pass.equals(txt_conpass)) {
            Toast.makeText(UserRegisterActivity.this, "please enter the same password again", Toast.LENGTH_SHORT).show();
            //register(txt_username,txt_email,txt_password);
        }else {
            register(txt_username,txt_email,txt_pass);
            startActivity(new Intent(this,MapsActivity.class));
        }



    }

    public void register(final String username, final String email, String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            DocumentReference locationRef =db.collection("user_registration_email").document(userid);
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", email);


                            locationRef.set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(UserRegisterActivity.this,MapsActivity.class);
                                        Toast.makeText(UserRegisterActivity.this,"successfully registered",Toast.LENGTH_LONG).show();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(UserRegisterActivity.this,"you can't register",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });


    }
}
