package com.example.maptesttwoapplication.Delivery_boy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.maptesttwoapplication.Adapter.NotificationAdapter;
import com.example.maptesttwoapplication.Adapter.UserActivityAdapter;
import com.example.maptesttwoapplication.MapsActivity;
import com.example.maptesttwoapplication.Model_java_class.DeliveryNotificationData;
import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.example.maptesttwoapplication.ServicesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeliveryboyDashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    private NotificationAdapter notificationAdapter;
    private List<DeliveryNotificationData> deliveryNotificationDataList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboy_dashboard);

        recyclerView = findViewById(R.id.delivery_boy_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        deliveryNotificationDataList = new ArrayList<>();

        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {
        CollectionReference serviceLocationRef =db.collection("notification_activity");

        serviceLocationRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    DeliveryNotificationData deliveryNotificationData = documentSnapshot.toObject(DeliveryNotificationData.class);
                    deliveryNotificationDataList.add(deliveryNotificationData);
                }

                notificationAdapter = new NotificationAdapter(deliveryNotificationDataList,getApplicationContext());
                recyclerView.setAdapter(notificationAdapter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MapsActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
