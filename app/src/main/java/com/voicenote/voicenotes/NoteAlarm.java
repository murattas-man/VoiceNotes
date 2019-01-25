package com.voicenote.voicenotes;

public class NoteAlarm {
    private int id;
    private String a_Title;
    private int a_Kontrol;
    private int a_Year;
    private int a_Month;
    private int a_Day;
    private int a_Hour;
    private int a_Minute;

    public int getId() {
        return id;
    }
    public NoteAlarm setId(int id) {
        this.id = id;
        return this;
    }

    public int getA_Kontrol() {
        return a_Kontrol;
    }
    public NoteAlarm setA_Kontrol(int a_Kontrol) {
        this.a_Kontrol = a_Kontrol;
        return this;
    }

    public int getA_Year() {
        return a_Year;
    }
    public NoteAlarm setA_Year(int a_Year) {
        this.a_Year = a_Year;
        return this;
    }

    public int getA_Month() {
        return a_Month;
    }
    public NoteAlarm setA_Month(int a_Month) {
        this.a_Month = a_Month;
        return this;
    }

    public int getA_Day() {
        return a_Day;
    }
    public NoteAlarm setA_Day(int a_Day) {
        this.a_Day = a_Day;
        return this;
    }

    public int getA_Hour() {
        return a_Hour;
    }
    public NoteAlarm setA_Hour(int a_Hour) {
        this.a_Hour = a_Hour;
        return this;
    }

    public int getA_Minute() {
        return a_Minute;
    }
    public NoteAlarm setA_Minute(int a_Minute) {
        this.a_Minute = a_Minute;
        return this;
    }

    public String getA_Title(){return a_Title;}
    public NoteAlarm setA_Title(String a_Title) {
        this.a_Title=a_Title;
        return  this;
    }

}
