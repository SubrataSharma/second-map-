package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.CompanyDealData;
import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.example.maptesttwoapplication.Model_java_class.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;




import javax.annotation.Nullable;

public class ServicesActivity extends AppCompatActivity {
    TextView companyName,ownerName,ownerContactNo,aboutCompany,serviceProcess,ownerWord;
    String companyId="";
    String clientName="";
    String serviceChoice;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        companyName=findViewById(R.id.service_company_name);
        ownerName=findViewById(R.id.owner_name);
        ownerContactNo=findViewById(R.id.contact_no);
        aboutCompany=findViewById(R.id.about_company);
        serviceProcess=findViewById(R.id.service_process);
        ownerWord=findViewById(R.id.owner_word);




    }

    @Override
    protected void onStart() {
        super.onStart();
        companyId =  getIntent().getExtras().getString("ID");
        serviceChoice =  getIntent().getExtras().getString("SERVICE");
        DocumentReference locationRef =db.collection("company_registration").document(companyId);
        locationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    return;
                }
                MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);


                String company = mapLocation.getCompany();
                String registerName = mapLocation.getRegisterName();
                String contactNo = mapLocation.getContactNo();


                companyName.setText(company);
                ownerName.setText("Owner Name:  "+registerName);
                ownerContactNo.setText("Contact No:  "+contactNo);
                aboutCompany.setText(mapLocation.getAbout_company());
                serviceProcess.setText(mapLocation.getService_process());
                ownerWord.setText(mapLocation.getOwner_word());
                DocumentReference locationRef =db.collection("user_registration").document(firebaseUser.getUid());
                locationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                        if(e !=null){
                            return;
                        }
                        assert documentSnapshot != null;
                        UserData userData = documentSnapshot.toObject(UserData.class);
                        clientName=userData.getUserName();
                       // userName.setText(clientName);

                    }
                });





            }
        });
    }

    public void addRequestButton(View view) {
        DocumentReference serviceLocationRef =db.collection("company_deal_details").document("pending_request").collection(companyId).document(firebaseUser.getUid());


        CompanyDealData companyDealData=new CompanyDealData(clientName,serviceChoice,firebaseUser.getEmail(),firebaseUser.getUid());
        serviceLocationRef.set(companyDealData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ServicesActivity.this, "service request added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ServicesActivity.this,MapsActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ServicesActivity.this,MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void cancelButton(View view) {
        startActivity(new Intent(ServicesActivity.this,MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
