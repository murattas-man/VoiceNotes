package com.voicenote.voicenotes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    public static String Tikla="com.voicenote.voicenotes.AppWidget.Tikla";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        Intent ıntent=new Intent(Tikla);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.notwidget,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public  void onReceive(Context context,Intent ıntent){
        super.onReceive(context, ıntent);
        if(Tikla.equals(ıntent.getAction())){
            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            Intent intentnew = new Intent(context,NotAl.class);
            intentnew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentnew.putExtra("sesKontrol",true);
            context.startActivity(intentnew);

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
}

