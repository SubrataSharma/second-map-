package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Empty;

public class MechanicRegistrationSecondActivity extends AppCompatActivity {

    EditText company_name,register_name,contact_no,company_email, pass, con_pass,service;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference locationRef =db.collection("company_registration_email");
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
             startActivity(new Intent(this,MapsActivity.class));

             MapLocation mapLocation = new MapLocation(lat, lon, company,
                     registerName,contactNo,email,serviceType);


        locationRef.add(mapLocation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MechanicRegistrationSecondActivity.this, "location added", Toast.LENGTH_SHORT).show();

            }
        });
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

    public void mechanicRegister(String email,String password,double lat,double lon,String company,
                                String registerName,String contactNo,String serviceType) {
    }
}
