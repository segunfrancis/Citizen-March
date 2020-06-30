package com.project.segunfrancis.citizenmarch.networkutils;

/**
 * Created by SegunFrancis
 */
public interface AddMarchToDatabase {
    void onSuccess(String message);

    void onError(Exception e);
}