package com.example.sparespark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sparespark.utils.SharedPreferencesHelper;
import com.example.sparespark.view.admin.AdminHome;
import com.example.sparespark.view.admin.AdminLogin;
import com.example.sparespark.view.parking_owner.OwnerHome;
import com.example.sparespark.view.parking_owner.PhoneAuth;
import com.example.sparespark.view.user.UserHome;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validateUser();
    }


    public void validateUser(){
        String user_role = SharedPreferencesHelper.getUserRole(this);
        String uid = SharedPreferencesHelper.getCurrentUid(this);

        if(user_role.isEmpty()!=true && uid.isEmpty()!=true){
            switch (user_role) {
                case "Owner":
                    startActivity(new Intent(this, OwnerHome.class));
                    break;
                case "User":
                    startActivity(new Intent(this, UserHome.class));
                    break;
                case "Admin":
                    startActivity(new Intent(this, AdminHome.class));
                    break;
            }
        }
    }

    public void moveToOwner(View view) {
        SharedPreferencesHelper.setUserRole(this , "Owner");
        startActivity(new Intent(this ,PhoneAuth.class));
    }

    public void moveToUser(View view) {
        SharedPreferencesHelper.setUserRole(this , "User");
        startActivity(new Intent(this , UserHome.class));
    }

    public void moveToAdmin(View view) {
        SharedPreferencesHelper.setUserRole(this , "Admin");
        startActivity(new Intent(this, AdminLogin.class));
    }
}