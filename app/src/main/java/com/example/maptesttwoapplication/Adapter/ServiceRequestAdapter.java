package com.example.maptesttwoapplication.Adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maptesttwoapplication.Model_java_class.CompanyDealData;
import com.example.maptesttwoapplication.R;

import java.util.Calendar;
import java.util.List;


public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ViewHolder> {

    private Context mContext;
    private List<CompanyDealData> mUsers;
    private String date_time = "";
    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;
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


                datePicker(view);



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
    private void datePicker(View view){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        timePicker(view);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(View view){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        //et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
                        Toast.makeText(mContext, ""+date_time+" "+hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

}

