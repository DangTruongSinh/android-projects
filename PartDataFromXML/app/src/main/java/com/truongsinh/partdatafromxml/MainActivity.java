package com.truongsinh.partdatafromxml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements Runnable,AdapterRecycleView.phanhoiEvent {
    RecyclerView recyclerView;
    AdapterRecycleView adapterRecycleView;
    Item item;
    ArrayList<Item> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyle_view);
        Thread thread = new Thread(this);
        thread.start();

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        adapterRecycleView = new AdapterRecycleView(arrayList,this);
        recyclerView.setAdapter(adapterRecycleView);


    }

    private void dowloadDataFromInterNet(String url) {
        try {
            URL url1 = new URL(url);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url1.openConnection();
            httpsURLConnection.connect();

            InputStream inputStream = httpsURLConnection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
           /* BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while((line = bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
                Log.d("Tag",line);
            }*/
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(inputStream,null);
            int events = xmlPullParser.getEventType();
            String s="";
            while (events!= XmlPullParser.END_DOCUMENT) {
                switch (xmlPullParser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equals("item"))
                            item = new Item();
                        break;
                    case XmlPullParser.TEXT:
                        s = xmlPullParser.getText();

                        break;
                    case XmlPullParser.END_TAG:
                        String result = xmlPullParser.getName();
                        if(result.equals("title") && item!=null)
                            item.setTitle(s);
                        else if(result.equals("link")&& item!=null)
                            item.setNoidung(s);
                        else if(result.equals("image")&& item!=null)
                            item.setImg_hinh(s);
                        if(xmlPullParser.getName().equals("item"))
                            arrayList.add(item);
                        break;

                }
                events = xmlPullParser.next();
            }
        //    Log.d("Tag",arrayList.size()+"");
            for(Item item1 : arrayList)
            {
                Log.d("Tag",item1.getTitle());
                Log.d("Tag",item1.getNoidung());
                Log.d("Tag",item1.getImg_hinh());
            }
            httpsURLConnection.disconnect();
        //    bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null ||!networkInfo.isConnected())
        {

        }
        else
        {
            dowloadDataFromInterNet("https://vietnamnet.vn/rss/tin-noi-bat.rss");
        }
    }

    @Override
    public void setPhanHoi(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
