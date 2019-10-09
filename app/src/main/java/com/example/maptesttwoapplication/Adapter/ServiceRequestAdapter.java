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

import com.example.maptesttwoapplication.Model_java_class.CompanyDealData;
import com.example.maptesttwoapplication.R;

import java.util.List;


public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ViewHolder> {

    private Context mContext;
    private List<CompanyDealData> mUsers;
    public ServiceRequestAdapter(List<CompanyDealData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext=mContext;

    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_request_item, parent, false);
        //mContext = parent.getContext();
        return new ServiceRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CompanyDealData companyDealData = mUsers.get(position);
        holder.service_type.setText(companyDealData.getServiceType());
        holder.service_user.setText(companyDealData.getUserName());
        holder.user_contact.setText(companyDealData.getContact());
        holder.addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "add service"+companyDealData.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.cancelService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "cancel service"+companyDealData.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView service_type,service_user,user_contact;
        private Button addService,cancelService;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            service_type = itemView.findViewById(R.id.service_request_type);
            service_user = itemView.findViewById(R.id.service_user_name);
            user_contact = itemView.findViewById(R.id.service_user_contact);
            addService = itemView.findViewById(R.id.add_service_button);
            cancelService = itemView.findViewById(R.id.cancel_service_button);
        }
    }

}

