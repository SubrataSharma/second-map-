package com.example.maptesttwoapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Model_java_class.DeliveryNotificationData;
import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private Context mContext;
    private List<DeliveryNotificationData> mUsers;

    public NotificationAdapter(List<DeliveryNotificationData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_activity_list_item, parent, false);
        //mContext = parent.getContext();
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DeliveryNotificationData deliveryNotificationData = mUsers.get(position);

            String text = "please call " + "<font color='blue'>" + deliveryNotificationData.getRegisterName() + "</font> for delivery service" + "<br>"
                    + " in this " + "<font color='blue'>" + deliveryNotificationData.getContactNo() + "</font> no." + "<br>";

            holder.service_type.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT), TextView.BufferType.SPANNABLE);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView service_type;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_type = itemView.findViewById(R.id.user_activity_data);


        }
    }


}

