package com.example.sparespark.view.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sparespark.R;
import com.example.sparespark.utils.Auth;
import com.example.sparespark.view.common.RequestedLocationFragment;
import com.example.sparespark.view.parking_owner.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AdminHome extends AppCompatActivity {
    BottomNavigationView navigationView;
    FragmentManager fragmentManager;

    @Inject
    Auth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
        }

        // Map fragment
        Fragment mapsFragment = new MapsFragment();

        //Default Fragment
        fragmentManager.beginTransaction().replace(R.id.admin_fragment, RequestedLocationFragment.class, null).commit();


        navigationView = findViewById(R.id.admin_bottom_nav);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getTitle().toString()) {
                    case "Approve/Delete":
                        item.setChecked(true);
                        fragmentManager.beginTransaction()
                                .replace(R.id.admin_fragment, RequestedLocationFragment.class, null).commit();
                        break;

                    case "Update":
                        item.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.admin_fragment, UpdateRequestLocationFragment.class,null).commit();
                        break;

                    case "Insert Custom":
                        item.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.admin_fragment, mapsFragment).commit();
                        break;
                    case "Logout":
                        auth.logout(AdminHome.this);
                }

                return false;
            }
        });
    }
}
