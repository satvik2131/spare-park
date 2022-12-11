package com.example.sparespark.view.parking_owner;

import android.os.Bundle;
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
import com.example.sparespark.view.parking_owner.adapter.RequestLocationAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestedLocationFragment extends Fragment {

    RequestLocationAdapter adapter ;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestedLocationBinding locationBinding = DataBindingUtil.inflate(
                inflater , R.layout.requested_location , container , false
        );

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference locationRequestRef = FirebaseDatabase.getInstance().getReference().child("location_requests");
        DatabaseReference finalRef = locationRequestRef.orderByChild("uid").equalTo(uid).getRef();

        FirebaseRecyclerOptions<LocationModel> options
                = new FirebaseRecyclerOptions
                .Builder<LocationModel>()
                .setQuery(finalRef,LocationModel.class)
                .build();

        adapter = new RequestLocationAdapter(options , getContext());
        locationBinding.setLocationAdapter(adapter);
        View view = locationBinding.getRoot();
        return view ;
    }
}
