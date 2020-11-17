package com.example.finalproject;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

public class reminder extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent it = new Intent(context,notifikasi.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pd = PendingIntent.getActivity(context,0,it,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Rewide")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Sapi Birahi")
                .setContentText("Sapi Anda Besok Birahi!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.ic_notifications, "Lihat", pd);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}

