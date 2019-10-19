package com.example.maptesttwoapplication.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
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
        /*if (serviceListData.isStatus()) {
            String text = "your appointment of " + "<font color='blue'>" + serviceListData.getServiceType() + "</font>" + "<br>"
                    + "service has been booked in " + "<font color='blue'>" + serviceListData.getCompany_name() + "</font>" + "<br>"
                    + "please go there in " + "<font color='#ff0000'>" + serviceListData.getServiceTime() + "</font>" + "<br>"
                    + "for more details contact " + "<font color='blue'>" + serviceListData.getCompany_contact_no() + "</font> or" + "<br>"
                    + "<font color='blue'>" + serviceListData.getCompany_contact_email() + "</font>" + "<br>"
                    + "<h6>status:<font color='#228B22'> pending</font></h6>";

            holder.service_type.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);

        } else {
            String text = "your service of " + "<font color='blue'>" + serviceListData.getServiceType() + "</font>" + "<br>"
                    + "is completed in " + "<font color='blue'>" + serviceListData.getCompany_name() + "</font>" + "<br>"
                    + "please go there in " + "<font color='#ff0000'>" + serviceListData.getServiceTime() + "</font>" + "<br>"
                    + "for more details contact " + "<font color='blue'>" + serviceListData.getCompany_contact_no() + "</font> or" + "<br>"
                    + "<font color='blue'>" + serviceListData.getCompany_contact_email() + "</font>" + "<br>"
                    + "<h6>status:<font color='#228B22'> completed</font></h6>";
            holder.service_type.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);


        }*/
        holder.seller_price_data.setText("₹ "+serviceListData.getSellingPrice());
        holder.seller_data.setText("" + serviceListData.getProductName() + "\n"
                + serviceListData.getSellerName());

        Picasso.get()
                .load(serviceListData.getFileUrl())
                .fit()
                .centerCrop()
                .into(holder.seller_product_image);



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
            seller_price_data = itemView.findViewById(R.id.seller_price_data);
            seller_data = itemView.findViewById(R.id.seller_data);
            seller_product_image = itemView.findViewById(R.id.seller_product_image);


        }
    }


}

