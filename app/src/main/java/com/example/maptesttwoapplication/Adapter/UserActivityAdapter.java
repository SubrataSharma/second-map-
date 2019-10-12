package com.example.maptesttwoapplication.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private Context mContext;
    private List<ServiceListData> mUsers;

    public UserActivityAdapter(List<ServiceListData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_activity_list_item, parent, false);
        //mContext = parent.getContext();
        return new UserActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServiceListData serviceListData = mUsers.get(position);
        if (serviceListData.isStatus()) {
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


        }


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

