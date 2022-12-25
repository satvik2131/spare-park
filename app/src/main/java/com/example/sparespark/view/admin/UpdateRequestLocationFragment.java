package com.example.sparespark.view.admin;

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
import com.example.sparespark.databinding.UpdateRequestLocationBinding;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.view.admin.adapter.UpdateRequestLocationsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UpdateRequestLocationFragment extends Fragment {
    UpdateRequestLocationsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UpdateRequestLocationBinding binding = DataBindingUtil.inflate(inflater, R.layout.update_request_location, container, false);


        Query approvedLocationQuery = FirebaseDatabase.getInstance().getReference()
                .child("location_requests").orderByChild("status").equalTo(true);
        FirebaseRecyclerOptions<LocationModel> options = new FirebaseRecyclerOptions
                .Builder<LocationModel>()
                .setQuery(approvedLocationQuery, LocationModel.class)
                .build();
        ;


        adapter = new UpdateRequestLocationsAdapter(options , getContext());
        binding.setLocationAdapter(adapter);

        View view = binding.getRoot();
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
