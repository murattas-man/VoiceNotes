package com.voicenote.voicenotes;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Not_Detay extends AppCompatActivity {

    SQLiteDatabase db;
    Veritabani mVeritabani;
    String saat,tarih;
    private int shepArka;
    private int shepYazi;

    TextView txt_title;
    TextView txt_detail;
    TextView txt_type;
    ImageView ımageView;
    TextView txt_Time;
    TextView txt_Date;

    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay_not);
         relativeLayout=findViewById(R.id.relativelayout);

        Bundle bundle = getIntent().getExtras();
        final long id=bundle.getInt("idNot");
       // final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        mVeritabani = new Veritabani(this);
        db = mVeritabani.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);
         txt_title = (TextView) findViewById(R.id.title);
         txt_detail = (TextView) findViewById(R.id.detail);
         txt_type = (TextView) findViewById(R.id.note_type_ans);
         txt_Time = (TextView) findViewById(R.id.alertvalue);
         txt_Date = (TextView) findViewById(R.id.datevalue);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                txt_title.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.TITLE)));
                txt_detail.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.DETAIL)));
                txt_type.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.TYPE)));
                txt_Time.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.TIME)));
                txt_Date.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.DATE)));
                saat=cursor.getString(cursor.getColumnIndex(mVeritabani.TIME));
                tarih=cursor.getString(cursor.getColumnIndex(mVeritabani.DATE));
                shepArka=cursor.getInt(cursor.getColumnIndex(mVeritabani.ARKAPLAN));
                shepYazi=cursor.getInt(cursor.getColumnIndex(mVeritabani.RENKKODU));
                arkaPlan(shepArka);
                renkYazi(shepYazi);
            }
            cursor.close();
        }
    }

    private void renkYazi(int shepYazi) {
        switch (shepYazi){

            case 0:
                txt_detail.setTextColor(Color.WHITE);
                txt_type.setTextColor(Color.WHITE);
                txt_Time.setTextColor(Color.WHITE);
                txt_Date.setTextColor(Color.WHITE);
                txt_title.setTextColor(Color.WHITE);
                break;
            case 1:
                txt_detail.setTextColor(Color.parseColor("#FFEB3B"));
                txt_title.setTextColor(Color.parseColor("#FFEB3B"));
                txt_Date.setTextColor(Color.parseColor("#FFEB3B"));
                txt_Time.setTextColor(Color.parseColor("#FFEB3B"));
                txt_type.setTextColor(Color.parseColor("#FFEB3B"));
                break;
            case 2:
                txt_detail.setTextColor(Color.parseColor("#FF5722"));
                txt_Time.setTextColor(Color.parseColor("#FF5722"));
                txt_type.setTextColor(Color.parseColor("#FF5722"));
                txt_Date.setTextColor(Color.parseColor("#FF5722"));
                txt_title.setTextColor(Color.parseColor("#FF5722"));
                break;
            case 3:
                txt_detail.setTextColor(Color.parseColor("#FF3D00"));
                txt_Date.setTextColor(Color.parseColor("#FF3D00"));
                txt_title.setTextColor(Color.parseColor("#FF3D00"));
                txt_type.setTextColor(Color.parseColor("#FF3D00"));
                txt_Time.setTextColor(Color.parseColor("#FF3D00"));
                break;
            case 4:
                txt_detail.setTextColor(Color.parseColor("#DD2C00"));
                txt_type.setTextColor(Color.parseColor("#DD2C00"));
                txt_Time.setTextColor(Color.parseColor("#DD2C00"));
                txt_title.setTextColor(Color.parseColor("#DD2C00"));
                txt_Date.setTextColor(Color.parseColor("#DD2C00"));
                break;
            case 5:
                txt_detail.setTextColor(Color.parseColor("#7C4DFF"));
                txt_title.setTextColor(Color.parseColor("#7C4DFF"));
                txt_Date.setTextColor(Color.parseColor("#7C4DFF"));
                txt_Time.setTextColor(Color.parseColor("#7C4DFF"));
                txt_type.setTextColor(Color.parseColor("#7C4DFF"));
                break;
            case 6:
                txt_detail.setTextColor(Color.parseColor("#3F51B5"));
                txt_Time.setTextColor(Color.parseColor("#3F51B5"));
                txt_type.setTextColor(Color.parseColor("#3F51B5"));
                txt_Date.setTextColor(Color.parseColor("#3F51B5"));
                txt_title.setTextColor(Color.parseColor("#3F51B5"));
                break;
            case 7:
                txt_detail.setTextColor(Color.parseColor("#2196F3"));
                txt_Date.setTextColor(Color.parseColor("#2196F3"));
                txt_title.setTextColor(Color.parseColor("#2196F3"));
                txt_Time.setTextColor(Color.parseColor("#2196F3"));
                txt_type.setTextColor(Color.parseColor("#2196F3"));
                break;
            case 8:
                txt_detail.setTextColor(Color.parseColor("#000000"));
                txt_type.setTextColor(Color.parseColor("#000000"));
                txt_Time.setTextColor(Color.parseColor("#000000"));
                txt_title.setTextColor(Color.parseColor("#000000"));
                txt_Date.setTextColor(Color.parseColor("#000000"));
                break;
            case 9:
                txt_detail.setTextColor(Color.parseColor("#4E342E"));
                txt_Time.setTextColor(Color.parseColor("#4E342E"));
                txt_Date.setTextColor(Color.parseColor("#4E342E"));
                txt_title.setTextColor(Color.parseColor("#4E342E"));
                txt_type.setTextColor(Color.parseColor("#4E342E"));
                break;
            case 10:
                txt_detail.setTextColor(Color.parseColor("#616161"));
                txt_type.setTextColor(Color.parseColor("#616161"));
                txt_Time.setTextColor(Color.parseColor("#616161"));
                txt_title.setTextColor(Color.parseColor("#616161"));
                txt_Date.setTextColor(Color.parseColor("#616161"));
                break;
            case 11:
                txt_detail.setTextColor(Color.parseColor("#C6FF00"));
                txt_Time.setTextColor(Color.parseColor("#C6FF00"));
                txt_Date.setTextColor(Color.parseColor("#C6FF00"));
                txt_title.setTextColor(Color.parseColor("#C6FF00"));
                txt_type.setTextColor(Color.parseColor("#C6FF00"));
                break;

        }
    }

    private void arkaPlan(int shepArka) {
        switch (shepArka)
        {
            case 0:
                relativeLayout.setBackgroundColor(Color.WHITE);
                break;
            case 1:
                relativeLayout.setBackgroundColor(Color.parseColor("#FFEB3B"));
                break;
            case 2:
                relativeLayout.setBackgroundColor(Color.parseColor("#FF5722"));
                break;
            case 3:
                relativeLayout.setBackgroundColor(Color.parseColor("#FF3D00"));
                break;
            case 4:
                relativeLayout.setBackgroundColor(Color.parseColor("#DD2C00"));
                break;
            case 5:
                relativeLayout.setBackgroundColor(Color.parseColor("#7C4DFF"));
                break;
            case 6:
                relativeLayout.setBackgroundColor(Color.parseColor("#3F51B5"));
                break;
            case 7:
                relativeLayout.setBackgroundColor(Color.parseColor("#2196F3"));
                break;
            case 8:
                relativeLayout.setBackgroundColor(Color.parseColor("#000000"));
                break;
            case 9:
                relativeLayout.setBackgroundColor(Color.parseColor("#4E342E"));
                break;
            case 10:
                relativeLayout.setBackgroundColor(Color.parseColor("#616161"));
                break;
            case 11:
                relativeLayout.setBackgroundColor(Color.parseColor("#C6FF00"));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundle = getIntent().getExtras();
        final long id=bundle.getInt("idNot");

        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                return true;
            case R.id.action_edit:

                Intent openEditNote = new Intent(Not_Detay.this, Duzenle_Not.class);
                openEditNote.putExtra(getString(R.string.intent_row_id), id);
                openEditNote.putExtra("saat",saat);
                openEditNote.putExtra("tarih",tarih);
                startActivity(openEditNote);
                return true;

            case R.id.action_discard:
                AlertDialog.Builder builder = new AlertDialog.Builder(Not_Detay.this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);
                                if (cursor != null){
                                    if (cursor.moveToFirst()) {
                                        final int ID=(int) cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARMKON));
                                        if(ID!=1){
                                            //silinen not alarmlı not ise alarmı iptal et
                                           alarmIptal(ID);
                                        }
                                    }
                                    cursor.close();
                                }

                                db.delete(Veritabani.TABLE_NAME, Veritabani.C_ID + "=" + id, null);
                                db.close();
                                Intent openMainActivity = new Intent(Not_Detay.this, MainActivity.class);
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)                        //Do nothing on no
                        .show();
                return true;

            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void alarmIptal(final int ID){
        //alarm notu silinirse alarmı iptal etme

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), ID, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

}
