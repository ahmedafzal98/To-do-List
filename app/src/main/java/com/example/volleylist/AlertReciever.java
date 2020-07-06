package com.example.volleylist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String id = intent.getStringExtra("id");
//        int position = intent.getIntExtra("position",1);
        Notification notification = new Notification(context);
        NotificationCompat.Builder nb = notification.getNotification(title,body,date,time,id);
        notification.getManager().notify(1, nb.build() );
    }
}
