package com.example.sparespark.view.user;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sparespark.R;
import com.example.sparespark.utils.Auth;
import com.example.sparespark.view.admin.UpdateRequestLocationFragment;
import com.example.sparespark.view.parking_owner.MapsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserHome extends AppCompatActivity {
    BottomNavigationView navigationView;
    FragmentManager fragmentManager;

    @Inject
    Auth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
        }

        // Map fragment
        Fragment userMapsFragment = new UserMapsFragment();

        //Default Fragment
        fragmentManager.beginTransaction().replace(R.id.user_frag, userMapsFragment).commit();


        //Botttom navigation listener
        navigationView = findViewById(R.id.user_bottom_nav);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getTitle().toString()) {
                    case "Book Space":
                        item.setChecked(true);
                        fragmentManager.beginTransaction()
                                .replace(R.id.user_frag,userMapsFragment).commit();
                        break;

                    case "Booked Space":
                        item.setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.user_frag, BookedSpacesFrag.class,null).commit();
                        break;


                    case "Logout":
                        auth.logout(UserHome.this);
                }

                return false;
            }
        });




    }
}
