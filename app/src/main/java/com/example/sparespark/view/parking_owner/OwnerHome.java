package com.example.sparespark.view.parking_owner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sparespark.R;
import com.example.sparespark.utils.Auth;
import com.example.sparespark.view.common.RequestedLocationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class OwnerHome extends AppCompatActivity {
    BottomNavigationView navigationView;
    FragmentManager fragmentManager;

    @Inject
    Auth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_home);

        // Map fragment
        Fragment mapsFragment = new MapsFragment();

        //RequestedLocation fragment
        Fragment requestedLocationFragment = new RequestedLocationFragment();

        fragmentManager = getSupportFragmentManager();

        // Open fragment
        fragmentManager.beginTransaction().replace(R.id.owner_home_frag, mapsFragment).commit();


        navigationView = findViewById(R.id.owner_bottom_nav);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()) {
                    case "Home":
                        item.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.owner_home_frag, mapsFragment).commit();
                        break;
                    case "Requested Location":
                        item.setChecked(true);
                        Log.d("changed --", item.getTitle().toString());
                        fragmentManager.beginTransaction().replace(R.id.owner_home_frag, requestedLocationFragment).commit();
                        break;

                    case "Requests":
                        item.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.owner_home_frag, RequestsUser.class,null).commit();
                        break;

                    case "Logout":
                        auth.logout(OwnerHome.this);
                        break;
                }

                return false;
            }
        });

    }

    public void refreshLayout(View view) {
        finish();
        startActivity(new Intent(this, OwnerHome.class));
    }

}
