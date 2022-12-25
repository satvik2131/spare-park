package com.example.sparespark.view.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sparespark.R;
import com.example.sparespark.databinding.SpaceDetailsCardBinding;
import com.example.sparespark.utils.FirebaseDatabaseOperations;
import com.example.sparespark.view.parking_owner.MapsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class UserMapsFragment extends Fragment {
    public Location currentLocation;
    private static final int REQUEST_CODE = 102;


    @Inject
    FirebaseDatabaseOperations operations;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fetchLocation(getContext());

    }

    //Fetches current location of the user
    @SuppressLint("MissingPermission")
    public void fetchLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        // Get the LocationManager instance
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


        // Get the last known location from the provider
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }



    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            //Zooms to the current location you are in
            LatLng currLl = new LatLng(currentLocation.getLatitude() , currentLocation.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currLl).zoom(17).tilt(40).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            operations.getAllMapsMarkers(googleMap , getContext() );

        }
    };

}