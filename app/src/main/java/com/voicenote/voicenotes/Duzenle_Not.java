package com.voicenote.voicenotes;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Duzenle_Not extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    SQLiteDatabase db;
    Veritabani mVeritabani;
    EditText mNotAciklamaText;
    Spinner mSpinner;
    TextView time,txtSaat;
    TextView date,txtTarih;
    CheckBox checkBoxAlarm;
    public Intent intentses;
    public static final int request_code_voice = 1;


    int year;
    int month;
    int day;
    int saat;
    int dakika;
    boolean alarmVarmi=false;

     int ID;
     String baslik;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private int shepArka;
    private int shepYazi;

    int arkaPlan=0;
    int yaziRengi=8;
    int arkaPlan1=0;
    int yaziRengi1=8;


    ImageView img1,img2,img3,img4;
    ImageView img5,img6,img7,img8;
    ImageView img9,img10,img11,img12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_duzenle_layout);
        mVeritabani = new Veritabani(this);
        db = mVeritabani.getWritableDatabase();

        mNotAciklamaText = (EditText) findViewById(R.id.description);
        mSpinner = (Spinner) findViewById(R.id.spinnerNoteType);
        time = (TextView) findViewById(R.id.txt_selecttime);
        date = (TextView) findViewById(R.id.txt_selectdate);
        checkBoxAlarm = (CheckBox) findViewById(R.id.chkbox);
        txtSaat=(TextView)findViewById(R.id.txtSaatd);
        txtTarih=(TextView)findViewById(R.id.txtTarihd);

        final long id = getIntent().getExtras().getLong(getString(R.string.row_id_log));

        sharedpreferences = getSharedPreferences("Mresim", Context.MODE_PRIVATE);
        shepArka=sharedpreferences.getInt("arkaplan",0);
        shepYazi=sharedpreferences.getInt("shepyazi",8);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.note_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView parent, View view, int position, long id) {
                        if (id == 2) {
                            showToast(getString(R.string.alarm_active));
                            checkBoxAlarm.setEnabled(true);
                            alarmVarmi=true;
                        }
                        else {
                            checkBoxAlarm.setEnabled(false);
                            checkBoxAlarm.setChecked(false);
                            alarmVarmi=false;
                        }
                    }

                    public void onNothingSelected(AdapterView parent) {

                    }
                });

        Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                mNotAciklamaText.setText(cursor.getString(cursor.getColumnIndex(mVeritabani.DETAIL)));
                arkaPlan1=cursor.getInt(cursor.getColumnIndex(mVeritabani.ARKAPLAN));
                yaziRengi1=cursor.getInt(cursor.getColumnIndex(mVeritabani.RENKKODU));
                if (cursor.getString(cursor.getColumnIndex(mVeritabani.TYPE)).equals(mSpinner.getItemAtPosition(0))){
                    mSpinner.setSelection(0);
                    checkBoxAlarm.setChecked(false);
                    checkBoxAlarm.setEnabled(false);
                    txtTarih.setVisibility(View.INVISIBLE);
                    txtSaat.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                }
                else if (cursor.getString(cursor.getColumnIndex(mVeritabani.TYPE)).equals(mSpinner.getItemAtPosition(1))){
                    mSpinner.setSelection(1);
                    checkBoxAlarm.setChecked(false);
                    checkBoxAlarm.setEnabled(false);
                    txtTarih.setVisibility(View.INVISIBLE);
                    txtSaat.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                }
                else if (cursor.getString(cursor.getColumnIndex(mVeritabani.TYPE)).equals(mSpinner.getItemAtPosition(2))) {
                    mSpinner.setSelection(2);
                    checkBoxAlarm.setChecked(true);
                    checkBoxAlarm.setEnabled(true);
                    final String eskiSaat=getIntent().getExtras().getString("saat");
                    final String eskiTarih=getIntent().getExtras().getString("tarih");
                    txtSaat.setText(eskiSaat+"");
                    txtTarih.setText(eskiTarih+"");
                    alarmVarmi=true;
                }
                if (cursor.getString(cursor.getColumnIndex(mVeritabani.TIME)).toString().equals(getString(R.string.Not_Set_Alert))) {
                    checkBoxAlarm.setChecked(false);
                    txtTarih.setVisibility(View.INVISIBLE);
                    txtSaat.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);

                }


            }
            cursor.close();
        }

        txtArkaPlan(arkaPlan1);
        txtYaziRengi(yaziRengi1);




        checkBoxAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    time.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    txtSaat.setVisibility(View.VISIBLE);
                    txtTarih.setVisibility(View.VISIBLE);
                    DialogFragment datePicker=new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(),"Date Picker");

                } else {

                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    txtSaat.setVisibility(View.INVISIBLE);
                    txtTarih.setVisibility(View.INVISIBLE);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabduzen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentses = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // intent i oluşturduk sesi tanıyabilmesi için
                intentses.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                try{
                    startActivityForResult(intentses, request_code_voice);  // activityi başlattık belirlediğimiz sabit değer ile birlikte
                }catch(ActivityNotFoundException e)
                {
                    // activity bulunamadığı zaman hatayı göstermek için alert dialog kullandım
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Duzenle_Not.this);
                    builder.setMessage(R.string.sesKontrol)
                            .setTitle(R.string.app_name)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });

        txtSaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });
        txtTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });
    }

    private void txtYaziRengi(int shepYazi) {
        switch (shepYazi){
            case 0:
                mNotAciklamaText.setTextColor(Color.WHITE);
                yaziRengi=0;
                break;
            case 1:
                mNotAciklamaText.setTextColor(Color.parseColor("#FFEB3B"));
                yaziRengi=1;
                break;
            case 2:
                mNotAciklamaText.setTextColor(Color.parseColor("#FF5722"));
                yaziRengi=2;
                break;
            case 3:
                mNotAciklamaText.setTextColor(Color.parseColor("#FF3D00"));
                yaziRengi=3;
                break;
            case 4:
                mNotAciklamaText.setTextColor(Color.parseColor("#DD2C00"));
                yaziRengi=4;
                break;
            case 5:
                mNotAciklamaText.setTextColor(Color.parseColor("#7C4DFF"));
                yaziRengi=5;
                break;
            case 6:
                mNotAciklamaText.setTextColor(Color.parseColor("#3F51B5"));
                yaziRengi=6;
                break;
            case 7:
                mNotAciklamaText.setTextColor(Color.parseColor("#2196F3"));
                yaziRengi=7;
                break;
            case 8:
                mNotAciklamaText.setTextColor(Color.parseColor("#000000"));
                yaziRengi=8;
                break;
            case 9:
                mNotAciklamaText.setTextColor(Color.parseColor("#4E342E"));
                yaziRengi=9;
                break;
            case 10:
                mNotAciklamaText.setTextColor(Color.parseColor("#616161"));
                yaziRengi=10;
                break;
            case 11:
                mNotAciklamaText.setTextColor(Color.parseColor("#C6FF00"));
                yaziRengi=11;
                break;
        }
    }

    private void txtArkaPlan(int shepArka) {

        switch (shepArka){
            case 0:
                mNotAciklamaText.setBackgroundColor(Color.WHITE);
                arkaPlan=0;
                break;
            case 1:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#FFEB3B"));
                arkaPlan=1;
                break;
            case 2:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#FF5722"));
                arkaPlan=2;
                break;
            case 3:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#FF3D00"));
                arkaPlan=3;
                break;
            case 4:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#DD2C00"));
                arkaPlan=4;
                break;
            case 5:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#7C4DFF"));
                arkaPlan=5;
                break;
            case 6:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#3F51B5"));
                arkaPlan=6;
                break;
            case 7:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#2196F3"));
                arkaPlan=7;
                break;
            case 8:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#000000"));
                arkaPlan=8;
                break;
            case 9:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#4E342E"));
                arkaPlan=9;
                break;
            case 10:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#616161"));
                arkaPlan=10;
                break;
            case 11:
                mNotAciklamaText.setBackgroundColor(Color.parseColor("#C6FF00"));
                arkaPlan=11;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                return true;
            case R.id.action_save:
                final long id = getIntent().getExtras().getLong(getString(R.string.row_id_long));
               // String title = baslikText.getText().toString();
                String detail = mNotAciklamaText.getText().toString().trim();

                if (detail.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.aciklama, Toast.LENGTH_LONG).show();
                    mNotAciklamaText.setText("");
                }
                else {
                        String[] words = detail.split(" ");
                        baslik=words[0];

                    String title = baslik;
                    String type = mSpinner.getSelectedItem().toString();
                    ContentValues cv = new ContentValues();
                    cv.put(mVeritabani.TITLE, title);
                    cv.put(mVeritabani.DETAIL, detail);
                    cv.put(mVeritabani.TYPE, type);
                    cv.put(mVeritabani.TIME, getString(R.string.Not_Set));
                    cv.putNull(mVeritabani.DATE);
                    cv.put(mVeritabani.RENKKODU,yaziRengi1);
                    cv.put(mVeritabani.ARKAPLAN,arkaPlan1);

                    if (checkBoxAlarm.isChecked()) {
                        Calendar calender = Calendar.getInstance();
                        calender.clear();
                        calender.set(Calendar.MONTH, month);
                        calender.set(Calendar.DAY_OF_MONTH, day);
                        calender.set(Calendar.YEAR, year);
                        calender.set(Calendar.HOUR, saat);
                        calender.set(Calendar.MINUTE, dakika);
                        calender.set(Calendar.SECOND, 00);

                        SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.hour_minutes));
                        String timeString = formatter.format(new Date(calender.getTimeInMillis()));
                        SimpleDateFormat dateformatter = new SimpleDateFormat(getString(R.string.dateformate));
                        String dateString = dateformatter.format(new Date(calender.getTimeInMillis()));

                        final Calendar takvim = Calendar.getInstance();
                        long farkZaman = Calendar.getInstance().getTimeInMillis() - calender.getTimeInMillis();
                        if (farkZaman > 0) {
                            Toast.makeText(getApplicationContext(), R.string.hangiTarih, Toast.LENGTH_LONG).show();


                        } else {
                            AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(this, AlarmReceiver.class);
                            String alertTitle = title;
                            intent.putExtra(getString(R.string.alert_title), alertTitle);
                            final int _id = (int) System.currentTimeMillis();
                            Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_NAME + " where " + mVeritabani.C_ID + "=" + id, null);
                            if (cursor != null) {
                                if (cursor.moveToFirst()) {
                                    ID = (int) cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARMKON));
                                }
                                cursor.close();
                            }
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ID, intent, 0);
                            alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

                            cv.put(mVeritabani.TIME, timeString);
                            cv.put(mVeritabani.DATE, dateString);

                        }


                    }

                    db.update(mVeritabani.TABLE_NAME, cv, mVeritabani.C_ID + "=" + id, null);
                    Intent openMainScreen = new Intent(Duzenle_Not.this, MainActivity.class);
                    openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(openMainScreen);
                }
                return true;
            case R.id.action_settings:
                arkaRenk();
                return true;

            case R.id.action_renk:
                renkSec();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void renkSec() {
        final Dialog dialog = new Dialog(Duzenle_Not.this);
        dialog.setContentView(R.layout.renk_layout);
        dialog.setTitle(getString(R.string.arkaPlan));
        final int arkSecilen;
        editor = sharedpreferences.edit();
        final Button btnIptal=(Button) dialog.findViewById(R.id.btnNo);
        final Button btnTamam=(Button) dialog.findViewById(R.id.btnTamam);

        img1=(ImageView) dialog.findViewById(R.id.img1);
        img2=(ImageView) dialog.findViewById(R.id.img2);
        img3=(ImageView) dialog.findViewById(R.id.img3);
        img4=(ImageView) dialog.findViewById(R.id.img4);
        img5=(ImageView) dialog.findViewById(R.id.img5);
        img6=(ImageView) dialog.findViewById(R.id.img6);
        img7=(ImageView) dialog.findViewById(R.id.img7);
        img8=(ImageView) dialog.findViewById(R.id.img8);
        img9=(ImageView) dialog.findViewById(R.id.img9);
        img10=(ImageView) dialog.findViewById(R.id.img10);
        img11=(ImageView) dialog.findViewById(R.id.img11);
        img12=(ImageView) dialog.findViewById(R.id.img12);
        int width = (int) (Duzenle_Not.this.getResources().getDisplayMetrics().widthPixels * 0.85);
        // set height for dialog
        int height = (int) (Duzenle_Not.this.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        int shepArka=sharedpreferences.getInt("arkaplan",0);

        int  shepYazi=sharedpreferences.getInt("shepyazi",0);

        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (yaziRengi){
                    case 0:
                        mNotAciklamaText.setTextColor(Color.WHITE);
                        editor.putInt("shepyazi",0);
                        break;
                    case 1:
                        mNotAciklamaText.setTextColor(Color.parseColor("#FFEB3B"));
                        editor.putInt("shepyazi",1);
                        break;
                    case 2:
                        mNotAciklamaText.setTextColor(Color.parseColor("#FF5722"));
                        editor.putInt("shepyazi",2);
                        break;
                    case 3:
                        mNotAciklamaText.setTextColor(Color.parseColor("#FF3D00"));
                        editor.putInt("shepyazi",3);
                        break;
                    case 4:
                        mNotAciklamaText.setTextColor(Color.parseColor("#DD2C00"));
                        editor.putInt("shepyazi",4);
                        break;
                    case 5:
                        mNotAciklamaText.setTextColor(Color.parseColor("#7C4DFF"));
                        editor.putInt("shepyazi",5);
                        break;
                    case 6:
                        mNotAciklamaText.setTextColor(Color.parseColor("#3F51B5"));
                        editor.putInt("shepyazi",6);
                        break;
                    case 7:
                        mNotAciklamaText.setTextColor(Color.parseColor("#2196F3"));
                        editor.putInt("shepyazi",7);
                        break;
                    case 8:
                        mNotAciklamaText.setTextColor(Color.parseColor("#000000"));
                        editor.putInt("shepyazi",8);
                        break;
                    case 9:
                        mNotAciklamaText.setTextColor(Color.parseColor("#4E342E"));
                        editor.putInt("shepyazi",9);
                        break;
                    case 10:
                        mNotAciklamaText.setTextColor(Color.parseColor("#616161"));
                        editor.putInt("shepyazi",10);
                        break;
                    case 11:
                        mNotAciklamaText.setTextColor(Color.parseColor("#C6FF00"));
                        editor.putInt("shepyazi",11);
                        break;
                }
                yaziRengi1=yaziRengi;

                editor.commit();
                dialog.dismiss();
            }
        });

        //iptal
        btnIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yaziRengi=sharedpreferences.getInt("shepyazi",0);
                arkaPlan=sharedpreferences.getInt("arkaplan",0);
                dialog.dismiss();
            }
        });

    }

    private void arkaRenk() {
        final Dialog dialog = new Dialog(Duzenle_Not.this);
        dialog.setContentView(R.layout.renk_layout);
        dialog.setTitle(getString(R.string.arkaPlan));
        final int arkSecilen;
        editor = sharedpreferences.edit();
        final Button btnIptal=(Button) dialog.findViewById(R.id.btnNo);
        final Button btnTamam=(Button) dialog.findViewById(R.id.btnTamam);

        img1=(ImageView) dialog.findViewById(R.id.img1);
        img2=(ImageView) dialog.findViewById(R.id.img2);
        img3=(ImageView) dialog.findViewById(R.id.img3);
        img4=(ImageView) dialog.findViewById(R.id.img4);
        img5=(ImageView) dialog.findViewById(R.id.img5);
        img6=(ImageView) dialog.findViewById(R.id.img6);
        img7=(ImageView) dialog.findViewById(R.id.img7);
        img8=(ImageView) dialog.findViewById(R.id.img8);
        img9=(ImageView) dialog.findViewById(R.id.img9);
        img10=(ImageView) dialog.findViewById(R.id.img10);
        img11=(ImageView) dialog.findViewById(R.id.img11);
        img12=(ImageView) dialog.findViewById(R.id.img12);

        int width = (int) (Duzenle_Not.this.getResources().getDisplayMetrics().widthPixels * 0.85);
        // set height for dialog
        int height = (int) (Duzenle_Not.this.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        int shepArka=sharedpreferences.getInt("arkaplan",0);

        int  shepYazi=sharedpreferences.getInt("shepyazi",0);
        //tamam
        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (arkaPlan){
                    case 0:
                        mNotAciklamaText.setBackgroundColor(Color.WHITE);
                        editor.putInt("arkaplan",0);
                        break;
                    case 1:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#FFEB3B"));
                        editor.putInt("arkaplan",1);
                        break;
                    case 2:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#FF5722"));
                        editor.putInt("arkaplan",2);
                        break;
                    case 3:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#FF3D00"));
                        editor.putInt("arkaplan",3);
                        break;
                    case 4:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#DD2C00"));
                        editor.putInt("arkaplan",4);
                        break;
                    case 5:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#7C4DFF"));
                        editor.putInt("arkaplan",5);
                        break;
                    case 6:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#3F51B5"));
                        editor.putInt("arkaplan",6);
                        break;
                    case 7:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#2196F3"));
                        editor.putInt("arkaplan",7);
                        break;
                    case 8:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#000000"));
                        editor.putInt("arkaplan",8);
                        break;
                    case 9:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#4E342E"));
                        editor.putInt("arkaplan",9);
                        break;
                    case 10:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#616161"));
                        editor.putInt("arkaplan",10);
                        break;
                    case 11:
                        mNotAciklamaText.setBackgroundColor(Color.parseColor("#C6FF00"));
                        editor.putInt("arkaplan",11);
                        break;
                }
                arkaPlan1=arkaPlan;

                editor.commit();
                dialog.dismiss();
            }
        });

        //iptal
        btnIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yaziRengi=sharedpreferences.getInt("shepyazi",0);
                arkaPlan=sharedpreferences.getInt("arkaplan",0);
                dialog.dismiss();
            }
        });


    }





    @Override
    public void onDateSet(DatePicker view, int yil, int ay, int gun) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,yil);
        c.set(Calendar.MONTH,ay);
        c.set(Calendar.DAY_OF_MONTH,gun);
        year=yil;
        month=ay;
        day=gun;
        SimpleDateFormat dateformatter = new SimpleDateFormat(getString(R.string.dateformate));
        String dateString = dateformatter.format(new Date(c.getTimeInMillis()));
        txtTarih.setText(dateString);
        openPickerDialog(true);

    }
    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Duzenle_Not.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();
    }
    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            saat=hourOfDay;
            dakika=minute;
            SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.hour_minutes));
            String timeString = formatter.format(new Date(calSet.getTimeInMillis()));
            txtSaat.setText(timeString);


        }};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {String toplam="";
        if(mNotAciklamaText.length()>0){
            toplam= mNotAciklamaText.getText().toString()+" ";
        }

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case request_code_voice: {

                if (resultCode == RESULT_OK && data != null)
                {
                    // intent boş olmadığında ve sonuç tamam olduğu anda konuşmayı alıp listenin içine attık
                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    toplam+=speech.get(0);

                }
                mNotAciklamaText.setText(toplam+"");
                break;
            }

        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((ImageView) view).hasOnClickListeners();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.img1:
                if (checked){

                    img1.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);
                    arkaPlan=0;
                    yaziRengi=0;

                }
                break;
            case R.id.img2:
                if (checked){

                    img2.setImageResource(R.drawable.ic_action_checked);
                    img1.setImageResource(R.color.renk1);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=1;
                    yaziRengi=1;

                }
                break;
            case R.id.img3:
                if (checked){

                    img3.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img1.setImageResource(R.color.renk1);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=2;
                    yaziRengi=2;

                }
                break;
            case R.id.img4:
                if (checked){

                    img4.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img1.setImageResource(R.color.renk1);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=3;
                    yaziRengi=3;

                }
                break;
            case R.id.img5:
                if (checked){

                    img5.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img1.setImageResource(R.color.renk1);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=4;
                    yaziRengi=4;

                }
                break;
            case R.id.img6:
                if (checked){

                    img6.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img1.setImageResource(R.color.renk1);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=5;
                    yaziRengi=5;

                }
                break;
            case R.id.img7:
                if (checked){

                    img7.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img1.setImageResource(R.color.renk1);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=6;
                    yaziRengi=6;

                }
                break;
            case R.id.img8:
                if (checked){

                    img8.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img1.setImageResource(R.color.renk1);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=7;
                    yaziRengi=7;

                }
                break;
            case R.id.img9:
                if (checked){

                    img9.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img1.setImageResource(R.color.renk1);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);
                    arkaPlan=8;
                    yaziRengi=8;

                }
                break;
            case R.id.img10:
                if (checked){

                    img10.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img1.setImageResource(R.color.renk1);
                    img11.setImageResource(R.color.renk11);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=9;
                    yaziRengi=9;

                }
                break;
            case R.id.img11:
                if (checked){

                    img11.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img1.setImageResource(R.color.renk1);
                    img12.setImageResource(R.color.renk12);

                    arkaPlan=10;
                    yaziRengi=10;

                }
                break;
            case R.id.img12:
                if (checked){

                    img12.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2);
                    img3.setImageResource(R.color.renk3);
                    img4.setImageResource(R.color.renk4);
                    img5.setImageResource(R.color.renk5);
                    img6.setImageResource(R.color.renk6);
                    img7.setImageResource(R.color.renk7);
                    img8.setImageResource(R.color.renk8);
                    img9.setImageResource(R.color.renk9);
                    img10.setImageResource(R.color.renk10);
                    img11.setImageResource(R.color.renk11);
                    img1.setImageResource(R.color.renk1);
                    arkaPlan=11;
                    yaziRengi=11;

                }
                break;
        }
    }

}
