package com.example.sparespark.utils;

import android.content.Context;
import android.content.Intent;

import com.example.sparespark.MainActivity;

import javax.inject.Inject;

public class Auth {

    @Inject
    public Auth(){}

    public void logout(Context context){
        //Marking user role and uid as null
        SharedPreferencesHelper.setCurrentUid(context , "");
        SharedPreferencesHelper.setUserRole(context , "");
        context.startActivity(new Intent(context , MainActivity.class));
    }
}
