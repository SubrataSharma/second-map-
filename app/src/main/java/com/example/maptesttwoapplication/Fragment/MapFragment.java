package com.example.maptesttwoapplication.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.maptesttwoapplication.Model_java_class.MapLocation;
import com.example.maptesttwoapplication.R;
import com.example.maptesttwoapplication.ServicesActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private static final String TAG = "Map fragment";
    private String serviceChoice;
    private double latitide;
    private double longitude;

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code=99;
        ActionBarDrawerToggle toggle;
    private EditText editText;
    private Button button;
    private CircleImageView carSearch,truckSearch,suvSearch,motorcycleSearch,tractorSearch;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference locationRef =db.collection("company_registration");


@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mapfragment,container,false);


        button =  v.findViewById(R.id.get_location_button);
        editText =  v.findViewById(R.id.get_location);

        showLocationByUserChoice(v);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(getEditText()))
                {
                    Geocoder geocoder = new Geocoder(getContext());

                    try
                    {
                        addressList = geocoder.getFromLocationName(getEditText(), 6);

                        if (addressList != null)
                        {
                            for (int i=0; i<addressList.size(); i++)
                            {

                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(getEditText());
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Location not found...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "please write any location name...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    /*// Obtain the SupportMapFragment and get notified when the map is ready to be used.
    getFragmentManager()
            .findFragmentById(R.id.map);
    getFragmentManager().getMapAsync(this);*/
    return v;
}



    public String getEditText() {
        String address = editText.getText().toString();
        return address;
    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera

        //LatLng custom = new LatLng(getLat(),getLon());
       // mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(custom));




    }

    public boolean checkUserLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            else
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {

    lastlocation = location;

        if (currentUserLocationMarker != null)
        {
            currentUserLocationMarker.remove();
        }

        latitide = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitide, longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);
        mMap.getCameraPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

        if (googleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }


    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void loadServiceActivity(){

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Query loc=locationRef.whereEqualTo("latitude",marker.getPosition().latitude)
                        .whereEqualTo("longitude",marker.getPosition().longitude);
                loc.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);


                            if(mapLocation.getLatitude()!= 0 && mapLocation.getLongitude()!=0){
                                String document =mapLocation.getId();
                                Intent intent =new Intent(getActivity(),ServicesActivity.class);
                                intent.putExtra("ID",document);
                                intent.putExtra("SERVICE",serviceChoice);
                                startActivity(intent);
                            }
                        }
                    }
                });

                return true;
            }
        });
    }

    private void showAllLocation(){
        locationRef.addSnapshotListener(  new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e !=null){
                    return;
                }
                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);
                    final double lat = mapLocation.getLatitude();
                    final double lon = mapLocation.getLongitude();
                    final LatLng custom = new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));

                    loadServiceActivity();
                }
            }
        });
    }

    private void showLocationByUserChoice(View view){
        carSearch = view.findViewById(R.id.search_button_car);
        truckSearch = view.findViewById(R.id.search_button_truck);
        suvSearch = view.findViewById(R.id.search_button_suv);
        motorcycleSearch = view.findViewById(R.id.search_button_motorcycle);
        tractorSearch = view.findViewById(R.id.search_button_tractor);


        carSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceChoice="CAR";
                mMap.clear();
                locationRef.whereArrayContains("services","car").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                assert queryDocumentSnapshots != null;
                                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                    MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);
                                    final double lat = mapLocation.getLatitude();
                                    final double lon = mapLocation.getLongitude();
                                    final LatLng custom = new LatLng(lat,lon);
                                    mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));
                                    loadServiceActivity();
                                }
                                // move camera to user current location
                                //LatLng latLng= new LatLng(latitide,longitude);
                                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

                            }
                        });
            }
        });
        truckSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceChoice="TRUCK";
                mMap.clear();
                locationRef.whereArrayContains("services","truck").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                assert queryDocumentSnapshots != null;
                                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                    MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);
                                    final double lat = mapLocation.getLatitude();
                                    final double lon = mapLocation.getLongitude();
                                    final LatLng custom = new LatLng(lat,lon);
                                    mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));

                                    loadServiceActivity();
                                }
                                // move camera to user current location
                                //LatLng latLng= new LatLng(latitide,longitude);
                                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

                            }
                        });
            }
        });
        suvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceChoice="SUV";
                mMap.clear();
                locationRef.whereArrayContains("services","suv").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                assert queryDocumentSnapshots != null;
                                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                    MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);
                                    final double lat = mapLocation.getLatitude();
                                    final double lon = mapLocation.getLongitude();
                                    final LatLng custom = new LatLng(lat,lon);
                                    mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));

                                    loadServiceActivity();
                                }
                                // move camera to user current location
                                //LatLng latLng= new LatLng(latitide,longitude);
                                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

                            }
                        });
            }
        });
        motorcycleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceChoice="MOTOR BIKE";
                mMap.clear();
                locationRef.whereArrayContains("services","bike").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                assert queryDocumentSnapshots != null;
                                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                    MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);
                                    final double lat = mapLocation.getLatitude();
                                    final double lon = mapLocation.getLongitude();
                                    final LatLng custom = new LatLng(lat,lon);
                                    mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));

                                    loadServiceActivity();
                                }
                                // move camera to user current location
                                //LatLng latLng= new LatLng(latitide,longitude);
                                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

                            }
                        });
            }
        });
        tractorSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceChoice="TRACTOR";
                mMap.clear();
                locationRef.whereArrayContains("services","tractor").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                assert queryDocumentSnapshots != null;
                                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                    MapLocation mapLocation = documentSnapshot.toObject(MapLocation.class);
                                    final double lat = mapLocation.getLatitude();
                                    final double lon = mapLocation.getLongitude();
                                    final LatLng custom = new LatLng(lat,lon);
                                    mMap.addMarker(new MarkerOptions().position(custom).title("Marker in Custom"));

                                    loadServiceActivity();
                                }
                                // move camera to user current location
                                //LatLng latLng= new LatLng(latitide,longitude);
                                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,7));

                            }
                        });
            }
        });
    }


}


