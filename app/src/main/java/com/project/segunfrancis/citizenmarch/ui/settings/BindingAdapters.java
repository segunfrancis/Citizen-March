package com.project.segunfrancis.citizenmarch.ui.settings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.project.segunfrancis.citizenmarch.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import androidx.databinding.BindingAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SegunFrancis
 */
public class BindingAdapters {

    @BindingAdapter("loadImage")
    public static void loadProfileImage(CircleImageView imageView, String url) {
        imageView.setTag(url);
        new DownloadImageTask().execute(imageView);
    }

    public static class DownloadImageTask extends AsyncTask<CircleImageView, Void, Bitmap> {
        CircleImageView imageView = null;

        @Override
        protected Bitmap doInBackground(CircleImageView... imageViews) {
            this.imageView = imageViews[0];
            return downloadImageFromUrl((String) imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        private Bitmap downloadImageFromUrl(String src) {
            try {
                URL url = new URL(src);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                Log.e("DownloadImageTask", e.getLocalizedMessage());
                return null;
            }
        }
    }
}
