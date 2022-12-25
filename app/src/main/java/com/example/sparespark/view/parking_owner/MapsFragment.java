package com.example.sparespark.view.parking_owner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.sparespark.R;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.utils.Auth;
import com.example.sparespark.utils.FirebaseDatabaseOperations;
import com.example.sparespark.utils.SharedPreferencesHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapsFragment extends Fragment {

    public GoogleMap mMap;
    private static final int REQUEST_CODE = 101;
    public Location currentLocation;
    public EditText tenant_name, address, phoneno, timing;

    @Inject
    FirebaseDatabaseOperations operations;
    @Inject
    Auth auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        fetchLocation(getContext());

        return view;
    }


    //Fetches location and mark a marker to the location
    @SuppressLint("MissingPermission")
    public void fetchLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        // Get the LocationManager instance
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


        // Get the last known location from the provider
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Zooms the camera to the current location
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("I am here"));


            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(17).tilt(40).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            operations.getAllMapsMarkers(googleMap , getContext());


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    onMapListener(getContext(), currentLocation);
                }
            });
        }
    };


    //Saving Custom Admin Request and owner request location
    public void onMapListener(Context context, Location currentLocation) {
        new MaterialAlertDialogBuilder(context).setView(R.layout.additional_dialog)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tenant_name = (EditText) ((AlertDialog) dialog).findViewById(R.id.tenant_name);
                        address = (EditText) ((AlertDialog) dialog).findViewById(R.id.address);
                        phoneno = (EditText) ((AlertDialog) dialog).findViewById(R.id.phoneno);
                        timing = (EditText) ((AlertDialog) dialog).findViewById(R.id.timing);

                        String finalTenantName = tenant_name.getText().toString();
                        String finalAddress = address.getText().toString();
                        String finalPhone = phoneno.getText().toString();
                        String finalTiming = timing.getText().toString();
                        String uid = SharedPreferencesHelper.getCurrentUid(context);

                        if (finalTenantName.isEmpty() || finalAddress.isEmpty() || finalPhone.isEmpty() || finalTiming.isEmpty()) {
                            Toast.makeText(context, "Fill all details", Toast.LENGTH_SHORT).show();
                        } else {

                            DatabaseReference reference;
                            reference = FirebaseDatabase.getInstance().getReference();



                            String lrid = reference.child("location_requests").push().getKey();
                            boolean status;

                            //if the user role is admin then it's not required to have permission of request
                            //Admin - status is already true
                            if(SharedPreferencesHelper.getUserRole(getContext()).equals("Admin")){
                                status = true;
                            }else{
                                status = false;
                            }

                            LocationModel locationRequest = new LocationModel(finalTenantName, finalAddress, finalPhone, finalTiming, currentLocation.getLatitude(), currentLocation.getLongitude(), uid, lrid ,status);
                            reference.child("location_requests").child(lrid).setValue(locationRequest);

                        }
                    }
                }).show().create();
    }
}