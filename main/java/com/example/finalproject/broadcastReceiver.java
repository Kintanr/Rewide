package com.example.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationManagerCompat;

public class broadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notifid = intent.getIntExtra("id",0);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notifid);

        SharedPreferences shd = context.getSharedPreferences("Rewide",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shd.edit();
        editor.putString("Rewide", "Active");
        editor.apply();
    }
}
