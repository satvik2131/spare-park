package com.example.sparespark.view.admin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.sparespark.R;
import com.example.sparespark.databinding.AdminLoginBinding;
import com.example.sparespark.utils.Auth;

import javax.inject.Inject;

public class AdminLogin extends AppCompatActivity {


    @Inject
    Auth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdminLoginBinding adminloginBinding = DataBindingUtil.setContentView(this, R.layout.admin_login);
        adminloginBinding.setAdminLogin(new Auth());
        adminloginBinding.executePendingBindings();
    }
}
