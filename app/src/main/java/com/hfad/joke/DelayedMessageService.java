package com.hfad.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;

import org.w3c.dom.Text;

public class DelayedMessageService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String EXTRA_MESSAGE = "Mensaje";
    public static final int NOTIFICATION_ID = 5453;


    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("DelayedMessageService", "Esto es un mensaje");

        synchronized (this) {
            try {
                wait(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showTextDos(text);

    }


private void showTextDos(final String text){
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

        // Configure the notification channel.
        notificationChannel.setDescription("Channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        notificationChannel.enableVibration(true);
        notificationManager.createNotificationChannel(notificationChannel);
    }


    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

    notificationBuilder.setAutoCancel(true)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle(getString(R.string.question))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(new long[]{0, 1000})
            .setAutoCancel(true);


    Intent actionLintent = new Intent(this, MainActivity.class);
    PendingIntent actionPendingIntend = PendingIntent.getActivity(this, 0, actionLintent, PendingIntent.FLAG_UPDATE_CURRENT);
    notificationBuilder.setContentIntent(actionPendingIntend);
    notificationManager.notify(/*notification id*/1, notificationBuilder.build());
}
}