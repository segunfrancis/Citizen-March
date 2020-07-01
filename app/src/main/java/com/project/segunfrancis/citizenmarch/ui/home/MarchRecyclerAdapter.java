package com.project.segunfrancis.citizenmarch.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.pojo.March;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SegunFrancis
 */
public class MarchRecyclerAdapter extends
        RecyclerView.Adapter<MarchRecyclerAdapter.ViewHolder> {

    private List<March> mMarchList;
    private OnItemClickListener onItemClickListener;

    MarchRecyclerAdapter(List<March> marches, OnItemClickListener onItemClickListener) {
        this.mMarchList = marches;
        this.onItemClickListener = onItemClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        void bind(final March march, final OnItemClickListener listener) {
            TextView titleTextView = itemView.findViewById(R.id.march_title_TV);
            TextView descriptionTextView = itemView.findViewById(R.id.march_description_TV);
            TextView locationTextView = itemView.findViewById(R.id.march_location_TV);
            ImageView marchImage = itemView.findViewById(R.id.march_imageView);
            titleTextView.setText(march.getTitle());
            descriptionTextView.setText(march.getDescription());
            locationTextView.setText(march.getLocation());
            Glide.with(marchImage.getContext())
                    .load(march.getMarchPhotoUrl())
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image_24dp)
                    .into(marchImage);
            itemView.setOnClickListener(v -> listener.onItemClick(march));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_march_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        March item = mMarchList.get(position);

        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mMarchList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(March march);
    }
}