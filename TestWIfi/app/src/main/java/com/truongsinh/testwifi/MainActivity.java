package com.truongsinh.testwifi;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnONOFF;
    TextView txtStatus;
    Button btnDis;
    ListView listDanhSach;
    WifiManager wifiManager;
    InternetReceiver broadcast;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    IntentFilter intentFilter;
    List<WifiP2pDevice> peers = new ArrayList<>();
    String[] deviceNameArray ;

    WifiP2pDevice[] deviceArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnONOFF = findViewById(R.id.btnONOFF);
        txtStatus = findViewById(R.id.txtStatus);
        btnDis = findViewById(R.id.btnDiscover);
        listDanhSach = findViewById(R.id.lvDanhSach);
        btnONOFF.setOnClickListener(this);
        btnDis.setOnClickListener(this);

        wifiManager = (WifiManager)getApplicationContext(). getSystemService(Context.WIFI_SERVICE);
        // this class  provides the api for managing WIFI peer-to-peer connectivity
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

        mChannel = mManager.initialize(MainActivity.this,getMainLooper(),null);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        broadcast = new InternetReceiver(mManager,mChannel,this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnONOFF)
        {
            if(wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(false);
            btnONOFF.setText("ON");
        }
        else
        {
            wifiManager.setWifiEnabled(true);
            btnONOFF.setText("OFF");
        }
        }
        else if(v.getId() == R.id.btnDiscover)
        {
            mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    txtStatus.setText("Discovery Started");
                }

                @Override
                public void onFailure(int reason) {
                    txtStatus.setText("Fail");
                }
            });
        }


    }
    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
         public void onPeersAvailable(WifiP2pDeviceList peers1) {
            if(!peers1.getDeviceList().equals(peers))
            {
                peers.clear();
                peers.addAll(peers1.getDeviceList());
                int index =0 ;
                deviceNameArray = new String[peers1.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peers1.getDeviceList().size()];
                for(WifiP2pDevice x: peers1.getDeviceList())
                {
                    deviceNameArray[index] = x.deviceName;
                    deviceArray[index] = x;
                    index++;
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1
                        ,deviceNameArray);
                listDanhSach.setAdapter(adapter);
            }
            if(peers.size() == 0)
            {
                Toast.makeText(MainActivity.this, "No Device Not Found!", Toast.LENGTH_SHORT).show();
            }
        }
    } ;
    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcast,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}
