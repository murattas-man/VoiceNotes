package com.voicenote.voicenotes;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Not_Detay extends AppCompatActivity {

    SQLiteDatabase db;
    Veritabani mVeritabani;
    String saat,tarih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay_not);
        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));


        mVeritabani = new Veritabani(this);
        db = mVeritabani.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);
        TextView title = (TextView) findViewById(R.id.title);
        TextView detail = (TextView) findViewById(R.id.detail);
        TextView notetype = (TextView) findViewById(R.id.note_type_ans);
        TextView time = (TextView) findViewById(R.id.alertvalue);
        TextView date = (TextView) findViewById(R.id.datevalue);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                title.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.TITLE)));
                detail.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.DETAIL)));
                notetype.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.TYPE)));
                time.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.TIME)));
                date.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.DATE)));
                saat=cursor.getString(cursor.getColumnIndex(mVeritabani.TIME));
                tarih=cursor.getString(cursor.getColumnIndex(mVeritabani.DATE));

            }
            cursor.close();
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
        final long id = getIntent().getExtras().getLong(getString(R.string.rowID));

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
