package com.example.sparespark.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sparespark.R;
import com.example.sparespark.model.UserBookRequest;
import com.example.sparespark.view.parking_owner.PhoneAuth;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class FirebaseDatabaseOperations {
    DatabaseReference reference;
    Marker marker;
    public String tenant_name, phoneno, timing, address;
    public TextView tenant_nameTV, phonenoTV, timingTV, addressTV;


    @Inject
    public FirebaseDatabaseOperations() {
        reference = FirebaseDatabase.getInstance().getReference();
    }


    //Google Maps and Firebase Related Operations
    //***************************************************************************************************************
    //Points all marker with icon and additional details
    public void getAllMapsMarkers(GoogleMap googleMap, Context context) {
        reference.child("location_requests").orderByChild("status").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    double latitude = snap.child("latitude").getValue(Double.class);
                    double longitude = snap.child("longitude").getValue(Double.class);

                    //Add a condition of status is false means not available for booking

                    //Setting the icon size
                    //************************************************************************/
                    int height = 150;
                    int width = 150;
                    BitmapDrawable bitmapdraw = (BitmapDrawable) context.getResources().getDrawable(R.drawable.location);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    //************************************************************************/

                    //Setting lrid to the marker for booking feature
                    LatLng ll = new LatLng(latitude, longitude);
                    marker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).position(ll).title("Available For booking"));
                    marker.setTag(snap.child("lrid").getValue(String.class));
                }


                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        if(SharedPreferencesHelper.getUserRole(context).equals("Owner")!=true){
                            tapOnMarker(marker, context);
                        }
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Booking a slot
    private void tapOnMarker(Marker marker, Context context) {
        String lrid = marker.getTag().toString();
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.space_details_card, null);

        reference.child("location_requests").child(lrid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Taking values from database
                tenant_name = snapshot.child("tenant_name").getValue(String.class);
                phoneno = snapshot.child("phoneno").getValue(String.class);
                timing = snapshot.child("timing").getValue(String.class);
                address = snapshot.child("address").getValue(String.class);


                //Finding the view textviews
                tenant_nameTV = customView.findViewById(R.id.u_tenant_name);
                phonenoTV = customView.findViewById(R.id.u_phoneno);
                timingTV = customView.findViewById(R.id.u_timing);
                addressTV = customView.findViewById(R.id.u_address);

                //Assigning the text views the values
                tenant_nameTV.setText(tenant_name);
                phonenoTV.setText(phoneno);
                timingTV.setText(timing);
                addressTV.setText(address);


                //Building the dialog box
                new MaterialAlertDialogBuilder(context)
                        .setView(customView)
                        .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkUserAuth(context, lrid, address, tenant_name, phoneno, timing);
                            }
                        })
                        .show().create();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Checks if user is logged in or not
    //And redirects accordingly
    private void checkUserAuth(Context context,
                               String lrid,
                               String tenant_address,
                               String tenant_name,
                               String tenant_phoneno,
                               String timing) {
        String userrole = SharedPreferencesHelper.getUserRole(context);
        String userID = SharedPreferencesHelper.getCurrentUid(context);
        if (userrole.equals("") != true && userID.equals("") != true) {

            reference.child("User").child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String username = snapshot.child("name").getValue(String.class);
                    String phoneno = snapshot.child("phoneno").getValue(String.class);


                    UserBookRequest newRequest = new UserBookRequest(
                            lrid, tenant_name,
                            tenant_phoneno, tenant_address,
                            timing, username,
                            phoneno, false,
                            userID);
                    reference.child("user_book_request_location").child(lrid).setValue(newRequest);
                    Toast.makeText(context, "Request will appear in Booked space", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            context.startActivity(new Intent(context, PhoneAuth.class));
        }
    }

    //***************************************************************************************************************


}
