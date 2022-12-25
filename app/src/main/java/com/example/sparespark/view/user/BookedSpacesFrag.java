package com.example.sparespark.view.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.sparespark.R;
import com.example.sparespark.databinding.UserBookedSpaceBinding;
import com.example.sparespark.model.UserBookRequest;
import com.example.sparespark.utils.Auth;
import com.example.sparespark.utils.SharedPreferencesHelper;
import com.example.sparespark.view.user.adapter.UserRequestAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class BookedSpacesFrag extends Fragment {
    UserRequestAdapter adapter;

    @Inject
    Auth auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //User Authorization
        auth.checkUserAuth(getContext());
        String uid = SharedPreferencesHelper.getCurrentUid(getContext());

        UserBookedSpaceBinding userSpaceBooking = DataBindingUtil.inflate(
                inflater, R.layout.user_booked_space, container, false
        );



        DatabaseReference locationRequestRef = FirebaseDatabase.getInstance().getReference().child("user_book_request_location");



        FirebaseRecyclerOptions<UserBookRequest> options;

            Query adminQuery = locationRequestRef.orderByChild("uid").equalTo(uid);

            options = new FirebaseRecyclerOptions
                    .Builder<UserBookRequest>()
                    .setQuery(adminQuery, UserBookRequest.class)
                    .build();



        adapter = new UserRequestAdapter(options, getContext());
        userSpaceBooking.setUserAdapter(adapter);
        View view = userSpaceBooking.getRoot();
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
