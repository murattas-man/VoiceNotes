package com.voicenote.voicenotes;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;
    Veritabani mVeritabani;
    ListView list;
    String orderBy = "_id DESC";
    String alrmlar="type='Alert' or type='Alarm'";
    String onemli="type='Important' or type='Önemli'";
    String deger=" type is not null ";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editorNot;
    private String ilkGiris;
    ImageView imageNot;
    private boolean seskontrol=false;


    private static final String TAG = "MainActivity";

    private AdView mAdView;

    private List<Items> listItem = new ArrayList<Items>();
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences("Mymain", Context.MODE_PRIVATE);
        ilkGiris = sharedpreferences.getString("deger",deger);

        list = (ListView)findViewById(R.id.commentslist);

        mVeritabani = new Veritabani(this);
        db= mVeritabani.getWritableDatabase();
        final ImageView alarmImage = (ImageView) findViewById(R.id.alarmImage);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seskontrol=false;
                Intent openCreateNote = new Intent(MainActivity.this, NotAl.class);
                openCreateNote.putExtra("sesKontrol",seskontrol);
                startActivity(openCreateNote);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        yukleListe(ilkGiris);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View view, int position,
                                    long id){
                final String whereKontol = sharedpreferences.getString("deger",deger);
                Cursor c = db.rawQuery("select "+ mVeritabani.C_ID +" from " + mVeritabani.TABLE_NAME + " WHERE "+whereKontol+" ORDER BY " + orderBy ,
                        null);
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (c.moveToNext()){
                    arrID.add(c.getInt(0));
                }

                Intent intent = new Intent(MainActivity.this, Not_Detay.class);
                intent.putExtra("idNot", arrID.get(position));
                startActivity(intent);
            }

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, final long pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final String whereKontol = sharedpreferences.getString("deger",deger);
                                Cursor c = db.rawQuery("select "+ mVeritabani.C_ID +" from " + mVeritabani.TABLE_NAME + " WHERE "+whereKontol+" ORDER BY " + orderBy ,
                                        null);
                                ArrayList<Integer> arrID = new ArrayList<Integer>();
                                while (c.moveToNext()){
                                    arrID.add(c.getInt(0));
                                }

                                final long id=arrID.get(position);
                                Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);
                                if (cursor != null){
                                    if (cursor.moveToFirst()) {
                                        final int ID=(int) cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARMKON));
                                        if(ID!=1){
                                            //silinen not alarmlı not ise alarmı iptal et
                                            alarmIptal(ID);
                                            db.delete(Veritabani.TABLE_ALARM, Veritabani.ALARM_KONTROL + "=" + ID, null);

                                        }
                                        int renk=cursor.getInt(cursor.getColumnIndex(mVeritabani.RENKKODU));
                                        int arka=cursor.getInt(cursor.getColumnIndex(mVeritabani.ARKAPLAN));
                                    }
                                    cursor.close();
                                }

                                db.delete(Veritabani.TABLE_NAME, Veritabani.C_ID + "=" + id, null);
                                db.close();
                                Intent openMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)                        //Do nothing on no
                        .show();
                return true;


            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_new:
                seskontrol=false;
                Intent openCreateNote = new Intent(MainActivity.this, NotAl.class);
                openCreateNote.putExtra("sesKontrol",seskontrol);
                startActivity(openCreateNote);
                return true;

            case R.id.ses_direkt:
                seskontrol=true;
                Intent openSesNote = new Intent(MainActivity.this, NotAl.class);
                openSesNote.putExtra("sesKontrol",seskontrol);
                startActivity(openSesNote);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notlar) {
            editorNot = sharedpreferences.edit();
            editorNot.putString("deger",deger);
            editorNot.commit();

           yukleListe(deger);

        } else if (id == R.id.nav_hatirlatmalar) {
            editorNot = sharedpreferences.edit();
            editorNot.putString("deger",alrmlar);
            editorNot.commit();

          yukleListe(alrmlar);

        } else if (id == R.id.nav_sesli) {
            editorNot = sharedpreferences.edit();
            editorNot.putString("deger",onemli);
            editorNot.commit();
           yukleListe(onemli);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alarmIptal(final int ID){
        //alarm notu silinirse alarmı iptal etme
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), ID, myIntent,
                0);

        alarmManager.cancel(pendingIntent);
    }

    private void yukleListe(String degerim){
        String[] from = {mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME, mVeritabani.DATE};
        final String[] column = {mVeritabani.C_ID, mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME,
                mVeritabani.DATE,mVeritabani.ALARMKON,mVeritabani.RENKKODU,mVeritabani.ARKAPLAN};
        //int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mVeritabani.TABLE_NAME, column, degerim, null ,null, null, orderBy);
        listItem.clear();
        while (cursor.moveToNext()) {
            Items items = new Items();
            items.setTitleNot(cursor.getString(cursor.getColumnIndex(mVeritabani.TITLE)));
            items.setDetailNot(cursor.getString(cursor.getColumnIndex(mVeritabani.DETAIL)));
            items.setTypeNot(cursor.getString(cursor.getColumnIndex(mVeritabani.TYPE)));
            items.setDateNot(cursor.getString(cursor.getColumnIndex(mVeritabani.DATE)));
            items.setTimeNot(cursor.getString(cursor.getColumnIndex(mVeritabani.TIME)));
            items.setIdArkapaln(cursor.getInt(cursor.getColumnIndex(mVeritabani.ARKAPLAN)));
            items.setIdRenk(cursor.getInt(cursor.getColumnIndex(mVeritabani.RENKKODU)));

            listItem.add(items);
        }
        customAdapter = new CustomAdapter(getApplicationContext(),listItem);
        list.setAdapter(customAdapter);
    }

}
