package com.karim.lebdrive;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationTrigger extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent2 = new Intent(context, MainActivity.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyMe")
                .setSmallIcon(R.drawable.outline_directions_car_black_24)
                .setContentTitle("Your test is in 1 day!")
                .setContentText("Start practing to ace your test.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
