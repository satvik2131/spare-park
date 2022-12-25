package com.example.sparespark.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sparespark.MainActivity;
import com.example.sparespark.model.UserBookRequest;
import com.example.sparespark.view.admin.AdminHome;
import com.example.sparespark.view.parking_owner.PhoneAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class Auth {
    public String username, password;

    @Inject
    public Auth() {
    }


    //Admin Login
    public void adminLogin(Context context) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("admin").child("credentials").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dbUsername = snapshot.child("username").getValue(String.class);
                String dbPassword = snapshot.child("password").getValue(String.class);
                boolean finalResult = dbUsername.equals(username) && dbPassword.equals(password) ? true : false;
                Log.d("FinalResult -", String.valueOf(finalResult));

                if (finalResult) {
                    //Authenticated
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();

                    //Updating user role and setting uid for authorization
                    SharedPreferencesHelper.setUserRole(context, "Admin");
                    SharedPreferencesHelper.setCurrentUid(context, "Admin11");


                    context.startActivity(new Intent(context, AdminHome.class));

                } else {
                    //Wrong Credentials
                    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Checks if user is logged in or not
    //And redirects accordingly
    public void checkUserAuth(Context context) {
        String userrole = SharedPreferencesHelper.getUserRole(context);
        String userID = SharedPreferencesHelper.getCurrentUid(context);
        if (userrole.equals("") ) {
            context.startActivity(new Intent(context, MainActivity.class));
        }

        if(userID.equals("")){
            context.startActivity(new Intent(context, PhoneAuth.class));
        }

    }




    //All User logout method
    public void logout(Context context) {
        //Marking user role and uid as null
        SharedPreferencesHelper.setCurrentUid(context, "");
        SharedPreferencesHelper.setUserRole(context, "");
        //Signout from firebase instance as well
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
        }

        context.startActivity(new Intent(context, MainActivity.class));
    }
}
