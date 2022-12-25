package com.example.sparespark.view.parking_owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparespark.R;
import com.example.sparespark.databinding.RequestsUserCardBinding;
import com.example.sparespark.model.UserBookRequest;
import com.example.sparespark.utils.OwnerRequestClick;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestsUserAdapter extends FirebaseRecyclerAdapter<UserBookRequest, RequestsUserAdapter.ViewHolder> implements OwnerRequestClick {
    Context context;
    RequestsUserCardBinding userCardBinding;


    public RequestsUserAdapter(@NonNull FirebaseRecyclerOptions<UserBookRequest> options, Context context) {
        super(options);
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public RequestsUserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        userCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.requests_user_card, viewGroup, false
        );

        return new ViewHolder(userCardBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull RequestsUserAdapter.ViewHolder viewHolder, int position, @NonNull UserBookRequest model) {
        viewHolder.userCardBinding.setLocationRequest(model);
        viewHolder.userCardBinding.setItemClickListener(this);
        viewHolder.userCardBinding.executePendingBindings();
    }

    @Override
    public void userApproved(UserBookRequest f) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("user_book_request_location").child(f.getLrid()).child("status").setValue(true);
    }

    @Override
    public void userDeclined(UserBookRequest f) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("user_book_request_location").child(f.getLrid()).removeValue();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RequestsUserCardBinding userCardBinding;

        public ViewHolder(RequestsUserCardBinding binding) {
            super(binding.getRoot());
            this.userCardBinding = binding;
        }
    }

}
