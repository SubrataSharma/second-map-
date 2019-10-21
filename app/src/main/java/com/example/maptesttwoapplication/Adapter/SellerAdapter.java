package com.example.maptesttwoapplication.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Model_java_class.SellerData;
import com.example.maptesttwoapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private Context mContext;
    private List<SellerData> mUsers;


    public SellerAdapter(List<SellerData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.seller_list_item, parent, false);
        //mContext = parent.getContext();
        return new SellerAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SellerData serviceListData = mUsers.get(position);

        holder.seller_price_data.setText("₹ "+serviceListData.getSellingPrice());
        holder.seller_data.setText("" + serviceListData.getProductName() + "\n"
                + serviceListData.getSellerName());

        Picasso.get()
                .load(serviceListData.getFileUrl())
                .fit()
                .centerCrop()
                .into(holder.seller_product_image);


        holder.delete_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.delete_option_button);

                popup.inflate(R.menu.delete_option);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_delete_option) {
                            db.collection("Seller_Data").document("new_item")
                                    .collection(firebaseUser.getUid()).document(serviceListData.getSellingId()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(mContext, "item deleted", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                            StorageReference photoRef = firebaseStorage.getReferenceFromUrl(serviceListData.getFileUrl());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(mContext, "file deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            deleteFromBuyer(serviceListData);
                            return true;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    private void deleteFromBuyer(SellerData serviceListData) {
        db.collection("buyer_Data").document(serviceListData.getSellingId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "item deleted from buyer", Toast.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView seller_price_data,seller_data,delete_option_button;
        private ImageView seller_product_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seller_price_data = itemView.findViewById(R.id.seller_price_data);
            seller_data = itemView.findViewById(R.id.seller_data);
            seller_product_image = itemView.findViewById(R.id.seller_product_image);
            delete_option_button = itemView.findViewById(R.id.textView_delete_Options);


        }
    }


}

