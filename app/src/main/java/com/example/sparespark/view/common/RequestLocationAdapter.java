package com.example.sparespark.view.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparespark.R;
import com.example.sparespark.databinding.RequestedLocationCardBinding;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.utils.CustomClickListener;
import com.example.sparespark.utils.SharedPreferencesHelper;
import com.example.sparespark.viewmodels.ButtonValidationVM;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestLocationAdapter extends FirebaseRecyclerAdapter<LocationModel, RequestLocationAdapter.ViewHolder> implements CustomClickListener {
    Context context;
    RequestedLocationCardBinding requestedLocationCardBinding;


    public RequestLocationAdapter(@NonNull FirebaseRecyclerOptions<LocationModel> options, Context context) {
        super(options);
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        requestedLocationCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.requested_location_card, viewGroup, false
        );

        return new ViewHolder(requestedLocationCardBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull LocationModel model) {
        viewHolder.requestedLocationCardBinding.setLocationRequest(model);
        viewHolder.requestedLocationCardBinding.setButtonValidationVM(new ButtonValidationVM());
        viewHolder.requestedLocationCardBinding.setItemClickListener(this);
        viewHolder.requestedLocationCardBinding.executePendingBindings();

    }

    @Override
    public void userApproved(LocationModel f) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("location_requests").child(f.getLrid()).child("status").setValue(true);
    }

    @Override
    public void userDeclined(LocationModel f) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("location_requests").child(f.getLrid()).removeValue();
    }

    @Override
    public void updateLocation(LocationModel f) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RequestedLocationCardBinding requestedLocationCardBinding;

        public ViewHolder(RequestedLocationCardBinding binding) {
            super(binding.getRoot());
            this.requestedLocationCardBinding = binding;
        }
    }

}
