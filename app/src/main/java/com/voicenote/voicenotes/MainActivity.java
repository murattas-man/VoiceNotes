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


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;
    Veritabani mVeritabani;
    ListView list;
    String orderBy = "_id DESC";
    String alrmlar="type='Alert' or type='Alarm'";
    String onemli="type='Important' or type='Önemli'";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private Boolean ilkGiris;
    ImageView imageNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedpreferences = getSharedPreferences("Mymain", Context.MODE_PRIVATE);
        ilkGiris = sharedpreferences.getBoolean("ilkmi", Boolean.parseBoolean(null));
        if(ilkGiris.equals(false)){

            editor = sharedpreferences.edit();
            editor.putBoolean("ilkmi", true);
            editor.commit();
        }



        list = (ListView)findViewById(R.id.commentslist);

        mVeritabani = new Veritabani(this);
        db= mVeritabani.getWritableDatabase();
        final ImageView alarmImage = (ImageView) findViewById(R.id.alarmImage);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCreateNote = new Intent(MainActivity.this, NotAl.class);
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

        String[] from = {mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME, mVeritabani.DATE};
        final String[] column = {mVeritabani.C_ID, mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME,
                mVeritabani.DATE,mVeritabani.ALARMKON,mVeritabani.RENKKODU,mVeritabani.ARKAPLAN};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mVeritabani.TABLE_NAME, column, null, null ,null, null, orderBy);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listView, View view, int position,
                                    long id){
                Intent intent = new Intent(MainActivity.this, Not_Detay.class);
                intent.putExtra(getString(R.string.rodId), id);
                startActivity(intent);
            }

        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,  int position, final long pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + pos, null);
                                if (cursor != null){
                                    if (cursor.moveToFirst()) {
                                        final int ID=(int) cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARMKON));
                                        if(ID!=1){
                                            //silinen not alarmlı not ise alarmı iptal et
                                            alarmIptal(ID);
                                        }
                                        int renk=cursor.getInt(cursor.getColumnIndex(mVeritabani.RENKKODU));
                                        int arka=cursor.getInt(cursor.getColumnIndex(mVeritabani.ARKAPLAN));
                                        Toast.makeText(getApplicationContext(),renk+" renk "+arka,Toast.LENGTH_LONG).show();
                                    }
                                    cursor.close();
                                }

                                db.delete(Veritabani.TABLE_NAME, Veritabani.C_ID + "=" + pos, null);
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
                Intent openCreateNote = new Intent(MainActivity.this, NotAl.class);
                startActivity(openCreateNote);
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
            String[] from = {mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME, mVeritabani.DATE};
            final String[] column = {mVeritabani.C_ID, mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME,
                    mVeritabani.DATE,mVeritabani.ALARMKON,mVeritabani.RENKKODU,mVeritabani.ARKAPLAN};
            int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

            final Cursor cursor = db.query(mVeritabani.TABLE_NAME, column, null, null ,null, null, orderBy);
            final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);
            list.setAdapter(adapter);

        } else if (id == R.id.nav_hatirlatmalar) {
            hatirlatmalar();

        } else if (id == R.id.nav_sesli) {
            onemlinotlar();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onemlinotlar() {
        String[] from = {mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME, mVeritabani.DATE};
        final String[] column = {mVeritabani.C_ID, mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME,
                mVeritabani.DATE,mVeritabani.ALARMKON,mVeritabani.RENKKODU,mVeritabani.ARKAPLAN};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mVeritabani.TABLE_NAME, column, onemli, null ,null, null, orderBy);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);
        list.setAdapter(adapter);
    }

    private void hatirlatmalar() {

        String[] from = {mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME, mVeritabani.DATE};
        final String[] column = {mVeritabani.C_ID, mVeritabani.TITLE, mVeritabani.DETAIL, mVeritabani.TYPE, mVeritabani.TIME,
                mVeritabani.DATE,mVeritabani.ALARMKON,mVeritabani.RENKKODU,mVeritabani.ARKAPLAN};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mVeritabani.TABLE_NAME, column, alrmlar, null ,null, null, orderBy);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_entry, cursor, from, to, 0);
        list.setAdapter(adapter);
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

}
