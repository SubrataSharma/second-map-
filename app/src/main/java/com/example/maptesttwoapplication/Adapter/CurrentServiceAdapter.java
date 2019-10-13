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
import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.example.maptesttwoapplication.Model_java_class.ServiceListData;
import com.example.maptesttwoapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.List;


public class CurrentServiceAdapter extends RecyclerView.Adapter<CurrentServiceAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private String date_time = "";
    private String service_date;
    private String company_name;
    private String company_contact_no;

    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour;
    private int mMinute;

    private Context mContext;
    private List<ServiceListData> mUsers;

    public CurrentServiceAdapter(List<ServiceListData> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_list_item, parent, false);
        //mContext = parent.getContext();
        return new CurrentServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ServiceListData serviceListData = mUsers.get(position);
        holder.service_type.setText(String.format("%s SERVICE", serviceListData.getServiceType()));
        holder.service_user.setText(serviceListData.getUserName());
        holder.user_contact.setText(serviceListData.getCompany_contact_email());
        holder.service_date.setText(serviceListData.getServiceTime());
        if(!serviceListData.isStatus()) holder.service_status.setVisibility(View.GONE);

        holder.service_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePicker(serviceListData, holder);
                //holder.service_status.setEnabled(false);


            }
        });


    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView service_type, service_user, user_contact, service_date;
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

    private void datePicker(final ServiceListData serviceListData, final ViewHolder holder) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        timePicker(serviceListData, holder);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void timePicker(final ServiceListData serviceListData, final ViewHolder holder) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        //et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
                        service_date = "date: " + date_time + " time: " + hourOfDay + ":" + minute;

                        addServiceData(serviceListData, holder);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void addServiceData(final ServiceListData serviceListData, final ViewHolder holder) {
        DocumentReference serviceListReference = db.collection("company_deal_details").document("service_list")
                .collection(firebaseUser.getUid()).document(serviceListData.getUserId());
        ServiceListData serviceListData1 = new ServiceListData(serviceListData.getUserName()
                , serviceListData.getServiceType(), serviceListData.getCompany_contact_email(), serviceListData.getUserId(), service_date, false);
        serviceListReference.set(serviceListData1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "sorry something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        DocumentReference serviceCompanyNameRef = db.collection("company_registration").document(firebaseUser.getUid());

        serviceCompanyNameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot documentSnapshot, @androidx.annotation.Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);

                assert mapLocation != null;
                company_name = mapLocation.getCompany();
                company_contact_no = mapLocation.getContactNo();
                addDataUserActivity(serviceListData, company_name, company_contact_no, holder);

            }
        });


    }

    private void addDataUserActivity(ServiceListData serviceListData, String company_name, String company_contact_no, ViewHolder holder) {

        DocumentReference userActivityListReference = db.collection("user_activity").document(serviceListData.getUserId())
                .collection("user_activity_data").document(firebaseUser.getUid());
        ServiceListData userActivityListData = new ServiceListData(serviceListData.getUserName()
                , serviceListData.getServiceType(), firebaseUser.getEmail(), serviceListData.getUserId()
                , service_date, company_name, company_contact_no, false);
        userActivityListReference.set(userActivityListData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mContext, "user has been notified", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("company_deal_details").document("pending_request")
                .collection(firebaseUser.getUid()).document(serviceListData.getUserId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "service completed", Toast.LENGTH_SHORT).show();

                    }
                });
        holder.service_status.setVisibility(View.GONE);
    }


}

