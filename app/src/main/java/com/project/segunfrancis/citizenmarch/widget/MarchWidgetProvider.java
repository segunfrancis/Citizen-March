package com.project.segunfrancis.citizenmarch.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.project.segunfrancis.citizenmarch.R;
import com.project.segunfrancis.citizenmarch.ui.main.MainActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCH_TIME_PREF_KEY;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCH_TITLE_PREF_KEY;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.MARCH_VENUE_PREF_KEY;
import static com.project.segunfrancis.citizenmarch.utility.AppConstants.WIDGET_SHARED_PREF_KEY;

/**
 * Implementation of App Widget functionality.
 */
public class MarchWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        SharedPreferences preferences = context.getSharedPreferences(WIDGET_SHARED_PREF_KEY, MODE_PRIVATE);
        String marchTitle = preferences.getString(MARCH_TITLE_PREF_KEY, context.getResources().getString(R.string.empty_march));
        String marchLocation = preferences.getString(MARCH_VENUE_PREF_KEY, "");
        String marchTime = preferences.getString(MARCH_TIME_PREF_KEY, "");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.march_widget);
        views.setTextViewText(R.id.appwidget_text_march_title, marchTitle);
        views.setTextViewText(R.id.appwidget_text_march_location, marchLocation);
        views.setTextViewText(R.id.appwidget_text_march_time, marchTime);

        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_linearLayout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MarchWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            MarchWidgetProvider.updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        SharedPreferences preferences = context.getSharedPreferences(WIDGET_SHARED_PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(MARCH_TITLE_PREF_KEY);
        editor.remove(MARCH_VENUE_PREF_KEY);
        editor.remove(MARCH_TIME_PREF_KEY);
        editor.apply();
    }
}

