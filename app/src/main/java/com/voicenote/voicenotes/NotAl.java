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
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotAl extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{

    SQLiteDatabase db;
    Veritabani mVeritabani;
    EditText mNotAciklamaText;
    Spinner mSpinner;
    TextView time,txtSaat;
    TextView date,txtTarih;
    CheckBox checkBoxAlarm;

    int year;
    int month;
    int day;
    int saat;
    int dakika;

    int arkaPlan=0;
    int yaziRengi=8;
    int arkaPlan1=0;
    int yaziRengi1=8;

    public Intent intentses;
    public static final int request_code_voice = 1;
    public EditText multiline_txt;
    private String soylenen;
    private int shepArka;
    private int shepYazi;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    ImageView img1,img2,img3,img4;
    ImageView img5,img6,img7,img8;
    ImageView img9,img10,img11,img12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_ses_layout);
        mVeritabani = new Veritabani(this);
        db = mVeritabani.getWritableDatabase();

        //  baslikText = (EditText) findViewById(R.id.txttitle);
        mNotAciklamaText = (EditText) findViewById(R.id.description);
        mSpinner = (Spinner) findViewById(R.id.spinnerNoteType);
        time = (TextView) findViewById(R.id.txtTime);
        date = (TextView) findViewById(R.id.txtDate);
        checkBoxAlarm = (CheckBox) findViewById(R.id.checkBox);
        txtSaat=(TextView)findViewById(R.id.txtSaat);
        txtTarih=(TextView)findViewById(R.id.txtTarih);
        time.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);

        sharedpreferences = getSharedPreferences("Mresim", Context.MODE_PRIVATE);
        shepArka=sharedpreferences.getInt("arkaplan",0);
        shepYazi=sharedpreferences.getInt("shepyazi",8);
        arkaPlan1=shepArka;
        yaziRengi1=shepYazi;
        txtArkaPlan(shepArka);
        txtYaziRengi(shepYazi);






        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(
                    AdapterView parent, View view, int position, long id) {
                if(id == 2){
                    showToast(getString(R.string.added_alert));
                    checkBoxAlarm.setEnabled(true);
                }
                else {
                    checkBoxAlarm.setEnabled(false);
                    checkBoxAlarm.setChecked(false);
                }
            }

            public void onNothingSelected(AdapterView parent) {
            }
        });

        checkBoxAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    time.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    txtSaat.setVisibility(View.VISIBLE);
                    txtTarih.setVisibility(View.VISIBLE);
                    DialogFragment datePicker=new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(),"Date Picker");
                }
                else{
                    time.setVisibility(View.INVISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    txtSaat.setVisibility(View.INVISIBLE);
                    txtTarih.setVisibility(View.INVISIBLE);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabses);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(NotAl.this);
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

        txtTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });
        txtSaat.setOnClickListener(new View.OnClickListener() {
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
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk1txt));
                yaziRengi=0;
                break;
            case 1:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk2txt));
                yaziRengi=1;
                break;
            case 2:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk3txt));
                yaziRengi=2;
                break;
            case 3:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk4txt));
                yaziRengi=3;
                break;
            case 4:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk5txt));
                yaziRengi=4;
                break;
            case 5:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk6txt));
                yaziRengi=5;
                break;
            case 6:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk7txt));
                yaziRengi=6;
                break;
            case 7:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk8txt));
                yaziRengi=7;
                break;
            case 8:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk9txt));
                yaziRengi=8;
                break;
            case 9:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk10txt));
                yaziRengi=9;
                break;
            case 10:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk11txt));
                yaziRengi=10;
                break;
            case 11:
                mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk12txt));
                yaziRengi=11;
                break;
        }
    }

    private void txtArkaPlan(int shepArka) {

        switch (shepArka){
            case 0:
                mNotAciklamaText.setBackgroundResource(R.color.renk1);
                arkaPlan=0;
                break;
            case 1:
                mNotAciklamaText.setBackgroundResource(R.color.renk2);
                arkaPlan=1;
                break;
            case 2:
                mNotAciklamaText.setBackgroundResource(R.color.renk3);
                arkaPlan=2;
                break;
            case 3:
                mNotAciklamaText.setBackgroundResource(R.color.renk4);
                arkaPlan=3;
                break;
            case 4:
                mNotAciklamaText.setBackgroundResource(R.color.renk5);
                arkaPlan=4;
                break;
            case 5:
                mNotAciklamaText.setBackgroundResource(R.color.renk6);
                arkaPlan=5;
                break;
            case 6:
                mNotAciklamaText.setBackgroundResource(R.color.renk7);
                arkaPlan=6;
                break;
            case 7:
                mNotAciklamaText.setBackgroundResource(R.color.renk8);
                arkaPlan=7;
                break;
            case 8:
                mNotAciklamaText.setBackgroundResource(R.color.renk9);
                arkaPlan=8;
                break;
            case 9:
                mNotAciklamaText.setBackgroundResource(R.color.renk10);
                arkaPlan=9;
                break;
            case 10:
                mNotAciklamaText.setBackgroundResource(R.color.renk11);
                arkaPlan=10;
                break;
            case 11:
                mNotAciklamaText.setBackgroundResource(R.color.renk12);
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
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_save:
                //String title = baslikText.getText().toString();
                String detail = mNotAciklamaText.getText().toString().trim();
                if (detail.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.aciklama, Toast.LENGTH_LONG).show();
                    mNotAciklamaText.setText("");
                }
                else
                {

                String[] words = detail.split(" ");
                String title=words[0];
                String type =  mSpinner.getSelectedItem().toString();
                ContentValues cv = new ContentValues();
                cv.put(mVeritabani.TITLE, title);
                cv.put(mVeritabani.DETAIL, detail);
                cv.put(mVeritabani.TYPE, type);
                cv.put(mVeritabani.TIME, getString(R.string.Not_Set));
                cv.put(mVeritabani.ALARMKON,1);
                cv.put(mVeritabani.RENKKODU,yaziRengi1);
                cv.put(mVeritabani.ARKAPLAN,arkaPlan1);

                if (checkBoxAlarm.isChecked()){
                    Calendar calender = Calendar.getInstance();
                    calender.clear();
                    calender.set(Calendar.MONTH, month);
                    calender.set(Calendar.DAY_OF_MONTH,day);
                    calender.set(Calendar.YEAR, year);
                    calender.set(Calendar.HOUR,saat);
                    calender.set(Calendar.MINUTE, dakika);
                    calender.set(Calendar.SECOND, 00);

                   // Toast.makeText(getApplicationContext(),"tarih:"+gAy+"ay"+month,Toast.LENGTH_LONG).show();

                    SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.hour_minutes));
                    String timeString = formatter.format(new Date(calender.getTimeInMillis()));
                    SimpleDateFormat dateformatter = new SimpleDateFormat(getString(R.string.dateformate));
                    String dateString = dateformatter.format(new Date(calender.getTimeInMillis()));

                    final int _id = (int) System.currentTimeMillis();

                    final Calendar takvim = Calendar.getInstance();
                    long farkZaman=Calendar.getInstance().getTimeInMillis()-calender.getTimeInMillis();

                    if(farkZaman>0){
                        Toast.makeText(getApplicationContext(),R.string.hangiTarih,Toast.LENGTH_LONG).show();



                    }else{
                        //geçerli bir tarih girildiyse alarm oluşturuluyor
                        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(this, AlarmReceiver.class);
                        String alertTitle = detail;
                        if(alertTitle.isEmpty()){
                            intent.putExtra(getString(R.string.alert_title),getString(R.string.app_name));
                        }else {
                            intent.putExtra(getString(R.string.alert_title), alertTitle);
                        }
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _id, intent, 0);

                        alarmMgr.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);
                       // Toast.makeText(getApplicationContext(), ""+calender.getTimeInMillis(), Toast.LENGTH_LONG).show();
                        cv.put(mVeritabani.TIME, timeString);
                        cv.put(mVeritabani.DATE, dateString);
                        cv.put(mVeritabani.ALARMKON,_id);
                    }


                }

                db.insert(mVeritabani.TABLE_NAME, null, cv);

                Intent openMainScreen = new Intent(this, MainActivity.class);
                openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(openMainScreen);

                }

                return true;

            case R.id.action_back:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                return true;

            case R.id.action_paylas:

                Paylas();
                return true;
            case R.id.action_settings_note:
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
        final Dialog dialog = new Dialog(NotAl.this);
        dialog.setContentView(R.layout.txt_renk_layout);
        dialog.setTitle(getString(R.string.arkaPlan));
        final int arkSecilen;
        editor = sharedpreferences.edit();
        final ImageButton btnIptal=(ImageButton) dialog.findViewById(R.id.btnNo);
        final ImageButton btnTamam=(ImageButton) dialog.findViewById(R.id.btnTamam);

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

        int width = (int) (NotAl.this.getResources().getDisplayMetrics().widthPixels * 0.85);
        // set height for dialog
        int height = (int) (NotAl.this.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (yaziRengi){
                    case 0:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk1txt));
                        editor.putInt("shepyazi",0);
                        break;
                    case 1:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk2txt));
                        editor.putInt("shepyazi",1);
                        break;
                    case 2:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk3txt));
                        editor.putInt("shepyazi",2);
                        break;
                    case 3:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk4txt));
                        editor.putInt("shepyazi",3);
                        break;
                    case 4:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk5txt));
                        editor.putInt("shepyazi",4);
                        break;
                    case 5:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk6txt));
                        editor.putInt("shepyazi",5);
                        break;
                    case 6:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk7txt));
                        editor.putInt("shepyazi",6);
                        break;
                    case 7:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk8txt));
                        editor.putInt("shepyazi",7);
                        break;
                    case 8:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk9txt));
                        editor.putInt("shepyazi",8);
                        break;
                    case 9:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk10txt));
                        editor.putInt("shepyazi",9);
                        break;
                    case 10:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk11txt));
                        editor.putInt("shepyazi",10);
                        break;
                    case 11:
                        mNotAciklamaText.setTextColor(getResources().getColor(R.color.renk12txt));
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
                arkaPlan=sharedpreferences.getInt("arkaplan",8);
                dialog.dismiss();
            }
        });


    }

    private void arkaRenk() {
        final Dialog dialog = new Dialog(NotAl.this);
        dialog.setContentView(R.layout.renk_layout);
        dialog.setTitle(getString(R.string.arkaPlan));
        final int arkSecilen;
        editor = sharedpreferences.edit();
        final ImageButton btnIptal=(ImageButton) dialog.findViewById(R.id.btnNo);
        final ImageButton btnTamam=(ImageButton) dialog.findViewById(R.id.btnTamam);

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

        int width = (int) (NotAl.this.getResources().getDisplayMetrics().widthPixels * 0.85);
        // set height for dialog
        int height = (int) (NotAl.this.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
 //tamam
        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (arkaPlan){
                    case 0:
                        mNotAciklamaText.setBackgroundResource(R.color.renk1);
                        editor.putInt("arkaplan",0);
                        break;
                    case 1:
                        mNotAciklamaText.setBackgroundResource(R.color.renk2);
                        editor.putInt("arkaplan",1);
                        break;
                    case 2:
                        mNotAciklamaText.setBackgroundResource(R.color.renk3);
                        editor.putInt("arkaplan",2);
                        break;
                    case 3:
                        mNotAciklamaText.setBackgroundResource(R.color.renk4);
                        editor.putInt("arkaplan",3);
                        break;
                    case 4:
                        mNotAciklamaText.setBackgroundResource(R.color.renk5);
                        editor.putInt("arkaplan",4);
                        break;
                    case 5:
                        mNotAciklamaText.setBackgroundResource(R.color.renk6);
                        editor.putInt("arkaplan",5);
                        break;
                    case 6:
                        mNotAciklamaText.setBackgroundResource(R.color.renk7);
                        editor.putInt("arkaplan",6);
                        break;
                    case 7:
                        mNotAciklamaText.setBackgroundResource(R.color.renk8);
                        editor.putInt("arkaplan",7);
                        break;
                    case 8:
                        mNotAciklamaText.setBackgroundResource(R.color.renk9);
                        editor.putInt("arkaplan",8);
                        break;
                    case 9:
                        mNotAciklamaText.setBackgroundResource(R.color.renk10);
                        editor.putInt("arkaplan",9);
                        break;
                    case 10:
                        mNotAciklamaText.setBackgroundResource(R.color.renk11);
                        editor.putInt("arkaplan",10);
                        break;
                    case 11:
                        mNotAciklamaText.setBackgroundResource(R.color.renk12);
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
                NotAl.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm");

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

    protected void Paylas() {


        String txt = mNotAciklamaText.getText().toString();

        if(txt.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Boş paylaşım yapılamaz!!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent share_intent = new Intent(android.content.Intent.ACTION_SEND); // intenti oluşturuyoruz
            share_intent.setType("text/plain");
            share_intent.putExtra(android.content.Intent.EXTRA_SUBJECT,  getString(R.string.app_name));        // mesaj konusu
            share_intent.putExtra(android.content.Intent.EXTRA_TEXT, mNotAciklamaText.getText().toString()); // mesaj içeriği
            startActivity(Intent.createChooser(share_intent, ""+R.string.paylasSesim));  // paylaşmak istediğimiz platformu seçiyoruz
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

                }
                break;
        }
    }
//yazı rekleri
    public void onCheckboxClickedtxt(View view) {
        // Is the view now checked?
        boolean checked = ((ImageView) view).hasOnClickListeners();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.img1:
                if (checked){

                    img1.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=0;

                }
                break;
            case R.id.img2:
                if (checked){

                    img2.setImageResource(R.drawable.ic_action_checked);
                    img1.setImageResource(R.color.renk1txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=1;

                }
                break;
            case R.id.img3:
                if (checked){

                    img3.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img1.setImageResource(R.color.renk1txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=2;

                }
                break;
            case R.id.img4:
                if (checked){

                    img4.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img1.setImageResource(R.color.renk1txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=3;

                }
                break;
            case R.id.img5:
                if (checked){

                    img5.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img1.setImageResource(R.color.renk1txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=4;

                }
                break;
            case R.id.img6:
                if (checked){

                    img6.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img1.setImageResource(R.color.renk1txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);
                    yaziRengi=5;

                }
                break;
            case R.id.img7:
                if (checked){

                    img7.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img1.setImageResource(R.color.renk1txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=6;

                }
                break;
            case R.id.img8:
                if (checked){

                    img8.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img1.setImageResource(R.color.renk1txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=7;

                }
                break;
            case R.id.img9:
                if (checked){

                    img9.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img1.setImageResource(R.color.renk1txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);
                    yaziRengi=8;

                }
                break;
            case R.id.img10:
                if (checked){

                    img10.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img1.setImageResource(R.color.renk1txt);
                    img11.setImageResource(R.color.renk11txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=9;

                }
                break;
            case R.id.img11:
                if (checked){

                    img11.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img1.setImageResource(R.color.renk1txt);
                    img12.setImageResource(R.color.renk12txt);

                    yaziRengi=10;

                }
                break;
            case R.id.img12:
                if (checked){

                    img12.setImageResource(R.drawable.ic_action_checked);
                    img2.setImageResource(R.color.renk2txt);
                    img3.setImageResource(R.color.renk3txt);
                    img4.setImageResource(R.color.renk4txt);
                    img5.setImageResource(R.color.renk5txt);
                    img6.setImageResource(R.color.renk6txt);
                    img7.setImageResource(R.color.renk7txt);
                    img8.setImageResource(R.color.renk8txt);
                    img9.setImageResource(R.color.renk9txt);
                    img10.setImageResource(R.color.renk10txt);
                    img11.setImageResource(R.color.renk11txt);
                    img1.setImageResource(R.color.renk1txt);
                    yaziRengi=11;

                }
                break;
        }
    }
}
