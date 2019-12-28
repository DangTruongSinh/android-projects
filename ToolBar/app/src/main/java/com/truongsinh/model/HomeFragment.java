package com.truongsinh.model;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.truongsinh.toolbar.R;

import java.security.KeyStore;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    int valuesHomeFragment = 0;
    LineChart mChart;
    ArrayList<Entry> arrayList = new ArrayList<>();
    LineDataSet mDataSet;
    LineData lineData;
    boolean flag;

    public HomeFragment() {
        // Required empty public constructor
    }
    public void inCreaseValues(int y)
    {
        arrayList.add(new Entry(y, y+1));
        if(y == 6)
            arrayList.clear();
        if(flag ==true)
        {
            mDataSet.notifyDataSetChanged();
            lineData.notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.invalidate();

            Log.d("tag", valuesHomeFragment + "");
        }
    }
    @Override
    public void onAttach(Context context) {
        Log.d("tag","onAttach");
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("tag","onCreateView");
        flag = true;
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onStart() {

        mChart = getView().findViewById(R.id.lineChart);
        mDataSet = new LineDataSet(arrayList,"lineData1");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(mDataSet);
        // Setting mDataSet
        mDataSet.setValueTextSize(9);
        mDataSet.setValueFormatter(new MyFortmat());
        mDataSet.setCircleColors(Color.parseColor("#FFDF1C1F"));
        mDataSet.setCircleHoleColor(Color.parseColor("#FFDF1C1F"));
        mDataSet.setColor(Color.parseColor("#FF267ABF"));
        lineData = new LineData(iLineDataSets);
        mChart.setData(lineData);
        mChart.invalidate();
        Log.d("tag","onStart");

        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d("tag","onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("tag","onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        flag = false;
        Log.d("tag","onDestroyView");
    }

    @Override
    public void onDestroy() {
        Log.d("tag","onDestroy");
        super.onDestroy();

    }
    class MyFortmat implements IValueFormatter
    {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return value+"°";
        }
    }
}
