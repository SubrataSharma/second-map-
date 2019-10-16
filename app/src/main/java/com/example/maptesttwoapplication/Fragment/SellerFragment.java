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
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.MechanicLoginActivity;
import com.example.maptesttwoapplication.MechanicRegistrationActivity;
import com.example.maptesttwoapplication.R;
import com.example.maptesttwoapplication.SetSellingItemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SellerFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sellerfragment,container,false);
        recyclerView = view.findViewById(R.id.seller_recycler_view);
        floatingActionButton = view.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SetSellingItemActivity.class));
            }
        });
        return view;
    }


}
