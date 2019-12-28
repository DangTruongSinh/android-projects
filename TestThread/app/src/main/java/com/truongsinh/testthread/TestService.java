package com.truongsinh.testthread;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class TestService extends Service {
    private NotificationManager notifManager;
    Thread thread;
    int m = 6;
    boolean isLive = true;
    String s="";
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String k = intent.getStringExtra("result");
        createNotification(intent);


        {
            if(s.equals(k))
            {
                Toast.makeText(this, "bang "+ isLive, Toast.LENGTH_SHORT).show();
            }
            else
            {
                try {
                    isLive = false;

                    Thread.sleep(1000);
                    isLive = true;
                    Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
                    createThread();
                    s = k;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //


            }
        }

        return START_NOT_STICKY;
    }
    private void createNotification(Intent intent)
    {
        String id = this.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = this.getString(R.string.default_notification_channel_title); // Default Channel
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                notifManager.createNotificationChannel(mChannel);

            }
        }
        builder = new NotificationCompat.Builder(this, id);
        intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentTitle("hgfhg")                            // required
                .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
                .setContentText("cxcxz") // required
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        startForeground(1, notification);
    }
    private void createThread()
    {
        thread=  new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i < 100 && isLive==true)
                {
                    try {
                        Log.i("tag",i+"");
                        i++;
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        thread.start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isLive = false;
        Log.i("tag", "Service onDestroy ");

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}