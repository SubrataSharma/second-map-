package com.example.maptesttwoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

import javax.annotation.Nullable;

public class ServicesActivity extends AppCompatActivity {
    TextView textView;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String User_data ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        textView=findViewById(R.id.service_test);



    }

    @Override
    protected void onStart() {
        super.onStart();
        final String value =  getIntent().getExtras().getString("ID");
        DocumentReference locationRef =db.collection("company_registration").document(value);
        locationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    return;
                }
                MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);

                double lat = mapLocation.getLatitude();
                double lon = mapLocation.getLongitude();
                String company = mapLocation.getCompany();
                String registerName = mapLocation.getRegisterName();
                String contactNo = mapLocation.getContactNo();
                String email = mapLocation.getEmail();
                String serviceType = mapLocation.getServiceType();


                User_data = "latitude:"+lat+"\n\n"+
                        "longitude:"+lon+"\n\n"+
                        "company name:"+company+"\n\n"+
                        "registerName:"+registerName+"\n\n"+
                        "contactNo:"+contactNo+"\n\n"+
                        "Email:"+email+"\n\n"+
                        "service:"+serviceType+"\n\n";
                textView.setText(User_data);




            }
        });
    }

}
