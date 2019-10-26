package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;


public class UserRegisterActivity extends AppCompatActivity {
    private EditText Username,userEmail,Pass,conPass,contact_no;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;


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
        contact_no=findViewById(R.id.user_contact_no);

        String txt_username = Username.getText().toString();
        String txt_email = userEmail.getText().toString();
        String txt_pass = Pass.getText().toString();
        String txt_conpass = conPass.getText().toString();
        String contactNo = contact_no.getText().toString();

        if(TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(contactNo)
                ||TextUtils.isEmpty(txt_pass)||TextUtils.isEmpty(txt_conpass)){
            Toast.makeText(UserRegisterActivity.this,"All fields are requires ",Toast.LENGTH_LONG).show();
        }else if(txt_pass.length() < 6){
            Toast.makeText(UserRegisterActivity.this, "Password must be at least 6 digits", Toast.LENGTH_SHORT).show();
        }else if(!txt_pass.equals(txt_conpass)) {
            Toast.makeText(UserRegisterActivity.this, "please enter the same password again", Toast.LENGTH_SHORT).show();
            //register(txt_username,txt_email,txt_password);
        }else {

            register(txt_username,txt_email,txt_pass,contactNo);

        }



    }

    public void register(final String username, final String email, String password, final String contactNo){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UserRegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            String dvicetoken = FirebaseInstanceId.getInstance().getToken();

                            DocumentReference locationRef =db.collection("user_registration").document(userid);

                            UserData userData=new UserData(userid,username,email,contactNo);


                            locationRef.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "you can't register in this email", Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}
