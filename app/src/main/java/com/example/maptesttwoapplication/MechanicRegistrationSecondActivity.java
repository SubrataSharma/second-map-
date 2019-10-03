package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MechanicRegistrationSecondActivity extends AppCompatActivity {

    EditText company_name,register_name,contact_no, pass, con_pass;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser;
    List<String> allService ;
    String[] serviceListitem;
    boolean[] serviceCheckedItems;
    ArrayList<Integer> serviceUserSelectedItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration_second);
        serviceListitem = getResources().getStringArray(R.array.service_type);
        serviceCheckedItems = new boolean[serviceListitem.length];
    }

    public void serviceType(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MechanicRegistrationSecondActivity.this);
        mBuilder.setTitle(R.string.dialog_title);
        mBuilder.setMultiChoiceItems(serviceListitem, serviceCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                if(isChecked){
                    Toast.makeText(getApplicationContext(), " "+position, Toast.LENGTH_SHORT).show();
                    serviceUserSelectedItems.add(position);
                }else{
                    serviceUserSelectedItems.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < serviceUserSelectedItems.size(); i++) {
                    item = item + serviceListitem[serviceUserSelectedItems.get(i)];
                    if (i != serviceUserSelectedItems.size() - 1) {
                        item = item + ", ";
                    }
                }
                String[] itemArray = item.split("\\s*,\\s*");
                allService = Arrays.asList(itemArray);
            }
        });

        mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "select one service at least", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < serviceCheckedItems.length; i++) {
                    serviceCheckedItems[i] = false;
                    serviceUserSelectedItems.clear();
                    Toast.makeText(getApplicationContext(), "select one service at least", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    public void submitButton(View view) {
        company_name=findViewById(R.id.mechanic_company_name);
        register_name=findViewById(R.id.mechanic_register_name);
        contact_no=findViewById(R.id.mechanic_contact_no);
        pass=findViewById(R.id.mechanic_pass);
        con_pass=findViewById(R.id.mechanic_con_pass);
        String company= company_name.getText().toString();
        String registerName= register_name.getText().toString();
        String contactNo= contact_no.getText().toString();
        String pin= pass.getText().toString();
        String conPin= con_pass.getText().toString();
         double lat =  getIntent().getExtras().getDouble("latitude");
         double lon =  getIntent().getExtras().getDouble("longitude");

         if(company.isEmpty()||(lat==0)||(lon==0)||registerName.isEmpty()||contactNo.isEmpty()
                 ||pin.isEmpty()||conPin.isEmpty()||allService.isEmpty()){
             Toast.makeText(this, "All fields are requires", Toast.LENGTH_SHORT).show();
         }else if(pin.length() < 6){
             Toast.makeText(this, "Password must be at least 6 digits", Toast.LENGTH_SHORT).show();
         }else if(!pin.equals(conPin)) {
             Toast.makeText(this, "please enter the same password again", Toast.LENGTH_SHORT).show();
             //register(txt_username,txt_email,txt_password);
         }else {
             firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
             assert firebaseUser != null;
                 String userid = firebaseUser.getUid();

                 DocumentReference locationRef =db.collection("company_registration").document(userid);
                 MapLocation mapLocation = new MapLocation(userid,lat, lon, company,
                         registerName,contactNo,Integer.parseInt(pin),allService);
                 locationRef.set(mapLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             Intent intent = new Intent(MechanicRegistrationSecondActivity.this,MapsActivity.class);
                             Toast.makeText(MechanicRegistrationSecondActivity.this,"successfully registered",Toast.LENGTH_LONG).show();
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                             startActivity(intent);
                             finish();
                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MechanicRegistrationSecondActivity.this,"you can't register",Toast.LENGTH_LONG).show();
                         finish();
                     }
                 });






         }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MechanicRegistrationSecondActivity.this,MechanicRegistrationActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void previousButton(View view) {
        startActivity(new Intent(MechanicRegistrationSecondActivity.this,MechanicRegistrationActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }




}
