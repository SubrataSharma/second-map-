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
import com.example.maptesttwoapplication.Adapter.ServiceRequestAdapter;
import com.example.maptesttwoapplication.Model_java_class.CompanyDealData;
import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CurrentServiceFragment extends Fragment {
    RecyclerView recyclerView;

    private CurrentServiceAdapter currentServiceAdapter;
    private List<ServiceListData> serviceList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_service_fragment,container,false);

        recyclerView = view.findViewById(R.id.current_service_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        serviceList = new ArrayList<>();

        loadRecyclerViewData();
        return view;
    }

    private void loadRecyclerViewData() {
        CollectionReference serviceLocationRef =db.collection("company_deal_details")
                .document("service_list").collection(firebaseUser.getUid());

        serviceLocationRef.orderBy("status", Query.Direction.ASCENDING)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                serviceList.clear();
                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    ServiceListData serviceListData = documentSnapshot.toObject(ServiceListData.class);
                    serviceList.add(serviceListData);
                }
                currentServiceAdapter =new CurrentServiceAdapter(serviceList,getContext());
                recyclerView.setAdapter(currentServiceAdapter);
            }

        });
    }


}
