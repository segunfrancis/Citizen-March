package com.project.segunfrancis.citizenmarch.ui.marchdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.pojo.March;

import static com.project.segunfrancis.citizenmarch.utility.AppConstants.HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT;

public class MarchDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_march_details);

        ExtendedFloatingActionButton extFab = findViewById(R.id.attend_fab);

        Intent intent = getIntent();
        if (intent != null) {
            populateLayout((March) intent.getSerializableExtra(HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT));
        } else {
            finish();
        }
    }

    private void populateLayout(March march) {
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
}
