package com.voicenote.voicenotes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CustomAdapter extends ArrayAdapter<Items> {
    private Context context;
    private ViewHolder viewHolder;
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
        viewHolder.txt_Time.setText(arrayList.get(position).getTimeNot());

        viewHolder.txt_title.setTextColor(Color.parseColor("#000000"));
        viewHolder.txt_type.setTextColor(Color.parseColor("#616161"));
        viewHolder.txt_detail.setTextColor(Color.parseColor("#616161"));
        viewHolder.txt_Date.setTextColor(Color.parseColor("#616161"));
        viewHolder.txt_Time.setTextColor(Color.parseColor("#616161"));
        int arkaid=arrayList.get(position).getIdArkapaln();
     switch (arkaid)
     {
         case 0:
             view.setBackgroundColor(Color.WHITE);
             break;
         case 1:
             view.setBackgroundColor(Color.parseColor("#FFEB3B"));
            break;
         case 2:
             view.setBackgroundColor(Color.parseColor("#FF5722"));
            break;
         case 3:
             view.setBackgroundColor(Color.parseColor("#FF3D00"));
            break;
         case 4:
             view.setBackgroundColor(Color.parseColor("#DD2C00"));
            break;
         case 5:
             view.setBackgroundColor(Color.parseColor("#7C4DFF"));
             break;
         case 6:
             view.setBackgroundColor(Color.parseColor("#3F51B5"));
            break;
         case 7:
             view.setBackgroundColor(Color.parseColor("#2196F3"));
            break;
         case 8:
             view.setBackgroundColor(Color.parseColor("#000000"));
            break;
         case 9:
             view.setBackgroundColor(Color.parseColor("#4E342E"));
             break;
         case 10:
             view.setBackgroundColor(Color.parseColor("#616161"));
             break;
         case 11:
             view.setBackgroundColor(Color.parseColor("#C6FF00"));
             break;
     }

     //yazı rengi
     int renkId=arrayList.get(position).getIdRenk();

        switch (renkId)
        {
            case 0:
                viewHolder.txt_detail.setTextColor(Color.WHITE);
                viewHolder.txt_type.setTextColor(Color.WHITE);
                viewHolder.txt_Time.setTextColor(Color.WHITE);
                viewHolder.txt_Date.setTextColor(Color.WHITE);
                viewHolder.txt_title.setTextColor(Color.WHITE);
                break;
            case 1:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#FFEB3B"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#FFEB3B"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#FFEB3B"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#FFEB3B"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#FFEB3B"));
                break;
            case 2:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#FF5722"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#FF5722"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#FF5722"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#FF5722"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#FF5722"));
                break;
            case 3:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#FF3D00"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#FF3D00"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#FF3D00"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#FF3D00"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#FF3D00"));
                break;
            case 4:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#DD2C00"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#DD2C00"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#DD2C00"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#DD2C00"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#DD2C00"));
                break;
            case 5:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#7C4DFF"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#7C4DFF"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#7C4DFF"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#7C4DFF"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#7C4DFF"));
                break;
            case 6:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#3F51B5"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#3F51B5"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#3F51B5"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#3F51B5"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#3F51B5"));
                break;
            case 7:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#2196F3"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#2196F3"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#2196F3"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#2196F3"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#2196F3"));
                break;
            case 8:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#000000"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#000000"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#000000"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#000000"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#000000"));
                break;
            case 9:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#4E342E"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#4E342E"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#4E342E"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#4E342E"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#4E342E"));
                break;
            case 10:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#616161"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#616161"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#616161"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#616161"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#616161"));
                break;
            case 11:
                viewHolder.txt_detail.setTextColor(Color.parseColor("#C6FF00"));
                viewHolder.txt_Time.setTextColor(Color.parseColor("#C6FF00"));
                viewHolder.txt_Date.setTextColor(Color.parseColor("#C6FF00"));
                viewHolder.txt_title.setTextColor(Color.parseColor("#C6FF00"));
                viewHolder.txt_type.setTextColor(Color.parseColor("#C6FF00"));
                break;
        }
        if (arrayList.get(position).getTypeNot()=="Alert" ||arrayList.get(position).getTypeNot()=="Alarm" ){
            viewHolder.ımageView.setImageResource(R.drawable.ic_action_alarms);
            if (arkaid==11){
                viewHolder.ımageView.setBackgroundColor(Color.parseColor("##FF3D00"));
            }
            else{
                viewHolder.ımageView.setBackgroundColor(Color.parseColor("#00E676"));
            }

        }


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
}
