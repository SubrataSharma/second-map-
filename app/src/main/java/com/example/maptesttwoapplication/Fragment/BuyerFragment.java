package com.example.maptesttwoapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Adapter.BuyerAdapter;
import com.example.maptesttwoapplication.Adapter.SellerAdapter;
import com.example.maptesttwoapplication.Model_java_class.SellerData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BuyerFragment extends Fragment {

    private RecyclerView recyclerView;

    private BuyerAdapter buyerAdapter;
    private List<SellerData> buyerDataList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buyerfragment,container,false);

        recyclerView = view.findViewById(R.id.buyer_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2
                , LinearLayoutManager.VERTICAL,false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        buyerDataList = new ArrayList<>();

        loadRecyclerViewData();

        return view;
    }

    private void loadRecyclerViewData() {
        CollectionReference serviceLocationRef =db.collection("buyer_Data")
                ;

        serviceLocationRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                buyerDataList.clear();
                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    SellerData sellerData = documentSnapshot.toObject(SellerData.class);
                    buyerDataList.add(sellerData);
                }

                buyerAdapter = new BuyerAdapter(buyerDataList, getContext());
                recyclerView.setAdapter(buyerAdapter);
            }
        });
    }
}
