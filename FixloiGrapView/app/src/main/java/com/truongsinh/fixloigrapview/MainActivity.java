package com.truongsinh.fixloigrapview;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arr = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        xuly();
        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

       // Toast.makeText(this, "Chay ne!", Toast.LENGTH_SHORT).show();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(context, "Chay ne", Toast.LENGTH_SHORT).show();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                String s = deviceName + "\n"+deviceHardwareAddress;
                arr.add(s);
                adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arr);
                listView.setAdapter(adapter);

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void xuly() {
        findBluetooth();
    }

    private void findBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        else
        {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 123);

            }
            scanfOldDevice();

            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            scanfOldDevice();
    }

    private void scanfOldDevice()
    {
        // pair
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        Toast.makeText(this, pairedDevices.size()+"", Toast.LENGTH_SHORT).show();
        if (pairedDevices.size() > 0) {

            String s ="";
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                s = deviceName + "-"+deviceHardwareAddress;
                arr.add(s);

            }
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr);
            listView.setAdapter(adapter);
            //
         /*   Intent discoverableIntent =
                    new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 10);
            startActivity(discoverableIntent);*/

    }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //
        bluetoothAdapter.cancelDiscovery();
        unregisterReceiver(receiver);
        //
        String item = arr.get(position);
        String address = item.substring(item.length() - 17);
        Log.i("tag",address);
        Intent intent = new Intent(this,ControlActivity.class);
        intent.putExtra(ControlActivity.EXTRA_ADDRESS,address);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }
}
