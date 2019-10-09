package com.example.maptesttwoapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Adapter.ServiceRequestAdapter;
import com.example.maptesttwoapplication.Model_java_class.CompanyDealData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestFragment extends Fragment {
    RecyclerView recyclerView;

    private ServiceRequestAdapter serviceRequestAdapter;
    private List<CompanyDealData> requestList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_request_fragment,container,false);

        recyclerView = view.findViewById(R.id.service_request_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        requestList = new ArrayList<>();

        loadRecyclerViewData();

        return view;
    }

    private void loadRecyclerViewData() {
       CollectionReference serviceLocationRef =db.collection("company_deal_details").document("pending_request").collection(firebaseUser.getUid());

        serviceLocationRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    CompanyDealData companyDealData = documentSnapshot.toObject(CompanyDealData.class);
                    requestList.add(companyDealData);
                }
                serviceRequestAdapter =new ServiceRequestAdapter(requestList,getContext());
                recyclerView.setAdapter(serviceRequestAdapter);
            }

        });
    }



}
