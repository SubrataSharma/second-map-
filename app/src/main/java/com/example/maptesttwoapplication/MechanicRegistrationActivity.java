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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;

public class MechanicRegistrationActivity extends AppCompatActivity implements OnMapReadyCallback
         {
    private static final String TAG = "MechanicRegistrationActivity";
    private static final String KEY_LATITUDE = "Latitude";
    private static final String KEY_LONGITUDE = "Longitude";
    private GoogleMap mMap;
    private EditText editText;
    private ImageButton imageButton;
    private Button submit_button;

    Double lat = 0.0;
    Double lon =0.0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference locationRef =db.collection("company_registration_email").document("company_name");
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
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
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

            // remember to add model class Location
         MapLocation mapLocation = new MapLocation(lat,lon);

         locationRef.set(mapLocation)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(MechanicRegistrationActivity.this, "location added", Toast.LENGTH_SHORT).show();
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MechanicRegistrationActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                         Log.d(TAG, e.toString());
                     }
                 });
      }

             @Override
             public void onBackPressed() {
                startActivity(new Intent(MechanicRegistrationActivity.this,MapsActivity.class));

                super.onBackPressed();
             }
         }
