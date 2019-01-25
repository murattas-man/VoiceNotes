package com.voicenote.voicenotes;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import Colours.Renkler;
import ImageButtons.RenkButon;

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
    private int shepArka;
    private int shepYazi;
    private boolean seskontrol;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    ImageView img1,img2,img3,img4;
    ImageView img5,img6,img7,img8;
    ImageView img9,img10,img11,img12;
    ImageView[] imageButonlar= RenkButon.imageButonlar();

    private InterstitialAd gecisReklam;
    private AdRequest adRequest;

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

        adRequest=new AdRequest.Builder().build();
        gecisReklam = new InterstitialAd(this);
        gecisReklam.setAdUnitId("ca-app-pub-3688388679356685/4684066498");
        gecisReklam.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Toast.makeText(NotAl.this, "reklam yüklendi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                loadGecisReklam();
                String detail = mNotAciklamaText.getText().toString().trim();
                if (detail.isEmpty()){
                    Intent setIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(setIntent);
                }
                else
                {
                    saveAllNotes();
                }
            }
        });
        loadGecisReklam();

        Bundle bundle = getIntent().getExtras();
        seskontrol=bundle.getBoolean("sesKontrol");
        if(seskontrol){
            seskontrol=false;
            hopses();
        }






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
                hopses();


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
        mNotAciklamaText.setTextColor(getResources().getColor(Renkler.textColor[shepYazi]));
        yaziRengi=shepYazi;
    }

    private void txtArkaPlan(int shepArka) {
        mNotAciklamaText.setBackgroundResource(Renkler.renkArkaplan[shepArka]);
        arkaPlan=shepArka;
    }
 private void hopses(){
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

    @Override
    public void onBackPressed() {
        showGecisReklam();

    }

    private void loadGecisReklam() {
        gecisReklam.loadAd(adRequest);

    }
    private void showGecisReklam() {
       if (gecisReklam.isLoaded())
        {
            gecisReklam.show();
       }else {
            String detail = mNotAciklamaText.getText().toString().trim();
            if (detail.isEmpty()){
                Intent setIntent = new Intent(this, MainActivity.class);
                startActivity(setIntent);
            }
            else
            {
                saveAllNotes();
            }
       }
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
                saveAllNotes();
                return true;

            case R.id.action_back:
                showGecisReklam();
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

//notları kaydet fonksiyonu
    private void saveAllNotes() {
        //String title = baslikText.getText().toString();
        String detail = mNotAciklamaText.getText().toString().trim();
        if (detail.isEmpty()){
            Toast.makeText(getApplicationContext(), R.string.aciklama, Toast.LENGTH_LONG).show();
            mNotAciklamaText.setText("");
        }
        else
        {
            String str=detail;
            char c=Character.toUpperCase(str.charAt(0));
            str=c+str.substring(1);

            String[] words = str.split(" ");
            String title=words[0];

            String type =  mSpinner.getSelectedItem().toString();
            ContentValues cv = new ContentValues();
            ContentValues A_cv = new ContentValues();
            cv.put(mVeritabani.TITLE, title);
            cv.put(mVeritabani.DETAIL, str);
            cv.put(mVeritabani.TYPE, type);
            cv.put(mVeritabani.TIME, getString(R.string.Not_Set));
            SimpleDateFormat dateformatterone = new SimpleDateFormat(getString(R.string.dateformate));
            String dateStringone = dateformatterone.format(new Date(Calendar.getInstance().getTimeInMillis()));
            cv.put(mVeritabani.DATE, dateStringone);
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
                    ComponentName receiver =new ComponentName(getApplicationContext(),AlarmReceiver.class);
                    PackageManager pm=getApplicationContext().getPackageManager();
                    pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);

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

                    A_cv.put(mVeritabani.ALARM_TITLE,detail);
                    A_cv.put(mVeritabani.ALARM_YEAR, year);
                    A_cv.put(mVeritabani.ALARM_MONTH, month);
                    A_cv.put(mVeritabani.ALARM_DAY, day);
                    A_cv.put(mVeritabani.ALARM_HOUR, saat);
                    A_cv.put(mVeritabani.ALARM_MINUTE, dakika);
                    A_cv.put(mVeritabani.ALARM_KONTROL,_id);


                    db.insert(mVeritabani.TABLE_ALARM, null, A_cv);
                }


            }

            db.insert(mVeritabani.TABLE_NAME, null, cv);

            Intent openMainScreen = new Intent(this, MainActivity.class);
            openMainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(openMainScreen);

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
        for (int i=0;i<12;i++)
            imageButonlar[i]=(ImageView) dialog.findViewById(RenkButon.btnRenkler[i]);

        int width = (int) (NotAl.this.getResources().getDisplayMetrics().widthPixels * 0.85);
        // set height for dialog
        int height = (int) (NotAl.this.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotAciklamaText.setTextColor(getResources().getColor(Renkler.textColor[yaziRengi]));
                editor.putInt("shepyazi",yaziRengi);
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


        for (int i=0;i<12;i++)
            imageButonlar[i]=(ImageView) dialog.findViewById(RenkButon.btnRenkler[i]);

        int width = (int) (NotAl.this.getResources().getDisplayMetrics().widthPixels * 0.85);
        // set height for dialog
        int height = (int) (NotAl.this.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
 //tamam
        btnTamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotAciklamaText.setBackgroundResource(Renkler.renkArkaplan[arkaPlan]);
                editor.putInt("arkaplan",arkaPlan);
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

        if (checked)
            for(int i=0;i<12;i++){
                if(view.getId()==RenkButon.btnRenkler[i])
                {
                    imageButonlar[i].setImageResource(R.drawable.ic_action_checked);
                    arkaPlan=i;
                }
                else
                    imageButonlar[i].setImageResource(Renkler.renkArkaplan[i]);
            }
    }
//yazı rekleri
    public void onCheckboxClickedtxt(View view) {
        // Is the view now checked?
        boolean checked = ((ImageView) view).hasOnClickListeners();

        if (checked)
            for(int i=0;i<12;i++){
                if(view.getId()==RenkButon.btnRenkler[i])
                {
                    imageButonlar[i].setImageResource(R.drawable.ic_action_checked);
                    yaziRengi=i;
                }
                else
                    imageButonlar[i].setImageResource(Renkler.textColor[i]);
            }
    }
}
