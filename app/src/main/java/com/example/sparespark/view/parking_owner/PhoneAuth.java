package com.example.sparespark.view.parking_owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sparespark.model.AllUserRoleModel;
import com.example.sparespark.utils.SharedPreferencesHelper;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class PhoneAuth extends AppCompatActivity {

    DatabaseReference reference;
    String name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reference = FirebaseDatabase.getInstance().getReference();

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());

        //Dialog box to get username
        loginPage(providers);

    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(), new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
        @Override
        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
            onSignInResult(result);
        }
    });

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            //Result code
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            //GETTING USER_ROLE
            String user_role = SharedPreferencesHelper.getUserRole(this);

            SharedPreferencesHelper.setCurrentUid(this , uid);
            AllUserRoleModel newUser = new AllUserRoleModel(name , user.getPhoneNumber() , uid);
            //NEW user added
            reference.child(user_role).child(uid).setValue(newUser);
            //....Remaining -- write redirection code

        } else {
            Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void loginPage(List<AuthUI.IdpConfig> providers) {
        AlertDialog.Builder boite = new MaterialAlertDialogBuilder(this);
        boite.setTitle("Enter Your Name");

        final TextInputEditText input = new TextInputEditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        boite
            .setView(input)
            .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (input.toString().isEmpty()) {
                        Toast.makeText(PhoneAuth.this, "Enter name", Toast.LENGTH_SHORT).show();
                    } else {
                        name = input.getText().toString();

                        //SIGN IN UI
                        // Create and launch sign-in UI
                        Intent signInIntent = AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(providers)
                                .build();

                        signInLauncher.launch(signInIntent);
                    }
                }
            }).show();
    }
}
