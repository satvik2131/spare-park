package com.example.sparespark.view.common;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.sparespark.R;
import com.example.sparespark.databinding.RequestedLocationBinding;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.utils.SharedPreferencesHelper;
import com.example.sparespark.view.common.RequestLocationAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


//"Admin" and "Owner"
//It is common for owner requestedlocation list and for admin
public class RequestedLocationFragment extends Fragment {
    RequestLocationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestedLocationBinding locationBinding = DataBindingUtil.inflate(
                inflater, R.layout.requested_location, container, false
        );


        //We are using RequestLocationFragment in Admin and Owner with a different parameter
        //Here we will separate admin and owner recycler view with type of user value
        String typeofuser = SharedPreferencesHelper.getUserRole(getContext());

        DatabaseReference locationRequestRef = FirebaseDatabase.getInstance().getReference().child("location_requests");

        FirebaseRecyclerOptions<LocationModel> options;

        if (typeofuser.equals("Admin")!=true) {//Owner
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Query ownerQuery =  locationRequestRef.orderByChild("uid").equalTo(uid);

            options = new FirebaseRecyclerOptions
                    .Builder<LocationModel>()
                    .setQuery(ownerQuery, LocationModel.class)
                    .build();

        } else {//Admin
            Query adminQuery = locationRequestRef.orderByChild("status").equalTo(false);

            options = new FirebaseRecyclerOptions
                    .Builder<LocationModel>()
                    .setQuery(adminQuery, LocationModel.class)
                    .build();
        }


        adapter = new RequestLocationAdapter(options, getContext());
        locationBinding.setLocationAdapter(adapter);
        View view = locationBinding.getRoot();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
