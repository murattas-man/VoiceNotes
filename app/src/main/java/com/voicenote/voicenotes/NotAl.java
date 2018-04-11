package com.voicenote.voicenotes;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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

    public Intent intentses;
    public static final int request_code_voice = 1;
    public EditText multiline_txt;
    private String soylenen;

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


        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.note_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
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
                cv.put(mVeritabani.RENKKODU,2);
                cv.put(mVeritabani.ARKAPLAN,3);

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

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
}
