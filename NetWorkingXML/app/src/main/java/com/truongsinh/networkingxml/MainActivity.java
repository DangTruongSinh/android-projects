package com.truongsinh.networkingxml;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadSever(View view) {
        // Check internet available
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkInfoData = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isWifi = networkInfoWifi.isConnected();
        boolean isData = networkInfoData.isConnected();
        boolean isConnect = isWifi || isData;
        if(isConnect == true)
        {
            Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
//                String x = getDataFromURL("https://www.w3schools.com/xml/cd_catalog.xml");
                String x = getDataFromURL("http://api.worldbank.org/country?format=json");
                Message message = new Message();
                message.obj = x;
                handler.sendMessage(message);
            }
        };
        thread.start();
        }
        else
        {
            Toast.makeText(this, "Not internet", Toast.LENGTH_SHORT).show();
        }

    }

    //create Handler: de update du lieu len view khi background dang chay
    Handler  handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = (String) msg.obj;
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    };

    //------------------------------------
    private String getDataFromURL(String s) {
        String result="";
        final int CONNECTION_TIMEOUT = 3000;
        final int SOCKET_TIMEOUT = 30000;
        // dùng để thiết lập thời gian connection-timeout and socker-timeout
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams,SOCKET_TIMEOUT);
        //
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(s);

        try {
            HttpResponse  httpResponse = httpClient.execute(httpGet);
            if(httpResponse!=null)
            {
                InputStream inputStream = httpResponse.getEntity().getContent();
                result = readData(inputStream);
            }


        }
        catch (ConnectionPoolTimeoutException ex)
        {
            result ="Connection time out";
        }
        catch (SocketTimeoutException ex)
        {
            result ="Socket time out";
        }
        catch (IOException e) {
            e.printStackTrace();
            result ="Loi doc du lieu";
        }
        return result;
    }

    private String readData(InputStream inputStream) {

        // dùng để nối dữ liệu từ internet về
        StringBuilder stringBuilder = new StringBuilder();
        // bộ đệm chứa dữ liệu
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try
        {
            String line ="";
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        }catch (Exception ex)
        {
                stringBuilder.append("Don't load data from Server");
        }
      //  return stringBuilder.toString();
    return filterCountryJson(stringBuilder.toString());
    }
    private String filterCountryJson(String result)
    {
        String line ="";
        try {

            JSONArray jsonArray = new JSONArray(result);
            JSONObject overview = (JSONObject) jsonArray.get(0);
            JSONArray jsonArrayCountry = (JSONArray) jsonArray.get(1);
            line = line+overview.getString("total")+"\n";
            for(int i =0;i <jsonArrayCountry.length();i++)
            {
                JSONObject object = (JSONObject) jsonArrayCountry.get(i);
                JSONObject objectRegion = object.getJSONObject("region");

                line += objectRegion.getString("value")+"\n";

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  line;
    }
    private String filterTitleXML(String s)
    {
        Serializer serializer = new Persister();
        try {
            ListCD listCD = serializer.read(ListCD.class,s);
            String line = "";
            for(CDModel x: listCD.cdModelList)
            {
                line += x.title+ "\n";
            }
            return line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
