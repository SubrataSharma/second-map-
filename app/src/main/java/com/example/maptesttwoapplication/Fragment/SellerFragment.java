package com.example.maptesttwoapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Adapter.SellerAdapter;
import com.example.maptesttwoapplication.Adapter.UserActivityAdapter;
import com.example.maptesttwoapplication.MechanicLoginActivity;
import com.example.maptesttwoapplication.MechanicRegistrationActivity;
import com.example.maptesttwoapplication.Model_java_class.SellerData;
import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.example.maptesttwoapplication.SetSellingItemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class SellerFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    private SellerAdapter sellerAdapter;
    private List<SellerData> sellerDataList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sellerfragment,container,false);
        recyclerView = view.findViewById(R.id.seller_recycler_view);
        floatingActionButton = view.findViewById(R.id.fab);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2
                ,LinearLayoutManager.VERTICAL,false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        sellerDataList = new ArrayList<>();

        loadRecyclerViewData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SetSellingItemActivity.class));
            }
        });
        return view;
    }

    private void loadRecyclerViewData() {
        CollectionReference serviceLocationRef =db.collection("Seller_Data")
                .document("new_item").collection(firebaseUser.getUid());

        serviceLocationRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    SellerData sellerData = documentSnapshot.toObject(SellerData.class);
                    sellerDataList.add(sellerData);
                }

                sellerAdapter = new SellerAdapter(sellerDataList, getContext());
                recyclerView.setAdapter(sellerAdapter);
            }
        });
    }


}
