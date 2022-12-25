package com.example.sparespark.utils;

import com.example.sparespark.model.LocationModel;

public interface CustomClickListener {
    void userApproved(LocationModel f);
    void userDeclined(LocationModel f);
    void updateLocation(LocationModel f);
}