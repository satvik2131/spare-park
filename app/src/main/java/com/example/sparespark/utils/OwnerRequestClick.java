package com.example.sparespark.utils;


import com.example.sparespark.model.UserBookRequest;

public interface OwnerRequestClick {
    void userApproved(UserBookRequest f);
    void userDeclined(UserBookRequest f);
}
