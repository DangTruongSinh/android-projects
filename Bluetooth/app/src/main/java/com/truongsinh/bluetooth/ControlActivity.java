package com.truongsinh.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
/*
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
*/
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ControlActivity extends AppCompatActivity {
    public static final String EXTRA_ADDRESS = "adress";
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    int soluongByte;
    InputStream inputStream;

    // Phan chart
  /*  LineChart mChart;
    ArrayList<Entry> arrayList = new ArrayList<>();
    LineDataSet mDataSet;
    LineData lineData;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        Intent newint = getIntent();
        address = newint.getStringExtra(EXTRA_ADDRESS);
        new ConnectBT().execute();// Khởi tạo 1 đối tượng kế thừa từ AsyncTask và cho nó thực thi

        // chart
      /*  mChart = findViewById(R.id.lineChart);
        mDataSet = new LineDataSet(arrayList,"lineData1");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(mDataSet);
        // Setting mDataSet
        mDataSet.setValueTextSize(9);
        mDataSet.setValueFormatter(new MyFortmat());
        mDataSet.setCircleColors(Color.parseColor("#FFDF1C1F"));
        mDataSet.setCircleHoleColor(Color.parseColor("#FFDF1C1F"));
        mDataSet.setColor(Color.parseColor("#FF267ABF"));
        // create lineData
        lineData = new LineData(iLineDataSets);
        mChart.setData(lineData);
        mChart.invalidate();*/

      /*  final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = btSocket.getInputStream();
                    int soluongByte = inputStream.available();
                    byte[] arr= new byte[soluongByte];

                    if (soluongByte > 0)
                    {
                        inputStream.read(arr);
                        String s = new String(arr,"US-ASCII");;
                        Log.i("tag","so luong byte ="+soluongByte);
                        Log.i("tag",s);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ControlActivity.this, "cc", Toast.LENGTH_SHORT).show();
                handler.post(this);

            }
        });*/

    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            String s;
            // Cause
            byte[] arr= new byte[2];
            while (true)
            {
                if(btSocket.isConnected())
                {try {
                    inputStream = btSocket.getInputStream();
                    soluongByte = inputStream.available();
                 //   Log.i("tag",soluongByte+"");
                    // chu y
                    arr =  new byte[soluongByte];
                 /*   byte[] arr1= new byte[soluongByte];
                    inputStream.read(arr1);
                    Log.i("tag",soluongByte+"");
                  /*  if (soluongByte  == 4)
                    {
                        inputStream.read(arr);
                        s = new String(arr,"US-ASCII");;
                        Log.i("tag","so luong byte ="+soluongByte);
                        Log.i("tag",s.substring(0,2));

                    }*/
               /*  if(soluongByte > 0)
                 {
                     Log.i("tag",soluongByte+"");
                     inputStream.read(new byte[soluongByte]);
                 }*/
               //Tinh so luong byte cho phu hop
                  if(soluongByte >= 1) {
                      inputStream.read(arr);
                      //

                      //
                      s = new String(arr, "US-ASCII");

                      Log.i("tag", s + "     "+soluongByte + "   ");

                  }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                else
                {
                    Log.i("tag","out success");
                    break;
                }
            }

        }

    });
    public void guiOn(View view) {

        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write("Sinh Thu".getBytes());

            Log.i("tag","gui thanh cong");
            inputStream = btSocket.getInputStream();
            soluongByte = inputStream.available();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void guiOff(View view) {
        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write("5".getBytes());

            Log.i("tag","gui thanh cong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> // UI thread
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ControlActivity.this, "Connecting...", "Vui lòng đợi!!!");
        }
        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                Toast.makeText(ControlActivity.this, "Kết nối thất bại!", Toast.LENGTH_LONG).show();
                finish();
            }
            else
            {
                Toast.makeText(ControlActivity.this, "Kết nối thành công!", Toast.LENGTH_LONG).show();
                isBtConnected = true;

            }
            progress.dismiss(); // Ẩn thanh progress


                thread.start();

        }
    }
 /*   class MyFortmat implements IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return value+"°";
        }
    }*/
}
