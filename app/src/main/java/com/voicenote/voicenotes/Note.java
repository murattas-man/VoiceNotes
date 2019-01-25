package com.voicenote.voicenotes;

public class Note {
    private int id;
    private String title;
    private String datail;
    private String type;
    private String time;
    private String date;
    private int alarmKon;
    private int renkKodu;
    private int arkaPlan;

    public int getId() {
        return id;
    }
    public Note setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDatail() {
        return datail;
    }
    public Note setDetail(String datail) {
        this.datail = datail;
        return this;
    }

    public String getType() {
        return type;
    }
    public Note setType(String type) {
        this.type = type;
        return this;
    }

    public String getTime() {
        return time;
    }
    public Note setTime(String time) {
        this.time = time;
        return this;
    }

    public String getDate() {
        return date;
    }
    public Note setDate(String date) {
        this.date = date;
        return this;
    }

    public int getAlarmKon() {
        return alarmKon;
    }
    public Note setAlarmKon(int alarmKon) {
        this.alarmKon = alarmKon;
        return this;
    }

    public int getRenkKodu() {
        return renkKodu;
    }
    public Note setRenkKodu(int renkKodu) {
        this.renkKodu = renkKodu;
        return this;
    }

    public int getArkaPlan() {
        return arkaPlan;
    }
    public Note setArkaPlan(int arkaPlan) {
        this.arkaPlan = arkaPlan;
        return this;
    }


}
