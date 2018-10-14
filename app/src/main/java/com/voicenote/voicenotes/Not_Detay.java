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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class Not_Detay extends AppCompatActivity {

    SQLiteDatabase db;
    Veritabani mVeritabani;
    String saat,tarih;
    private int shepArka;
    private int shepYazi;

    EditText txt_detail;
    TextView txt_type;
    ImageView ımageView;
    TextView txt_Time;
    TextView txt_Date;

    RelativeLayout relativeLayout;
    private static final String TAG = "Not_Detay";

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay_not);
         relativeLayout=findViewById(R.id.relativelayout);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Bundle bundle = getIntent().getExtras();
        final long id=bundle.getInt("idNot");
       // final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        mVeritabani = new Veritabani(this);
        db = mVeritabani.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);
         txt_detail = (EditText) findViewById(R.id.detail);
         txt_type = (TextView) findViewById(R.id.note_type_ans);
         txt_Time = (TextView) findViewById(R.id.alertvalue);
         txt_Date = (TextView) findViewById(R.id.datevalue);


        if (cursor != null) {
            if (cursor.moveToFirst()) {
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
                txt_detail.setTextColor(getResources().getColor(R.color.renk1txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk1txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk1txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk1txt));
                break;
            case 1:
                txt_detail.setTextColor(getResources().getColor(R.color.renk2txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk2txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk2txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk2txt));
                break;
            case 2:
                txt_detail.setTextColor(getResources().getColor(R.color.renk3txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk3txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk3txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk3txt));
                break;
            case 3:
                txt_detail.setTextColor(getResources().getColor(R.color.renk4txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk4txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk4txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk4txt));
                break;
            case 4:
                txt_detail.setTextColor(getResources().getColor(R.color.renk5txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk5txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk5txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk5txt));
                break;
            case 5:
                txt_detail.setTextColor(getResources().getColor(R.color.renk6txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk6txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk6txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk6txt));
                break;
            case 6:
                txt_detail.setTextColor(getResources().getColor(R.color.renk7txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk7txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk7txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk7txt));
                break;
            case 7:
                txt_detail.setTextColor(getResources().getColor(R.color.renk8txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk8txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk8txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk8txt));
                break;
            case 8:
                txt_detail.setTextColor(getResources().getColor(R.color.renk9txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk9txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk9txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk9txt));
                break;
            case 9:
                txt_detail.setTextColor(getResources().getColor(R.color.renk10txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk10txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk10txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk10txt));
                break;
            case 10:
                txt_detail.setTextColor(getResources().getColor(R.color.renk11txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk11txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk11txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk11txt));
                break;
            case 11:
                txt_detail.setTextColor(getResources().getColor(R.color.renk12txt));
                txt_Time.setTextColor(getResources().getColor(R.color.renk12txt));
                txt_type.setTextColor(getResources().getColor(R.color.renk12txt));
                txt_Date.setTextColor(getResources().getColor(R.color.renk12txt));
                break;

        }
    }

    private void arkaPlan(int shepArka) {
        switch (shepArka)
        {
            case 0:
                // view.setBackgroundColor(Color.parseColor("#FFF9C4"));
                relativeLayout.setBackgroundResource(R.color.renk1);
                break;
            case 1:
                relativeLayout.setBackgroundResource(R.color.renk2);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.color.renk3);
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.color.renk4);
                break;
            case 4:
                relativeLayout.setBackgroundResource(R.color.renk5);
                break;
            case 5:
                relativeLayout.setBackgroundResource(R.color.renk6);
                break;
            case 6:
                relativeLayout.setBackgroundResource(R.color.renk7);
                break;
            case 7:
                relativeLayout.setBackgroundResource(R.color.renk8);
                break;
            case 8:
                relativeLayout.setBackgroundResource(R.color.renk9);
                break;
            case 9:
                relativeLayout.setBackgroundResource(R.color.renk10);
                break;
            case 10:
                relativeLayout.setBackgroundResource(R.color.renk11);
                break;
            case 11:
                relativeLayout.setBackgroundResource(R.color.renk12);
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
                                            db.delete(Veritabani.TABLE_ALARM, Veritabani.ALARM_KONTROL + "=" + ID, null);

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
                //paylaşmak
                Paylas();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void Paylas() {


        String txt = txt_detail.getText().toString();

        if(txt.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Boş paylaşım yapılamaz!!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent share_intent = new Intent(android.content.Intent.ACTION_SEND); // intenti oluşturuyoruz
            share_intent.setType("text/plain");
            share_intent.putExtra(android.content.Intent.EXTRA_SUBJECT,  getString(R.string.app_name));        // mesaj konusu
            share_intent.putExtra(android.content.Intent.EXTRA_TEXT, txt_detail.getText().toString()); // mesaj içeriği
            startActivity(Intent.createChooser(share_intent, ""+R.string.paylasSesim));  // paylaşmak istediğimiz platformu seçiyoruz
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
