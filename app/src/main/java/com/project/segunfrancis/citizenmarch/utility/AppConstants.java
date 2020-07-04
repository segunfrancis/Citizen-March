package com.project.segunfrancis.citizenmarch.utility;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by SegunFrancis
 */
public final class AppConstants {
    public final static int RC_SIGN_IN = 201;
    public final static String USERS_DATABASE_REFERENCE = "users";
    public final static String MARCHES_DATABASE_REFERENCE = "marches";
    public final static String FIREBASE_STORAGE_REFERENCE = "image_location/";
    public final static String ATTENDEES_DATABASE_REFERENCE = "attendees";
    public final static int MARCH_IMAGE_REQUEST_CODE = 123;
    public final static String HOME_FRAGMENT_TO_DETAIL_ACTIVITY_INTENT = "com.project.segunfrancis.citizenmarch.INTENT_KEY";
    public final static String ATTENDING_SHARED_PREF_KEY = "com.project.segunfrancis.citizenmarch.ATTENDING";
    public final static String WIDGET_SHARED_PREF_KEY = "com.project.segunfrancis.citizenmarch.APP_WIDGET";
    public final static String MARCH_TITLE_PREF_KEY = "march_title";
    public final static String MARCH_VENUE_PREF_KEY = "march_location";
    public final static String MARCH_TIME_PREF_KEY = "march_time";

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
