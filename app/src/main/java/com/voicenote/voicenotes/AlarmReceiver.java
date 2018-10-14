package com.voicenote.voicenotes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Calendar;

/**
 * Created by Dominic on 13/04/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    Veritabani mVeritabani;
    SQLiteDatabase db;
    int YIL,AY,GUN,SAAT,DAKIKA,idAlarm;
    String title="";
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()))
        {
            mVeritabani=new Veritabani(context);
            db= mVeritabani.getWritableDatabase();
            String[] from = {mVeritabani.ALARM_TITLE,mVeritabani.ALARM_KONTROL, mVeritabani.ALARM_YEAR,mVeritabani.ALARM_MONTH,mVeritabani.ALARM_DAY,
                    mVeritabani.ALARM_HOUR,mVeritabani.ALARM_MINUTE};
            final String[] column = {mVeritabani.A_ID, mVeritabani.ALARM_TITLE,mVeritabani.ALARM_KONTROL, mVeritabani.ALARM_YEAR,
                    mVeritabani.ALARM_MONTH,mVeritabani.ALARM_DAY,mVeritabani.ALARM_HOUR,mVeritabani.ALARM_MINUTE};
            final Cursor cursor = db.query(mVeritabani.TABLE_ALARM, column, null, null ,null, null, null);

            while (cursor.moveToNext())
            {
                YIL = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_YEAR));
                AY  = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_MONTH));
                GUN = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_DAY));
                SAAT = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_HOUR));
                DAKIKA  = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_MINUTE));
                title = cursor.getString(cursor.getColumnIndex(mVeritabani.ALARM_TITLE));
                idAlarm  = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_KONTROL));

                Calendar calender = Calendar.getInstance();
                calender.clear();
                calender.set(Calendar.MONTH, AY);
                calender.set(Calendar.DAY_OF_MONTH,GUN);
                calender.set(Calendar.YEAR, YIL);
                calender.set(Calendar.HOUR,SAAT);
                calender.set(Calendar.MINUTE, DAKIKA);
                calender.set(Calendar.SECOND, 00);
                final Calendar takvim = Calendar.getInstance();
                long farkZaman=Calendar.getInstance().getTimeInMillis()-calender.getTimeInMillis();
                if(farkZaman>0){

                }else
                {
                    //kurulu olmayan alarm var ise
                    //Toast.makeText(context,"girilen tarihte alarm olabilir",Toast.LENGTH_SHORT).show();
                    AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                    Intent a_intent = new Intent(context, AlarmReceiver.class);
                    a_intent.putExtra(context.getString(R.string.alert_title), title);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarm, a_intent, 0);
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

                }
            }

        }
        else {//Toast.makeText(context, context.getString(R.string.Alertnotifty) + intent.getStringExtra("title") , Toast.LENGTH_LONG).show();
            String Title = intent.getStringExtra(context.getString(R.string.titttle));
            Intent x = new Intent(context, Alarm.class);
            x.putExtra(context.getString(R.string.titttle), Title);
            x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(x);
        }

    }
}