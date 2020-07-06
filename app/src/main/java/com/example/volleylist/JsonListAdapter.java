package com.example.volleylist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

class JsonListAdapter extends ArrayAdapter<JsonDataList> {

    String title;
    Context mContext;
    int mResource;
    List<JsonDataList> arrayList;
    int lastpostion = -1;
    int TYPE_ITEM = 0;
    int TYPE_HEADER = 1;
    ViewHolder holder = null;
    Date currentDate;
    int index = 0;
    Date listDate;
    int p = 1;
    boolean b = true;
    int positionUpcoming = -1;
    int positionOverdue = -1;
    int upcomingPosition = -1;
    SimpleDateFormat format;
    String itemDateString;

    public class ViewHolder{
        TextView userId;
        TextView id;
        TextView title;
        TextView body;
        TextView date;
        TextView header;
        Button btn_delete;
    }

    public JsonListAdapter(@NonNull Context context, int resource, @NonNull List<JsonDataList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        arrayList = objects;
        currentDate = new Date();

        for (int i =0; i<arrayList.size();i++){
            itemDateString = getItem(i).getTaskDate();
            format = new SimpleDateFormat("d/M/yyyy hh:mm");
            try {
                listDate = format.parse(itemDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(listDate.getTime() > currentDate.getTime()){

                upcomingPosition = i;
                break;
            }else {

            }
        }

    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String id = getItem(position).getId();
        String user_id = getItem(position).getUserId();
       String title = getItem(position).getTitle();
       String body = getItem(position).getBody();
       String date = getItem(position).getTaskDate();

        format = new SimpleDateFormat("d/M/yyyy hh:mm");
        try {
            listDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.adapter_view_layout,null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.txt_view3);
            holder.body = (TextView) convertView.findViewById(R.id.txt_view4);
            holder.date = (TextView) convertView.findViewById(R.id.txt_view5);
            holder.header = (TextView) convertView.findViewById(R.id.txt_header);


            updateItem(position);
           convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
            updateItem(position);
        }
       return convertView;
    }

    private void updateItem(int position){

        if(position == 0 && listDate.getTime() < currentDate.getTime()){
            holder.header.setText("Overdue");
            holder.header.setTextColor(Color.RED);
            holder.header.setVisibility(View.VISIBLE);
        }else if(position == upcomingPosition){
            holder.header.setText("Upcoming");
            holder.header.setTextColor(Color.BLUE);
            holder.header.setVisibility(View.VISIBLE);
        }else{
            holder.header.setVisibility(View.GONE);
        }

        if(position < upcomingPosition || upcomingPosition == -1){
            holder.date.setTextColor(Color.RED);
        }else{
            holder.date.setTextColor(Color.BLUE);
        }
        JsonDataList list = getItem(position);
        holder.title.setText(list.getTitle());
        holder.body.setText(list.getBody());
        holder.date.setText(list.getTaskDate());
    }

}
