package com.example.yusepmaulana07.myalarm3;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class MyBroadcastReceiver extends BroadcastReceiver {

    Ringtone r;

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("Alarm is ON")
                .setContentText("You Had activate alarm")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        notification.flags|=Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0,notification);

        Uri notif_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        r = RingtoneManager.getRingtone(context,notif_uri);
//        r.play();

        Intent startIntent = new Intent(context, RingtoneService.class);
        startIntent.putExtra("ringtone-uri", notif_uri);
        context.startService(startIntent);
//        System.out.print("context my broadcast:"+context.toString());
    }

}
