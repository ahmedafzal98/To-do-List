package com.example.volleylist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class Notification extends ContextWrapper {
    public static final String notifId = "notificationChannel";
    public static final String notifName = "channelName";

    NotificationManager mManager;
    public Notification(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannel();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel channel = new NotificationChannel(notifId,notifName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager(){

        if (mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }
    public NotificationCompat.Builder getNotification(String title , String body , String date , String time ,String id){
        Intent intent = new Intent(this , UpdateData.class);
//        intent.putExtra("position",position);
        intent.putExtra("title" , title);
        intent.putExtra("body" , body);
        intent.putExtra("date" , date+" "+time);
        intent.putExtra("time" , time);
        intent.putExtra("isComingFromNotification",true);
        intent.putExtra("id",id);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(),notifId)
                .setContentTitle(title)
                .setContentText("Message")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

    }
}
