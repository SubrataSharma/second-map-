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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.List;


public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ViewHolder> {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    private Context mContext;
    private List<CompanyDealData> mUsers;
    private String date_time = "";
    private String service_date;
    private String company_name;
    private String company_contact_no;

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
                datePicker(companyDealData);

            }
        });

        holder.cancelService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("company_deal_details").document("pending_request")
                        .collection(firebaseUser.getUid()).document(companyDealData.getUserId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "service canceled", Toast.LENGTH_SHORT).show();

                            }
                        });
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
    private void datePicker(final CompanyDealData companyDealData){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        timePicker(companyDealData);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void timePicker(final CompanyDealData companyDealData){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        //et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);
                       service_date = "date: "+date_time+" time: "+hourOfDay + ":" + minute;

                        addServiceData(companyDealData);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void addServiceData(final CompanyDealData companyDealData){

        DocumentReference serviceListReference = db.collection("company_deal_details").document("service_list")
                .collection(firebaseUser.getUid()).document(companyDealData.getUserId());
        ServiceListData serviceListData = new ServiceListData(companyDealData.getUserName()
                ,companyDealData.getServiceType(),companyDealData.getContact(),companyDealData.getUserId(),service_date,true);
        serviceListReference.set(serviceListData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "sorry something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


        DocumentReference serviceCompanyNameRef =db.collection("company_registration").document(firebaseUser.getUid());

        serviceCompanyNameRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable DocumentSnapshot documentSnapshot, @androidx.annotation.Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);

                assert mapLocation != null;
                company_name=mapLocation.getCompany();
                company_contact_no=mapLocation.getContactNo();
                addDataUserActivity(companyDealData,company_name,company_contact_no);

            }
        });


    }

    private void addDataUserActivity(CompanyDealData companyDealData,String company_name,String company_contact_no){

        DocumentReference userActivityListReference = db.collection("user_activity").document(companyDealData.getUserId());
        ServiceListData userActivityListData = new ServiceListData(companyDealData.getUserName()
                ,companyDealData.getServiceType(),firebaseUser.getEmail(),companyDealData.getUserId(),service_date,company_name,company_contact_no,true);
        userActivityListReference.set(userActivityListData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mContext, "user has been notified", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

