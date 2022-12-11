package com.example.sparespark.view.parking_owner;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.sparespark.R;
import com.google.android.gms.maps.MapFragment;

public class OwnerHome extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_home);

        // Initialize fragment
        Fragment fragment = new MapsFragment();

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.owner_home_frag,fragment)
                .commit();

    }
}
