package com.truongsinh.testwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {
    WifiP2pManager wifiP2pManager;
    WifiP2pManager.Channel mChannel;
    MainActivity mainActivity;

    public InternetReceiver(WifiP2pManager wifiP2pManager, WifiP2pManager.Channel mChannel, MainActivity mainActivity) {
        this.wifiP2pManager = wifiP2pManager;
        this.mChannel = mChannel;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
           int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
           if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
               Toast.makeText(mainActivity, "Wifi ON", Toast.LENGTH_SHORT).show();
           else
               Toast.makeText(mainActivity, "Wifi OFF", Toast.LENGTH_SHORT).show();
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
                if(wifiP2pManager!=null)   wifiP2pManager.requestPeers(mChannel,mainActivity.peerListListener);
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action))
        {

        }else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action))
        {

        }
    }
}
