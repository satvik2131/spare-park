package com.example.sparespark.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.sparespark.R;
import com.example.sparespark.model.LocationModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import javax.inject.Inject;

public class LocationHelper {

    public EditText tenant_name, address, phoneno, timing;

    @Inject
    FirebaseDatabaseOperations operations;
    @Inject
    Auth auth;

    @Inject
    public LocationHelper() {
    }


    //Onclicking on the map dialog will appear
    //Dialog to fill extra details
    public void onMapListener(LatLng latLng, Context context , Location currentLocation) {
        new MaterialAlertDialogBuilder(context).setView(R.layout.additional_dialog).setPositiveButton("Add", new DialogInterface.OnClickListener() {
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

                    //New location request
                    LocationModel locationRequest = new LocationModel(finalTenantName, finalAddress, finalPhone, finalTiming, currentLocation.getLatitude(), currentLocation.getLongitude(), uid);
                    operations.addRequest(context, locationRequest);
                }
            }
        }).show().create();
    }





}
