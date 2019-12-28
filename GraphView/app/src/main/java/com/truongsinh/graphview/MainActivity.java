package com.truongsinh.graphview;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity  implements Runnable{
    int[][] values = {{2,3},{3,5},{4,6},{5,8},{6,10},{7,5},{8,12},{9,7},{10,9},{12,10},{14,3},{15,6},{16,10},{18,15},{19,3}
,{20,20},{22,24},{23,6},{25,20},{26,23}
};
    LineChart mChart;
    ArrayList<Entry> arrayList = new ArrayList<>();
    ArrayList<Entry> arrayList2 = new ArrayList<>();
    LineDataSet mDataSet;
    LineDataSet mDataSet2;
    LineData lineData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChart = findViewById(R.id.lineChart);
       /* XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new MyformatAxit());*/
        mDataSet = new LineDataSet(arrayList,"lineData1");
        mDataSet2 = new LineDataSet(arrayList2,"LineData2");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(mDataSet);iLineDataSets.add(mDataSet2);
        // Setting mDataSet
        mDataSet.setValueTextSize(9);
        mDataSet.setValueFormatter(new MyFortmat());
        mDataSet.setCircleColors(Color.parseColor("#FFDF1C1F"));
        mDataSet.setCircleHoleColor(Color.parseColor("#FFDF1C1F"));
        mDataSet.setColor(Color.parseColor("#FF267ABF"));
        //Setting mdataset2
        mDataSet2.setValueTextSize(9);
        mDataSet2.setValueFormatter(new MyFortmat());
        mDataSet2.setCircleColors(Color.parseColor("#FF2E6B62"));
        mDataSet2.setCircleHoleColor(Color.parseColor("#FF2E6B62"));
        mDataSet2.setColor(Color.parseColor("#FFB9366D"));
        // create lineData
        lineData = new LineData(iLineDataSets);
        mChart.setData(lineData);
        mChart.invalidate();
        Thread  thread = new Thread(this);
        thread.start();

    }


    private ArrayList<Entry> addData(int x)
    {
        return null;
    }
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int y = (int) msg.obj;
            arrayList.add(new Entry(values[y][0],values[y][1]));
            arrayList2.add(new Entry(values[y][0],values[19-y][1]));
            mDataSet.notifyDataSetChanged();
            mDataSet2.notifyDataSetChanged();
            lineData.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    };
    @Override
    public void run() {
        int y =0;
        while (y < 20)
        {

            try {
                Message message = new Message();
                message.obj = y;
                y++;
                handler.sendMessage(message);
                Log.d("tag","dang chay "+ y);
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MyFortmat implements IValueFormatter
    {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return value+"°";
        }
    }
    class MyformatAxit implements IAxisValueFormatter
    {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return value +"M";
        }
    }
}
