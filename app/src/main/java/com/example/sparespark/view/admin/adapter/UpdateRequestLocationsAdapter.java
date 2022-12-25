package com.example.sparespark.view.admin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparespark.R;
import com.example.sparespark.databinding.UpdateRequestLocationCardBinding;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.utils.CustomClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class UpdateRequestLocationsAdapter extends FirebaseRecyclerAdapter<LocationModel, UpdateRequestLocationsAdapter.ViewHolder> implements CustomClickListener {
    Context context;
    UpdateRequestLocationCardBinding requestedLocationCardBinding;


    public UpdateRequestLocationsAdapter(@NonNull FirebaseRecyclerOptions<LocationModel> options, Context context) {
        super(options);
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public UpdateRequestLocationsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        requestedLocationCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.update_request_location_card, viewGroup, false
        );

        return new UpdateRequestLocationsAdapter.ViewHolder(requestedLocationCardBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull UpdateRequestLocationsAdapter.ViewHolder viewHolder, int position, @NonNull LocationModel model) {
        viewHolder.requestedLocationCardBinding.setLocationRequest(model);
        viewHolder.requestedLocationCardBinding.setItemClickListener(this);
        viewHolder.requestedLocationCardBinding.executePendingBindings();

    }


    //Approves user
    @Override
    public void userApproved(LocationModel f) {
    }


//    Rejects User
    @Override
    public void userDeclined(LocationModel f) {

    }


    //Updates approved location requests
    @Override
    public void updateLocation(LocationModel f) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference().child("accepted_requests").child(f.getLrid());

        new MaterialAlertDialogBuilder(context)
                .setView(R.layout.additional_dialog)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText tenant_name, address, phoneno, timing;

                        tenant_name = (EditText) ((AlertDialog) dialog).findViewById(R.id.tenant_name);
                        address = (EditText) ((AlertDialog) dialog).findViewById(R.id.address);
                        phoneno = (EditText) ((AlertDialog) dialog).findViewById(R.id.phoneno);
                        timing = (EditText) ((AlertDialog) dialog).findViewById(R.id.timing);

                        String finalTenantName = tenant_name.getText().toString();
                        if(finalTenantName.equals("") != true ) reference.child("tenant_name").setValue(finalTenantName);
                        String finalAddress = address.getText().toString();
                        if(finalAddress.equals("") != true ) reference.child("address").setValue(finalAddress);
                        String finalPhone = phoneno.getText().toString();
                        if(finalPhone.equals("") != true ) reference.child("phoneno").setValue(finalPhone);
                        String finalTiming = timing.getText().toString();
                        if(finalTiming.equals("") != true ) reference.child("timing").setValue(finalTiming);
                    }
                }).show().create();


    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public UpdateRequestLocationCardBinding requestedLocationCardBinding;

        public ViewHolder(UpdateRequestLocationCardBinding binding) {
            super(binding.getRoot());
            this.requestedLocationCardBinding = binding;
        }
    }

}