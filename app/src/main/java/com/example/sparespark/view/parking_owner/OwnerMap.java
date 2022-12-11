//package com.example.sparespark.view.parking_owner;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//
//import com.example.sparespark.R;
//import com.example.sparespark.databinding.OwnerMapBinding;
//import com.example.sparespark.model.LocationModel;
//import com.example.sparespark.utils.Auth;
//import com.example.sparespark.utils.FirebaseDatabaseOperations;
//import com.example.sparespark.utils.SharedPreferencesHelper;
//import com.example.sparespark.view.parking_owner.adapter.RequestLocationAdapter;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.dialog.MaterialAlertDialogBuilder;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.navigation.NavigationBarView;
//
//import javax.inject.Inject;
//
//import dagger.hilt.android.AndroidEntryPoint;
//
//
//@AndroidEntryPoint
//public class OwnerMap extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private OwnerMapBinding binding;
//    Location currentLocation;
//    FusedLocationProviderClient fusedLocationProviderClient;
//    private static final int REQUEST_CODE = 101;
//    FragmentManager fragmentManager;
//
//
//    public EditText tenant_name, address, phoneno, timing;
//
//    @Inject
//    FirebaseDatabaseOperations operations;
//    @Inject
//    Auth auth;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (savedInstanceState == null) {
//            fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().add(R.id.map , SupportMapFragment.class , null).commit();
//        }
//
//        binding = OwnerMapBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fetchLocation();
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.owner_bottom_nav);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getTitle().toString()){
//                    case "Home":
//                        item.setChecked(true);
//                        fragmentManager
//                                .beginTransaction()
//                                .replace(R.id.map, SupportMapFragment.class, null)
//                                .setReorderingAllowed(true)
//                                .commit();
//                        break;
//
//                    case "Requested Location":
//                        Toast.makeText(OwnerMap.this, "Request krra ha", Toast.LENGTH_SHORT).show();
//                        item.setChecked(true);
//                        fragmentManager
//                                .beginTransaction()
//                                .replace(R.id.map, RequestedLocationFragment.class, null)
//                                .setReorderingAllowed(true)
//                                .commit();
//                        break;
//
//                    case "Logout":
//                        auth.logout(getApplicationContext());
//                        break;
//                }
//                return false;
//            }
//        });
//
//
//        //For refreshing the map
//        FloatingActionButton refreshButton = findViewById(R.id.refresh_button);
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext() , OwnerMap.class));
//            }
//        });
//    }
//
//
//
//
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Zooms the camera to the current location
//        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(latLng).title("I am here"));
//
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(17).tilt(40).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(@NonNull LatLng latLng) {
//                onMapListener(latLng);
//            }
//        });
//    }
//
//
//
//}