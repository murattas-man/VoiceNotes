package com.voicenote.voicenotes;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Servis extends Service {
    Context context ;
    Notification notification;
    Timer timer;
    SQLiteDatabase db;
    Veritabani mVeritabani;

    @Override
    public IBinder onBind(Intent ıntent) {
        return null;
    }
    @Override
    public void onCreate() {//Servis startService(); metoduyla çağrıldığında çalışır
        context = getApplicationContext();

        timer = new Timer();
        timer.schedule(new TimerTask() {  //her 60 sn de bir kontrolet(); metodu çağırılır.
            @Override
            public void run() {
                alarmKontol();
            }

        }, 0, 30000);

    }
    public void alarmKontol(){
        mVeritabani = new Veritabani(this);
        db = mVeritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("select "+mVeritabani.ALARMKON+" from " + mVeritabani.TABLE_NAME , null);
        final Calendar takvim = Calendar.getInstance();
        final int _id = (int) System.currentTimeMillis();
        String detail="";

            while (cursor.moveToNext()){
               if ( takvim.getTimeInMillis()==cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARMKON))){
                   AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                   Intent intent = new Intent(this, AlarmReceiver.class);
                   String alertTitle = detail;

                   if(alertTitle.isEmpty()){
                       intent.putExtra(getString(R.string.alert_title),getString(R.string.app_name));
                   }else {
                       intent.putExtra(getString(R.string.alert_title), alertTitle);
                   }
                   PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _id, intent, 0);
                   alarmMgr.set(AlarmManager.RTC_WAKEUP, takvim.getTimeInMillis(), pendingIntent);
               }
    }
    }
}
