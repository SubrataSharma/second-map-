package com.example.maptesttwoapplication.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.MapsActivity;
import com.example.maptesttwoapplication.Model_java_class.SellerData;
import com.example.maptesttwoapplication.R;
import com.example.maptesttwoapplication.ShowBuyerDataActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private Context mContext;
    private List<SellerData> mUsers;

    public BuyerAdapter(List<SellerData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.buyer_list_item, parent, false);
        //mContext = parent.getContext();
        return new BuyerAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SellerData serviceListData = mUsers.get(position);

        holder.seller_price_data.setText("₹ "+serviceListData.getSellingPrice());

        if(serviceListData.getSellingType().equals("wholesaling")) {
            String text=" <h6><i>" + serviceListData.getProductName() + "</i></h6>"+
                    "for <b>" +serviceListData.getSellingType() + "</b><br> "
                    + " ☞ " + serviceListData.getContactDetail();
            holder.seller_data.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    , TextView.BufferType.SPANNABLE);
        }else {
            String text=" <h6><i>" + serviceListData.getProductName() + "</i></h6><br> "
                    + " ☞ " + serviceListData.getContactDetail();
            holder.seller_data.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    , TextView.BufferType.SPANNABLE);
        }
        Picasso.get()
                .load(serviceListData.getFileUrl())
                .fit()
                .centerCrop()
                .into(holder.seller_product_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buyerId=serviceListData.getSellingId();
                Intent intent = new Intent(mContext, ShowBuyerDataActivity.class);
                intent.putExtra("buyerID",buyerId);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView seller_price_data,seller_data;
        private ImageView seller_product_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seller_price_data = itemView.findViewById(R.id.buyer_price_data);
            seller_data = itemView.findViewById(R.id.buyer_data);
            seller_product_image = itemView.findViewById(R.id.buyer_product_image);


        }
    }


}

