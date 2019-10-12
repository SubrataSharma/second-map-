package com.example.maptesttwoapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Adapter.CurrentServiceAdapter;
import com.example.maptesttwoapplication.Adapter.UserActivityAdapter;
import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment {

    RecyclerView recyclerView;

    private UserActivityAdapter userActivityAdapter;
    private List<ServiceListData> serviceList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activityfragment,container,false);

        recyclerView = view.findViewById(R.id.activity_fragment_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        serviceList = new ArrayList<>();

        loadRecyclerViewData();

        return view;
    }

    private void loadRecyclerViewData() {

        DocumentReference serviceLocationRef =db.collection("user_activity")
                .document(firebaseUser.getUid());

        serviceLocationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                assert documentSnapshot != null;
                ServiceListData serviceListData = documentSnapshot.toObject(ServiceListData.class);
                serviceList.add(serviceListData);


                userActivityAdapter = new UserActivityAdapter(serviceList, getContext());
                recyclerView.setAdapter(userActivityAdapter);
            }
        });
    }
}
