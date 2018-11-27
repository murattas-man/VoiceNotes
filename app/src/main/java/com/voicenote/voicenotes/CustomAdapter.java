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


public class CustomAdapter extends ArrayAdapter<Items> {
    private Context context;
    private ViewHolder viewHolder;
    Veritabani mVeritabani;
    SQLiteDatabase db;
    int YIL,AY,GUN,SAAT,DAKIKA,idAlarm;
    private List<Items> arrayList = new ArrayList<Items>();
    public CustomAdapter(Context context, List<Items> list_items) {

        super(context, R.layout.list_entry, list_items);
        this.context = context;
        this.arrayList = list_items;
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
        viewHolder.txt_title.setText(arrayList.get(position).getTitleNot());
        viewHolder.txt_type.setText(arrayList.get(position).getTypeNot());
        viewHolder.txt_detail.setText(arrayList.get(position).getDetailNot());
        viewHolder.txt_Date.setText(arrayList.get(position).getDateNot());

        int alarmkon=arrayList.get(position).getAlarmKontrol();
        viewHolder.txt_Time.setText(arrayList.get(position).getTimeNot());


        int arkaid=arrayList.get(position).getIdArkapaln();
     switch (arkaid)
     {
         case 0:
            // view.setBackgroundColor(Color.parseColor("#FFF9C4"));
             view.setBackgroundResource(R.color.renk1);
             break;
         case 1:
             view.setBackgroundResource(R.color.renk2);
            break;
         case 2:
             view.setBackgroundResource(R.color.renk3);
            break;
         case 3:
             view.setBackgroundResource(R.color.renk4);
            break;
         case 4:
             view.setBackgroundResource(R.color.renk5);
            break;
         case 5:
             view.setBackgroundResource(R.color.renk6);
             break;
         case 6:
             view.setBackgroundResource(R.color.renk7);
            break;
         case 7:
             view.setBackgroundResource(R.color.renk8);
            break;
         case 8:
             view.setBackgroundResource(R.color.renk9);
            break;
         case 9:
             view.setBackgroundResource(R.color.renk10);
             break;
         case 10:
             view.setBackgroundResource(R.color.renk11);
             break;
         case 11:
             view.setBackgroundResource(R.color.renk12);
             break;
     }

     //yazı rengi
     int renkId=arrayList.get(position).getIdRenk();

        switch (renkId)
        {
            case 0:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk1txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk1txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk1txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk1txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk1txt));
                break;
            case 1:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk2txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk2txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk2txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk2txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk2txt));
                break;
            case 2:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk3txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk3txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk3txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk3txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk3txt));
                break;
            case 3:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk4txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk4txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk4txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk4txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk4txt));
                break;
            case 4:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk5txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk5txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk5txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk5txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk5txt));
                break;
            case 5:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk6txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk6txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk6txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk6txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk6txt));
                break;
            case 6:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk7txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk7txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk7txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk7txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk7txt));
                break;
            case 7:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk8txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk8txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk8txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk8txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk8txt));
                break;
            case 8:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk9txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk9txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk9txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk9txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk9txt));
                break;
            case 9:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk10txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk10txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk10txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk10txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk10txt));
                break;
            case 10:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk11txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk11txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk11txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk11txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk11txt));
                break;
            case 11:
                viewHolder.txt_detail.setTextColor(context.getResources().getColor(R.color.renk12txt));
                viewHolder.txt_Time.setTextColor(context.getResources().getColor(R.color.renk12txt));
                viewHolder.txt_type.setTextColor(context.getResources().getColor(R.color.renk12txt));
                viewHolder.txt_Date.setTextColor(context.getResources().getColor(R.color.renk12txt));
                viewHolder.txt_title.setTextColor(context.getResources().getColor(R.color.renk12txt));
                break;
        }

        /*
        if (arrayList.get(position).getTypeNot()=="Alert" ||arrayList.get(position).getTypeNot()=="Alarm" ){
            viewHolder.ımageView.setImageResource(R.drawable.ic_action_alarms);
            if (arkaid==11){
                viewHolder.ımageView.setBackgroundColor(Color.parseColor("##FF3D00"));
            }
            else{
                viewHolder.ımageView.setBackgroundColor(Color.parseColor("#00E676"));
            }

        }*/

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
        mVeritabani=new Veritabani(context);
        db= mVeritabani.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + mVeritabani.TABLE_ALARM + " where " + mVeritabani.ALARM_KONTROL + "=" + id, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                YIL = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_YEAR));
                AY  = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_MONTH));
                GUN = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_DAY));
                SAAT = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_HOUR));
                DAKIKA  = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_MINUTE));
                idAlarm  = cursor.getInt(cursor.getColumnIndex(mVeritabani.ALARM_KONTROL));

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
