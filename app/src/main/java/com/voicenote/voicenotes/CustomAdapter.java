package com.voicenote.voicenotes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Colours.Renkler;


public class CustomAdapter extends ArrayAdapter<Note> {
    private Context context;
    private ViewHolder viewHolder;
    private Veritabani db;
    int YIL,AY,GUN,SAAT,DAKIKA,idAlarm;
    private List<Note> arrayList = new ArrayList<Note>();
    public CustomAdapter(Context context, List<Note> arrayList) {

        super(context, R.layout.list_entry, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.list_entry, parent,false);

            viewHolder = new ViewHolder();
            viewHolder.txt_title = (TextView) view.findViewById(R.id.title);
            viewHolder.txt_detail = (TextView) view.findViewById(R.id.Detail);
            viewHolder.txt_type = (TextView) view.findViewById(R.id.type);
            viewHolder.txt_Time = (TextView) view.findViewById(R.id.time);
            viewHolder.txt_Date = (TextView) view.findViewById(R.id.date);
            viewHolder.ımageView = (ImageView) view.findViewById(R.id.alarmImage);

            view.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) view.getTag();

        }
        //viewHolder.txt_title.setText(arrayList.get(position).getTitle());
        viewHolder.txt_type.setText(arrayList.get(position).getType());
        viewHolder.txt_detail.setText(arrayList.get(position).getDatail());
        viewHolder.txt_Date.setText(arrayList.get(position).getDate());

        int alarmkon=arrayList.get(position).getAlarmKon();
        viewHolder.txt_Time.setText(arrayList.get(position).getTime());


        view.setBackgroundResource(Renkler.renkArkaplan[arrayList.get(position).getArkaPlan()]);

        viewHolder.txt_detail.setTextColor(context.getResources().getColor(Renkler.textColor[arrayList.get(position).getRenkKodu()]));
        viewHolder.txt_type.setTextColor(context.getResources().getColor(Renkler.textColor[arrayList.get(position).getRenkKodu()]));
        viewHolder.txt_Time.setTextColor(context.getResources().getColor(Renkler.textColor[arrayList.get(position).getRenkKodu()]));
        viewHolder.txt_Date.setTextColor(context.getResources().getColor(Renkler.textColor[arrayList.get(position).getRenkKodu()]));
       // viewHolder.txt_title.setTextColor(context.getResources().getColor(Renkler.textColor[arrayList.get(position).getRenkKodu()]));

        if(alarmkon!=1)
        alarmKontrolEt(alarmkon);
        return view;
    }



    private static class ViewHolder{
        TextView txt_title;
        TextView txt_detail;
        TextView txt_type;
        ImageView ımageView;
        TextView txt_Time;
        TextView txt_Date;

    }

    private void alarmKontrolEt(int alaramkon) {
        int id=alaramkon;
        try {
            db=new Veritabani(context);
        Cursor cursor = db.getAll("select * from " + db.TABLE_ALARM + " where " + db.ALARM_KONTROL + "=" + id);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                YIL = cursor.getInt(cursor.getColumnIndex(db.ALARM_YEAR));
                AY  = cursor.getInt(cursor.getColumnIndex(db.ALARM_MONTH));
                GUN = cursor.getInt(cursor.getColumnIndex(db.ALARM_DAY));
                SAAT = cursor.getInt(cursor.getColumnIndex(db.ALARM_HOUR));
                DAKIKA  = cursor.getInt(cursor.getColumnIndex(db.ALARM_MINUTE));
                idAlarm  = cursor.getInt(cursor.getColumnIndex(db.ALARM_KONTROL));

                Calendar calender = Calendar.getInstance();
                calender.clear();
                calender.set(Calendar.MONTH, AY);
                calender.set(Calendar.DAY_OF_MONTH,GUN);
                calender.set(Calendar.YEAR, YIL);
                calender.set(Calendar.HOUR,SAAT);
                calender.set(Calendar.MINUTE, DAKIKA);
                calender.set(Calendar.SECOND, 00);
                final Calendar takvim = Calendar.getInstance();
                long farkZaman=Calendar.getInstance().getTimeInMillis()-calender.getTimeInMillis();
                if(farkZaman>0){
                    viewHolder.txt_Time.setText(""+context.getString(R.string.old));
                    viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.eskise));
                    viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.eskise));
                    viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.eskise));
                    viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.eskise));
                    viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.eskise));
                }
            }
        } } catch (Exception ane) {
            faceFace(ane.getLocalizedMessage());
        }
    }
    void faceFace(String s) {
        Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
