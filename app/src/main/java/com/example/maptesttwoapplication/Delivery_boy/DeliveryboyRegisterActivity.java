package com.example.maptesttwoapplication.Delivery_boy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.maptesttwoapplication.MapsActivity;
import com.example.maptesttwoapplication.MechanicRegistrationActivity;
import com.example.maptesttwoapplication.MechanicRegistrationSecondActivity;
import com.example.maptesttwoapplication.Model_java_class.DeliveryBoyData;
import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.example.maptesttwoapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DeliveryboyRegisterActivity extends AppCompatActivity {
    EditText Area_Of_Work,register_name,contact_no;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboy_register);
        progressBar=findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void submitButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Area_Of_Work=findViewById(R.id.mechanic_company_name);
        register_name=findViewById(R.id.mechanic_register_name);
        contact_no=findViewById(R.id.mechanic_contact_no);
        String area_of_work= Area_Of_Work.getText().toString();
        String registerName= register_name.getText().toString();
        String contactNo= contact_no.getText().toString();
        String pin = "1785463129831254";

        if(area_of_work.isEmpty()||registerName.isEmpty()||contactNo.isEmpty()){
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "All fields are requires", Toast.LENGTH_SHORT).show();
        }else {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            assert firebaseUser != null;
            String userid = firebaseUser.getUid();

            DocumentReference locationRef =db.collection("Delivery_Boy_registration").document(userid);
            DeliveryBoyData deliveryBoyData = new DeliveryBoyData(userid,area_of_work,
                    registerName,contactNo,Long.parseLong(pin));
            locationRef.set(deliveryBoyData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(DeliveryboyRegisterActivity.this, MapsActivity.class);
                        Toast.makeText(DeliveryboyRegisterActivity.this,"thanks for registering you will get a call from our customer service",Toast.LENGTH_LONG).show();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(DeliveryboyRegisterActivity.this,"you can't register",Toast.LENGTH_LONG).show();
                    finish();
                }
            });






        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DeliveryboyLoginActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
