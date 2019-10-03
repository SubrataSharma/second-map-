package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.example.maptesttwoapplication.Model_java_class.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MechanicLoginActivity extends AppCompatActivity {


    FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_login);



    }


    public void mechanicLoginSubmitButton(View view) {
        EditText login_pass = findViewById(R.id.mechanic_login_pass);
        String txt_password = login_pass.getText().toString();
        final int userPin = Integer.parseInt(txt_password);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        DocumentReference locationRef = db.collection("company_registration").document(userid);
        locationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);


                assert mapLocation != null;
                if (userPin == mapLocation.getPin()) {
                    Intent intent = new Intent(MechanicLoginActivity.this, MechanicDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(MechanicLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Toast.makeText(MechanicLoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void mechanicSignUpOption(View view) {
        startActivity(new Intent(MechanicLoginActivity.this, MechanicRegistrationActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MechanicLoginActivity.this, MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
