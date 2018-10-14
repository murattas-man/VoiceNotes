package com.voicenote.voicenotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dominic on 07/04/2015.
 */
public class Veritabani extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data2";
    public static final String TABLE_NAME = "comments_table";
    public static final String C_ID = "_id";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String DETAIL = "description";
    public static final String TIME = "time";
    public static final String DATE = "date";
    public static final String ALARMKON ="_aid";
    public static final String RENKKODU="_renkid";
    public static final String ARKAPLAN="arkaplan";
    public static final int VERSION = 2;

    private final String createDB = "create table if not exists " + TABLE_NAME + " ( "
            + C_ID + " integer primary key autoincrement, "
            + TITLE + " text, "
            + DETAIL + " text, "
            + TYPE + " text, "
            + TIME + " text, "
            + DATE + " text,"
            + ALARMKON + " integer,"
            +RENKKODU + " integer,"
            +ARKAPLAN + " integer)";

    public static final String TABLE_ALARM = "alarm_table";
    public static final String A_ID = "_aid";
    public static final String ALARM_TITLE = "atitle";
    public static final String ALARM_YEAR = "yil";
    public static final String ALARM_MONTH = "ay";
    public static final String ALARM_DAY = "gun";
    public static final String ALARM_HOUR = "saat";
    public static final String ALARM_MINUTE = "dakika";
    public static final String ALARM_KONTROL ="idkontrol";


    private final String createDBTWO = "create table if not exists " + TABLE_ALARM + " ( "
            + A_ID + " integer primary key autoincrement, "
            + ALARM_TITLE + " text, "
            + ALARM_KONTROL + " integer,"
            + ALARM_YEAR + " integer,"
            + ALARM_MONTH + " integer,"
            + ALARM_DAY + " integer,"
            + ALARM_HOUR + " integer,"
            +ALARM_MINUTE + " integer)";

    public Veritabani(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createDB);
        db.execSQL(createDBTWO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        db.execSQL("drop table " + TABLE_ALARM);
    }
}