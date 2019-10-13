package com.example.maptesttwoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;

public class MechanicRegistrationActivity extends AppCompatActivity implements OnMapReadyCallback
         {

    private GoogleMap mMap;
    private EditText editText;
    private ImageButton imageButton;
    private TextView submit_button;

    Double lat = 0.0;
    Double lon =0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_registration);

         imageButton = findViewById(R.id.get_location_button_registration);
        editText = findViewById(R.id.get_location_registration);
        submit_button=findViewById(R.id.submit_button);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_registration);
        supportMapFragment.getMapAsync(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(getEditText()))
                {
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    try
                    {
                        addressList = geocoder.getFromLocationName(getEditText(), 6);

                        if (addressList != null)
                        {
                            for (int i=0; i<addressList.size(); i++)
                            {
                                Address userAddress = addressList.get(i);
                                lat=userAddress.getLatitude();
                                lon=userAddress.getLongitude();
                                LatLng latLng = new LatLng(lat, lon);
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(getEditText());
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(userMarkerOptions);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Location not found...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "please write any location name...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public String getEditText() {
        String address = editText.getText().toString();
        return address;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

     public void submitButton(View view) {

         if((lat==0)||(lon==0)) {
             Toast.makeText(this, "No valid location found", Toast.LENGTH_SHORT).show();
         }else {

             Intent intent = new Intent(this, MechanicRegistrationSecondActivity.class);
             intent.putExtra("latitude", lat);
             intent.putExtra("longitude", lon);
             startActivity(intent);



         }
        // remember to add model class Location


      }

             @Override
             public void onBackPressed() {
                 super.onBackPressed();
                 startActivity(new Intent(MechanicRegistrationActivity.this,MechanicLoginActivity.class));
                 overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
             }

             public void previousButton(View view) {
                 startActivity(new Intent(MechanicRegistrationActivity.this,MechanicLoginActivity.class));
                 overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
             }
         }
