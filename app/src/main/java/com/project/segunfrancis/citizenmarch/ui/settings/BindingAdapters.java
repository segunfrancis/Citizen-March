package com.project.segunfrancis.citizenmarch.ui.settings;

import com.bumptech.glide.Glide;
import com.project.segunfrancis.citizenmarch.R;

import androidx.databinding.BindingAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SegunFrancis
 */
public class BindingAdapters {

    @BindingAdapter("loadImage")
    public static void loadProfileImage(CircleImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image_24dp)
                .into(imageView);
    }
}
