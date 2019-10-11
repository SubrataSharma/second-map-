package com.example.maptesttwoapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class CurrentServiceAdapter extends RecyclerView.Adapter<CurrentServiceAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private Context mContext;
    private List<ServiceListData> mUsers;

    public CurrentServiceAdapter(List<ServiceListData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext=mContext;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_list_item, parent, false);
        //mContext = parent.getContext();
        return new CurrentServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServiceListData serviceListData = mUsers.get(position);
        holder.service_type.setText(serviceListData.getServiceType());
        holder.service_user.setText(serviceListData.getUserName());
        holder.user_contact.setText(serviceListData.getContact());
        holder.service_date.setText(serviceListData.getServiceTime());

        holder.service_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "not available", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView service_type,service_user,user_contact,service_date;
        private Button service_status;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_type = itemView.findViewById(R.id.service_request_type);
            service_user = itemView.findViewById(R.id.service_user_name);
            user_contact = itemView.findViewById(R.id.service_user_contact);
            service_date = itemView.findViewById(R.id.service_user_time);
            service_status = itemView.findViewById(R.id.service_status_button);
        }
    }


}

