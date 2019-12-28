package com.truongsinh.countrywiki;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {
    ProgressBar progressBar;
    ListView listView;
    AdapterModel adapterModel;
    static ArrayList<ModelItem> arr = new ArrayList<ModelItem>();
    private  int page = 1;
    private final int TOTAL_PAGE = 13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listDs);
        adapterModel = new AdapterModel(MainActivity.this,arr);
        listView.setAdapter(adapterModel);

        listView.setOnItemClickListener(this);
        getData();

    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        final String url ="http://api.worldbank.org/country?format=json&per_page=25&page="+page;
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
               String result = Util.getDatafromURL(url);
               Message message = new Message();
               message.obj = result;
               handler.sendMessage(message);
            }
        };
        thread.start();
    }
    final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            convertToJSon(result);
            progressBar.setVisibility(View.GONE);
            listView.setOnScrollListener(MainActivity.this);
        }
    };
    private void convertToJSon(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONArray object = (JSONArray) jsonArray.get(1);
            for(int i =0 ;i<object.length();i++)
            {
                JSONObject object1 = object.getJSONObject(i);
                ModelItem item = new ModelItem();
                item.iso2Code = object1.getString("iso2Code");
                item.name = object1.getString("name");
                JSONObject objectRegion = object1.getJSONObject("region");
                item.region = objectRegion.getString("value");
                item.capitalCity = object1.getString("capitalCity");
                item.longitude = object1.getString("longitude");
                item.latitude = object1.getString("latitude");
                arr.add(item);
                adapterModel.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Loi o JSOnARRAY", Toast.LENGTH_SHORT).show();
        }


        }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int currentPosition = firstVisibleItem + visibleItemCount;
        if(currentPosition >= totalItemCount )
        {
            listView.setOnScrollListener(null);
            if(page < TOTAL_PAGE)
            {
                page = page + 1;
                getData();
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA,position);
        startActivity(intent);
    }

}
