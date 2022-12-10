package com.example.sparespark.view.parking_owner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.sparepark.R;
import com.example.sparepark.databinding.OwnerMapBinding;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.utils.FirebaseDatabaseOperations;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class OwnerMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private OwnerMapBinding binding;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    public EditText tenant_name, address, phoneno, timing;

    @Inject
    FirebaseDatabaseOperations operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = OwnerMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }


    //Fetches location and mark a marker to the location
    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(OwnerMap.this);
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Zooms the camera to the current location
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("I am here"));


        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(17).tilt(40).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                onMapListener(latLng);
            }
        });
    }


    //Onclicking on the map dialog will appear
    //Dialog to fill extra details
    private void onMapListener(LatLng latLng) {
        new MaterialAlertDialogBuilder(this).setView(R.layout.additional_dialog).setPositiveButton("Add", new DialogInterface.OnClickListener() {
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

                if(finalTenantName.isEmpty() || finalAddress.isEmpty() || finalPhone.isEmpty() || finalTiming.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all details",Toast.LENGTH_SHORT).show();
                }else{
                    //New location request
                    LocationModel locationRequest = new LocationModel(
                            finalTenantName,
                            finalAddress,
                            finalPhone,
                            finalTiming,
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude());

                    operations.addRequest(getApplicationContext(), locationRequest);
                }
            }
        }).show().create();
    }
}