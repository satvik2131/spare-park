package com.example.sparespark.view.parking_owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparespark.R;
import com.example.sparespark.databinding.RequestedLocationBinding;
import com.example.sparespark.databinding.RequestedLocationCardBinding;
import com.example.sparespark.model.LocationModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RequestLocationAdapter extends FirebaseRecyclerAdapter<LocationModel, RequestLocationAdapter.ViewHolder> {
    Context context ;

    public RequestLocationAdapter(@NonNull FirebaseRecyclerOptions<LocationModel> options , Context context) {
        super(options);
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RequestedLocationCardBinding requestedLocationCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.requested_location_card,viewGroup,false
        );

        return new ViewHolder(requestedLocationCardBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull LocationModel model) {
        viewHolder.requestedLocationCardBinding.setLocationRequest(model);
        viewHolder.requestedLocationCardBinding.executePendingBindings();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RequestedLocationCardBinding requestedLocationCardBinding;

        public ViewHolder(RequestedLocationCardBinding binding) {
            super(binding.getRoot());
            this.requestedLocationCardBinding = binding;
        }
    }

}
