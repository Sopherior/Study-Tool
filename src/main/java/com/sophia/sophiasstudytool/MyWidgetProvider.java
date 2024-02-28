package com.sophia.sophiasstudytool;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Iterate over all instances of the widget
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // Set up a click intent to open the website
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.testprepreview.com/"));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            // Attach the click listener to the button
            views.setOnClickPendingIntent(R.id.widgetOpenButton, pendingIntent);

            // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // code for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // code for when the last widget is disabled
    }
}

