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
import com.example.sparespark.databinding.RequestsUserBinding;
import com.example.sparespark.databinding.UserBookedSpaceBinding;
import com.example.sparespark.model.UserBookRequest;
import com.example.sparespark.utils.Auth;
import com.example.sparespark.utils.SharedPreferencesHelper;
import com.example.sparespark.view.parking_owner.adapter.RequestsUserAdapter;
import com.example.sparespark.view.user.adapter.UserRequestAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import javax.inject.Inject;

public class RequestsUser extends Fragment {
    RequestsUserAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RequestsUserBinding requestsUserBinding = DataBindingUtil.inflate(
                inflater, R.layout.requests_user, container, false
        );



        DatabaseReference locationRequestRef = FirebaseDatabase.getInstance().getReference().child("user_book_request_location");

        FirebaseRecyclerOptions<UserBookRequest> options;

        options = new FirebaseRecyclerOptions
                .Builder<UserBookRequest>()
                .setQuery(locationRequestRef, UserBookRequest.class)
                .build();



        adapter = new RequestsUserAdapter(options, getContext());
        requestsUserBinding.setUserAdapter(adapter);
        View view = requestsUserBinding.getRoot();
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