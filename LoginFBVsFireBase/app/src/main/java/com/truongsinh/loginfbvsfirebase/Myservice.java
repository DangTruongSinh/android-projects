package com.truongsinh.loginfbvsfirebase;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Myservice extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("TAG", "message:"+remoteMessage.getNotification().getBody()+"-"
                        +remoteMessage.getFrom()+"-"+remoteMessage.getData());

    }
}
