package com.truongsinh.internetbroadcastreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;
import static android.support.v4.content.ContextCompat.getSystemServiceName;

public class InternetReceiver extends BroadcastReceiver {
    static NotificationCompat.Builder mBuilder;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isWifiConnected = networkInfo.isConnected();
        boolean isDataConnected = data.isConnected();
        boolean isConnect = isDataConnected||isWifiConnected;

        mBuilder =
                new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(android.R.drawable.ic_input_add)
                .setContentTitle("My notification")
                .setContentText("Hello World!");

        Intent resulIntent = new Intent(context,ResultActivity.class);
//        Intent resulIntent = new Intent(Intent.ACTION_MANAGE_NETWORK_USAGE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,resulIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(uri);
        NotificationManager notificationManager = (NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);




        if(isConnect == true)
        {
           mBuilder.setContentText("Wifi is connected");
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mBuilder.setContentText("Wifi is not connected");
            Toast.makeText(context, "unconnected", Toast.LENGTH_SHORT).show();
        }
        notificationManager.notify(113312,mBuilder.build());

    }
}
