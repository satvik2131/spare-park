package com.example.sparespark.viewmodels;

import android.content.Context;
import android.util.Log;


import androidx.lifecycle.ViewModel;

import com.example.sparespark.utils.SharedPreferencesHelper;

public class ButtonValidationVM extends ViewModel {


    //For Approving and rejecting user request
    public boolean getUserValidation(Context context){
        String typeofuser = SharedPreferencesHelper.getUserRole(context);
        boolean result = typeofuser.equals("Admin");
        if(result){
            return true;
        }else{
            return false;
        }
    }

}
