package com.example.maptesttwoapplication.Delivery_boy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maptesttwoapplication.MapsActivity;
import com.example.maptesttwoapplication.Model_java_class.DeliveryBoyData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DeliveryboyLoginActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboy_login);
    }

    public void deliveryBoyLoginSubmitButton(View view) {
        EditText login_pass = findViewById(R.id.mechanic_login_pass);
        String txt_password = login_pass.getText().toString();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();
        if (TextUtils.isEmpty(txt_password)) {
            Toast.makeText(this, "please enter your PIN", Toast.LENGTH_SHORT).show();

        }
        else {
            final long userPin = Long.parseLong(txt_password);
            DocumentReference locationRef = db.collection("Delivery_Boy_registration").document(userid);
            locationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                    assert documentSnapshot != null;
                    if(documentSnapshot.exists()){
                        DeliveryBoyData deliveryBoyData = documentSnapshot.toObject(DeliveryBoyData.class);

                        assert deliveryBoyData != null;
                        if (userPin == deliveryBoyData.getPin()) {
                            Intent intent = new Intent(DeliveryboyLoginActivity.this, DeliveryboyDashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(DeliveryboyLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {

                            Toast.makeText(DeliveryboyLoginActivity.this, "Authentication failed! incorrect PIN", Toast.LENGTH_SHORT).show();

                        }
                    }else {
                        Toast.makeText(DeliveryboyLoginActivity.this, "please register and get your PIN", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    public void deliveryBoySignUpOption(View view) {
        startActivity(new Intent(this, DeliveryboyRegisterActivity.class));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
