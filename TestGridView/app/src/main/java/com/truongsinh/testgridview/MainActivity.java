package com.truongsinh.testgridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView ;
    AdapterHinhAnh<HinhAnh> adapterHinhAnh;
    ArrayList<HinhAnh> arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);
        arr.add(new HinhAnh(R.drawable.ledtat,false));
        arr.add(new HinhAnh(R.drawable.ledtat,false));
        arr.add(new HinhAnh(R.drawable.ledtat,false));
        arr.add(new HinhAnh(R.drawable.ledtat,false));
        adapterHinhAnh = new AdapterHinhAnh<HinhAnh>(this,arr,R.layout.layout_grid);
        gridView.setAdapter(adapterHinhAnh);
        
    }
}
