package com.example.sparespark.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.sparespark.model.LocationModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

public class FirebaseDatabaseOperations {
    DatabaseReference reference;

    @Inject
    public FirebaseDatabaseOperations(){

    }

    public void addRequest(Context context, LocationModel model){
        reference = FirebaseDatabase.getInstance().getReference();

        String key = reference.child("location_requests").push().getKey();
        reference.child("location_requests").child(key).setValue(model);

        Toast.makeText(context, "Location Request will be visible after admin approval", Toast.LENGTH_LONG).show();
    }

}
