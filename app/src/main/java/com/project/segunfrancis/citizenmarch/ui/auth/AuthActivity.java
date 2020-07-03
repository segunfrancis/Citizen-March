package com.project.segunfrancis.citizenmarch.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.segunfrancis.citizenmarch.pojo.User;
import com.project.segunfrancis.citizenmarch.ui.main.MainActivity;
import com.project.segunfrancis.citizenmarch.R;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.RC_SIGN_IN;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.USERS_DATABASE_REFERENCE;

public class AuthActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_auth);
        MaterialButton signInButton = findViewById(R.id.button_sign_in);
        mProgressBar = findViewById(R.id.auth_progress_bar);
        signInButton.setOnClickListener(view -> signIn());

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, connectionResult -> {

        }).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                hideProgressBar();
                displaySnackBar(e.getLocalizedMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USERS_DATABASE_REFERENCE);
                String userId = mAuth.getCurrentUser().getUid();
                User user = new User();
                user.setName(mAuth.getCurrentUser().getDisplayName());
                user.setProfilePhotoUrl(mAuth.getCurrentUser().getPhotoUrl().toString());
                user.setUserId(userId);
                reference.child(userId).setValue(user);
                finish();
            } else {
                hideProgressBar();
                displaySnackBar(task.getException().getLocalizedMessage());
            }
        });
    }

    private void signIn() {
        showProgressBar();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void displaySnackBar(String message) {
        Snackbar.make(findViewById(R.id.auth_constraintLayout), message, Snackbar.LENGTH_LONG).show();
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
