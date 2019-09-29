package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Empty;

import java.util.Objects;

public class MechanicRegistrationSecondActivity extends AppCompatActivity {

    EditText company_name,register_name,contact_no,company_email, pass, con_pass,service;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration_second);
    }


    public void submitButton(View view) {
        company_name=findViewById(R.id.mechanic_company_name);
        register_name=findViewById(R.id.mechanic_register_name);
        contact_no=findViewById(R.id.mechanic_contact_no);
        company_email=findViewById(R.id.mechanic_email);
        pass=findViewById(R.id.mechanic_pass);
        con_pass=findViewById(R.id.mechanic_con_pass);
        service=findViewById(R.id.mechanic_service);
        String company= company_name.getText().toString();
        String registerName= register_name.getText().toString();
        String contactNo= contact_no.getText().toString();
        String email= company_email.getText().toString();
        String password= pass.getText().toString();
        String conPassword= con_pass.getText().toString();
        String serviceType= service.getText().toString();
         double lat =  getIntent().getExtras().getDouble("latitude");
         double lon =  getIntent().getExtras().getDouble("longitude");

         if(company.isEmpty()||(lat==0)||(lon==0)||registerName.isEmpty()||contactNo.isEmpty()||email.isEmpty()
                 ||password.isEmpty()||conPassword.isEmpty()||serviceType.isEmpty()){
             Toast.makeText(this, "All fields are requires", Toast.LENGTH_SHORT).show();
         }else if(password.length() < 6){
             Toast.makeText(this, "Password must be at least 6 digits", Toast.LENGTH_SHORT).show();
         }else if(!password.equals(conPassword)) {
             Toast.makeText(this, "please enter the same password again", Toast.LENGTH_SHORT).show();
             //register(txt_username,txt_email,txt_password);
         }else {
             firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
             assert firebaseUser != null;
             if(Objects.equals(firebaseUser.getEmail(), email)){
                 String userid = firebaseUser.getUid();

                 DocumentReference locationRef =db.collection("company_registration").document(userid);
                 MapLocation mapLocation = new MapLocation(userid,lat, lon, company,
                         registerName,contactNo,email,serviceType);
                 locationRef.set(mapLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Intent intent = new Intent(MechanicRegistrationSecondActivity.this,MapsActivity.class);
                             Toast.makeText(MechanicRegistrationSecondActivity.this,"successfully registered",Toast.LENGTH_LONG).show();
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(intent);
                             finish();
                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MechanicRegistrationSecondActivity.this,"you can't register",Toast.LENGTH_LONG).show();
                         finish();
                     }
                 });
             }else{
                 mechanicRegister(email, password, lat, lon, company, registerName, contactNo, serviceType);
             }





         }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MechanicRegistrationSecondActivity.this,MechanicRegistrationActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void previousButton(View view) {
        startActivity(new Intent(MechanicRegistrationSecondActivity.this,MechanicRegistrationActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void mechanicRegister(final String email, String password, final double lat, final double lon, final String company,
                                 final String registerName, final String contactNo, final String serviceType) {

        auth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){

                           FirebaseUser firebaseUser = auth.getCurrentUser();
                           assert firebaseUser != null;
                           String userid = firebaseUser.getUid();

                           DocumentReference locationRef =db.collection("company_registration").document(userid);
                           MapLocation mapLocation = new MapLocation(userid,lat, lon, company,
                                   registerName,contactNo,email,serviceType);
                           locationRef.set(mapLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Intent intent = new Intent(MechanicRegistrationSecondActivity.this,MapsActivity.class);
                                       Toast.makeText(MechanicRegistrationSecondActivity.this,"successfully registered",Toast.LENGTH_LONG).show();
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                       startActivity(intent);
                                       finish();
                                   }
                               }
                           });

                       }else {
                           Toast.makeText(MechanicRegistrationSecondActivity.this,"you can't register",Toast.LENGTH_LONG).show();
                           finish();
                       }
                   }
               });

    }
}
