package com.truongsinh.mydirectory.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.truongsinh.mydirectory.R;
import com.truongsinh.mydirectory.controler.AdapterModel;
import com.truongsinh.mydirectory.controler.SoureAdapter;
import com.truongsinh.mydirectory.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static SoureAdapter soureAdapter;
    public  static ArrayList<ModelItem> arr = new ArrayList<>();
    AdapterModel adapterModel;
    ListView listView;
    TextView txtComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkAndRequestPermissions();
        // findview
        listView = findViewById(R.id.list);
        txtComment = findViewById(R.id.txtComment);
        adapterModel = new AdapterModel(this,arr);
        listView.setAdapter(adapterModel);
        listView.setOnItemClickListener(this);
        //
        soureAdapter = new SoureAdapter(this);
        soureAdapter.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.plus)
        {
            Intent intent = new Intent(MainActivity.this,NewNoteActivity.class);
            startActivity(intent);
        }
        else
        {

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(arr.size() > 0)
                {
                    txtComment.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                       adapterModel  = new AdapterModel(MainActivity.this,arr);
                       listView.setAdapter(adapterModel);
                    //adapterModel.notifyDataSetChanged();
                }
                else
                {
                    txtComment.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }
            }
        };
        super.onResume();
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                arr = soureAdapter.getAllNote();
                handler.sendMessage(new Message());
            }
        };

       thread.start();
    }

    private void updateView() {
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();

                handler.sendMessage(new Message());
            }
        };
        thread.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soureAdapter.close();
    }
    // Yeu cau permission, neu ko yeu cau thi phai vao setting permission cho ung dung
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA,position);
        startActivity(intent);
    }
}
