package com.project.segunfrancis.citizenmarch.networkutils;

/**
 * Created by SegunFrancis
 */
public interface ImageUpload {
        void onSuccess(String imageUrl);

        void onError(Exception e);
}
