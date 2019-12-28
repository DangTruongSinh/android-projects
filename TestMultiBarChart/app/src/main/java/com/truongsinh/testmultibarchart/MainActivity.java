package com.truongsinh.testmultibarchart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BarChart barChart;
    List<BarEntry> entriesGroup1 = new ArrayList<>();
    List<BarEntry> entriesGroup2 = new ArrayList<>();
    int group1[]={0,0,0,0,0,0,0};
    int group2[]={0,0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barChart = findViewById(R.id.bar_chart);
        // fill the lists
        for(int i = 0; i < group1.length; i++) {
            entriesGroup1.add(new BarEntry(i, group1[i]));
            entriesGroup2.add(new BarEntry(i, group2[i]));
        }
        BarDataSet set1 = new BarDataSet(entriesGroup1, "Nhiệt độ");
        set1.setColor(Color.parseColor("#FF0C68B9"));
        set1.setValueTextSize(10);
        BarDataSet set2 = new BarDataSet(entriesGroup2, "Độ ẩm");
        set2.setColor(Color.parseColor("#FFDF6310"));
        set2.setValueTextSize(10);
        float groupSpace = 0.22f;
        float barSpace = 0.04f; // x2 dataset
        float barWidth = 0.35f; // x2 dataset
// (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
        BarData data = new BarData();
        data.addDataSet(set1);
        data.addDataSet(set2);
        data.setBarWidth(barWidth); // set the width of each bar
        barChart.setData(data);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String days[] = {"Sun","Mon","Tue","Wed","Thur","Fri","Sat"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(groupSpace,barSpace)*7);
        barChart.getAxisLeft().setAxisMinimum(0);
        Description description = new Description();
        description.setText("Nhiệt độ - Độ ẩm");
        barChart.setDescription(description);
        barChart.invalidate(); // refresh
    }


}
