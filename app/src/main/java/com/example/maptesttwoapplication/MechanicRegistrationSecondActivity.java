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

    EditText company_name;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference locationRef =db.collection("company_registration_email");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration_second);
    }


    public void submitButton(View view) {
        company_name=findViewById(R.id.edit_text_2);
        String company= company_name.getText().toString();
         double lat =  getIntent().getExtras().getDouble("latitude");
         double lon =  getIntent().getExtras().getDouble("longitude");

         if(company.isEmpty()||(lat==0)||(lon==0)){
             Toast.makeText(this, "No valid location found", Toast.LENGTH_SHORT).show();
         }else {
             MapLocation mapLocation = new MapLocation(lat, lon, company);


        locationRef.add(mapLocation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MechanicRegistrationSecondActivity.this, "location added", Toast.LENGTH_SHORT).show();

            }
        });
         }
    }

    public void previousButton(View view) {
        onBackPressed();
    }
}
