package com.project.segunfrancis.citizenmarch.ui.marchdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.pojo.March;
import com.project.segunfrancis.citizenmarch.pojo.User;

import java.util.List;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.ATTENDEES_DATABASE_REFERENCE;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCHES_DATABASE_REFERENCE;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.SHARED_PREF_KEY;

public class MarchDetailsActivity extends AppCompatActivity {

    private MarchDetailsViewModel mViewModel;
    private ExtendedFloatingActionButton mExtFab;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_march_details);

        mPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        mViewModel = new ViewModelProvider(this).get(MarchDetailsViewModel.class);
        mExtFab = findViewById(R.id.attend_fab);
        final List<String> attendees;
        User currentUser = new User();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
        currentUser.setUserId(auth.getCurrentUser().getUid());
        currentUser.setProfilePhotoUrl(auth.getCurrentUser().getPhotoUrl().toString());
        currentUser.setName(auth.getCurrentUser().getDisplayName());

        Intent intent = getIntent();
        if (intent != null) {
            populateLayout((March) intent.getSerializableExtra(HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT));
            March march = (March) intent.getSerializableExtra(HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT);
            attendees = march.getAttendees();
            setFabStatus(march);
            mExtFab.setOnClickListener(view -> {
                if (attendees.contains(currentUser.getUserId())) {
                    attendees.remove(currentUser.getUserId());
                    reference.child(march.getMarchId()).child(ATTENDEES_DATABASE_REFERENCE).setValue(attendees);
                    mExtFab.setText(getResources().getString(R.string.attend));
                    mExtFab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    editor.putBoolean(march.getMarchId(), false);
                    editor.apply();
                } else {
                    attendees.add(currentUser.getUserId());
                    reference.child(march.getMarchId()).child(ATTENDEES_DATABASE_REFERENCE).setValue(attendees);
                    mExtFab.setText(getResources().getString(R.string.attending));
                    mExtFab.setBackgroundColor(getResources().getColor(R.color.colorFabAttending));
                    editor.putBoolean(march.getMarchId(), true);
                    editor.apply();
                }
            });
        } else {
            finish();
        }
    }

    private void populateLayout(March march) {
        mViewModel.setMarch(march);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(march.getTitle());
        }
        TextView title = findViewById(R.id.march_detail_title_TV);
        TextView description = findViewById(R.id.march_detail_description_TV);
        TextView location = findViewById(R.id.march_detail_location_TV);
        TextView hashtags = findViewById(R.id.march_detail_hashtag_TV);
        ImageView image = findViewById(R.id.march_detail_imageView);
        TextView createdBy = findViewById(R.id.march_detail_created_by_TV);
        TextView time = findViewById(R.id.march_detail_time_TV);
        TextView date = findViewById(R.id.march_detail_date_TV);

        title.setText(march.getTitle());
        description.setText(march.getDescription());
        location.setText(march.getLocation());
        hashtags.setText(march.getHashTags());
        createdBy.setText(march.getCreatedBy());
        time.setText(march.getTime());
        date.setText(march.getDate());
        Glide.with(this)
                .load(march.getMarchPhotoUrl())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image_24dp)
                .into(image);
    }

    private void setFabStatus(March march) {
        if (mPreferences.getBoolean(march.getMarchId(), false)) {
            mExtFab.setText(getResources().getString(R.string.attending));
            mExtFab.setBackgroundColor(getResources().getColor(R.color.colorFabAttending));
            Log.d("FabStatus", "Attending");
        } else {
            mExtFab.setText(getResources().getString(R.string.attend));
            mExtFab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Log.d("FabStatus", "Not Attending");
        }
    }
}
