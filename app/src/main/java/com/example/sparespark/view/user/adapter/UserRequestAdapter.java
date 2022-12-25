package com.example.sparespark.view.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sparespark.R;
import com.example.sparespark.databinding.UserBookingCardBinding;
import com.example.sparespark.model.LocationModel;
import com.example.sparespark.model.UserBookRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class UserRequestAdapter extends FirebaseRecyclerAdapter<UserBookRequest, UserRequestAdapter.ViewHolder>  {
    Context context;
    UserBookingCardBinding bookingCardBinding;


    public UserRequestAdapter(@NonNull FirebaseRecyclerOptions<UserBookRequest> options, Context context) {
        super(options);
        this.context = context;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public UserRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        bookingCardBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.user_booking_card, viewGroup, false
        );

        return new ViewHolder(bookingCardBinding);
    }


    @Override
    protected void onBindViewHolder(@NonNull UserRequestAdapter.ViewHolder viewHolder, int position, @NonNull UserBookRequest model) {
        viewHolder.bookingCardBinding.setLocationRequest(model);
        viewHolder.bookingCardBinding.executePendingBindings();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public UserBookingCardBinding bookingCardBinding;

        public ViewHolder(UserBookingCardBinding binding) {
            super(binding.getRoot());
            this.bookingCardBinding = binding;
        }
    }

}
