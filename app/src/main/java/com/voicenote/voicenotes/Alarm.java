package com.voicenote.voicenotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class Alarm extends AppCompatActivity {
    AlarmController alarmController;
    String msg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        alarmController=new AlarmController(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String kontol=getIntent().getExtras().getString(getString(R.string.title_msg));
        if (kontol==null){
             msg=""+getString(R.string.app_name);
        }
        else {
             msg = "!" + getIntent().getExtras().getString(getString(R.string.title_msg));
        }
      alarmController.playSound();

        builder.setMessage(msg).setCancelable(
                false).setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       alarmController.stopSound();
                        Alarm.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}