package com.project.segunfrancis.citizenmarch.ui.marchdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
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

public class MarchDetailsActivity extends AppCompatActivity {

    private MarchDetailsViewModel mViewModel;
    private ExtendedFloatingActionButton mExtFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_march_details);

        mViewModel = new ViewModelProvider(this).get(MarchDetailsViewModel.class);
        mExtFab = findViewById(R.id.attend_fab);
        final List<String> attendees;
        User currentUser = new User();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(MARCHES_DATABASE_REFERENCE);
        currentUser.setUserId(auth.getCurrentUser().getUid());
        currentUser.setProfilePhotoUrl(auth.getCurrentUser().getPhotoUrl().toString());
        currentUser.setName(auth.getCurrentUser().getDisplayName());

        setFabStatus();

        Intent intent = getIntent();
        if (intent != null) {
            populateLayout((March) intent.getSerializableExtra(HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT));
            March march = (March) intent.getSerializableExtra(HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT);
            attendees = march.getAttendees();
            mExtFab.setOnClickListener(view -> {
                mViewModel.getIsAttending().observe(this, isAttending -> {
                    if (!isAttending) {
                        attendees.add(currentUser.getUserId());
                        reference.child(march.getMarchId()).child(ATTENDEES_DATABASE_REFERENCE).setValue(attendees);
                        mViewModel.setIsAttending(true);
                    } else { // User no longer wants to attend
                        attendees.remove(currentUser.getUserId());
                        reference.child(march.getMarchId()).child(ATTENDEES_DATABASE_REFERENCE).setValue(attendees);
                        mViewModel.setIsAttending(false);
                    }
                });
            });

            if (attendees != null) {
                if (attendees.contains(currentUser.getUserId())) { // User is already attending
                    mViewModel.setIsAttending(true);
                } else { // User isn't attending
                    mViewModel.setIsAttending(false);
                }
            }
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

    private void setFabStatus() {
        mViewModel.getIsAttending().observe(this, isAttending -> {
            if (isAttending) {
                mExtFab.setText("Attending");
                mExtFab.setBackgroundColor(getResources().getColor(R.color.colorFabAttending));
            } else {
                mExtFab.setText("Attend");
                mExtFab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }
}