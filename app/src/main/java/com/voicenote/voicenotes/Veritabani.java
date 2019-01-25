package com.voicenote.voicenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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

    private SQLiteDatabase db;

    public Veritabani(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        db.execSQL(createDB);
        db.execSQL(createDBTWO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   /*     db.execSQL("drop table " + TABLE_NAME);
        db.execSQL("drop table " + TABLE_ALARM);
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + TITLE + " integer DEFAULT \'\'");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + ALARM_TITLE + " integer DEFAULT \'\'");
        }*/
    }
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public Cursor getAll(String sql) {
        open();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }

    public long insert(String table, ContentValues values) {
        open();
        long index = db.insert(table, null, values);
        close();
        return index;
    }

    public boolean update(String table, ContentValues values, String where) {
        open();
        long index = db.update(table, values, where, null);
        close();
        return index > 0;
    }

    public boolean delete(String table, String where) {
        open();
        long index = db.delete(table, where, null);
        close();
        return index > 0;
    }

    /*******************************************/

    public Note getNote(String sql) {
        Note note = null;
        Cursor cursor = getAll(sql);
        if (cursor != null) {
            note = cursorToNote(cursor);
            cursor.close();
        }
        return note;
    }
    public ArrayList<Note> getListNote(String sql) {
        ArrayList<Note> list = new ArrayList<>();
        Cursor cursor = getAll(sql);

        while (!cursor.isAfterLast()) {
            list.add(cursorToNote(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }
    public long insertNote(Note note) {
        return insert(TABLE_NAME, noteToValues(note));
    }
    public boolean updateNote(Note note) {
        return update(TABLE_NAME, noteToValues(note), C_ID + " = " + note.getId());
    }
    public boolean deleteNote(String where) {
        return delete(TABLE_NAME, where);
    }
    private ContentValues noteToValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DETAIL, note.getDatail());
        values.put(TYPE, note.getType());
        values.put(TIME, note.getTime());
        values.put(DATE, note.getDate());
        values.put(ALARMKON, note.getAlarmKon());
        values.put(RENKKODU, note.getRenkKodu());
        values.put(ARKAPLAN, note.getArkaPlan());
        return values;
    }

    public Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndex(C_ID)))
                .setTitle(cursor.getString(cursor.getColumnIndex(TITLE)))
                .setDetail(cursor.getString(cursor.getColumnIndex(DETAIL)))
                .setType(cursor.getString(cursor.getColumnIndex(TYPE)))
                .setTime(cursor.getString(cursor.getColumnIndex(TIME)))
                .setDate(cursor.getString(cursor.getColumnIndex(DATE)))
                .setAlarmKon(cursor.getInt(cursor.getColumnIndex(ALARMKON)))
                .setRenkKodu(cursor.getInt(cursor.getColumnIndex(RENKKODU)))
                .setArkaPlan(cursor.getInt(cursor.getColumnIndex(ARKAPLAN)));
        return note;
    }
    /*******************************************/
    /*******************************************/

    public NoteAlarm getNoteAlarm(String sql) {
        NoteAlarm noteAlarm = null;
        Cursor cursor = getAll(sql);
        if (cursor != null) {
            noteAlarm = cursorToNoteAlarm(cursor);
            cursor.close();
        }
        return noteAlarm;
    }
    public ArrayList<NoteAlarm> getListNoteAlarm(String sql) {
        ArrayList<NoteAlarm> list = new ArrayList<>();
        Cursor cursor = getAll(sql);

        while (!cursor.isAfterLast()) {
            list.add(cursorToNoteAlarm(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }
    public long insertNoteAlarm(NoteAlarm noteAlarm) {
        return insert(TABLE_ALARM, noteAlarmToValues(noteAlarm));
    }
    public boolean updateNoteAlarm(NoteAlarm noteAlarm) {
        return update(TABLE_ALARM, noteAlarmToValues(noteAlarm), A_ID + " = " + noteAlarm.getId());
    }
    public boolean deleteNoteAlarm(String where) {
        return delete(TABLE_ALARM, where);
    }
    private ContentValues noteAlarmToValues(NoteAlarm noteAlarm) {
        ContentValues values = new ContentValues();
        values.put(TITLE, noteAlarm.getA_Title());
        values.put(ALARMKON, noteAlarm.getA_Kontrol());
        values.put(ALARM_YEAR, noteAlarm.getA_Year());
        values.put(ALARM_MONTH, noteAlarm.getA_Month());
        values.put(ALARM_DAY, noteAlarm.getA_Day());
        values.put(ALARM_HOUR, noteAlarm.getA_Hour());
        values.put(ALARM_MINUTE, noteAlarm.getA_Minute());

        return values;
    }

    public NoteAlarm cursorToNoteAlarm(Cursor cursor) {
        NoteAlarm noteAlarm = new NoteAlarm();
        noteAlarm.setId(cursor.getInt(cursor.getColumnIndex(A_ID)))
                .setA_Title(cursor.getString(cursor.getColumnIndex(ALARM_TITLE)))
                .setA_Kontrol(cursor.getInt(cursor.getColumnIndex(ALARMKON)))
                .setA_Year(cursor.getInt(cursor.getColumnIndex(ALARM_YEAR)))
                .setA_Month(cursor.getInt(cursor.getColumnIndex(ALARM_MONTH)))
                .setA_Day(cursor.getInt(cursor.getColumnIndex(ALARM_DAY)))
                .setA_Hour(cursor.getInt(cursor.getColumnIndex(ALARM_HOUR)))
                .setA_Minute(cursor.getInt(cursor.getColumnIndex(ALARM_MINUTE)));
        return noteAlarm;
    }
}