package com.truongsinh.countrywiki;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Util {


    public static String getDatafromURL(String url){
        String result ="";
        final int CONNECTION_TIMEOUT = 3000;
        final int SOCKET_TIMEOUT = 30000;
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams,SOCKET_TIMEOUT);
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            InputStream inputStream = httpResponse.getEntity().getContent();
            result = convertStreamToString(inputStream);
        }
        catch (ConnectTimeoutException ex)
        {
            result ="ConnectionTimeoutException";
        }
        catch (SocketTimeoutException ex)
        {
            result = "SocketTimeoutException";
        }
        catch (IOException e) {
            result ="Loi IO";
        }
        return result;
    }
    private static String convertStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String  line ="";
        try
        {
            while ((line = bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        }
        catch (Exception ex)
        {
            line="Loi read data";
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
    public static Drawable loadImageFromWebOperations(String url)
    {
        try {
            InputStream inputStream = (InputStream) new URL(url).getContent();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Drawable drawable = Drawable.createFromStream(bufferedInputStream,"sinh");
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void setBitmapToImage(final String url, final ImageView imageView)
    {
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                if(drawable!=null)
                    imageView.setImageDrawable(drawable);

            }
        };
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                Drawable drawable =loadImageFromWebOperations(url);
                Message message = new Message();
                message.obj = drawable;
                handler.sendMessage(message);
            }
        };
        thread.start();
    }
    public static void loadDataCach2(final String url, final Context context)
    {
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = (String) msg.obj;
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        };
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    InputStream inputStream = (InputStream) new URL(url).getContent();
                    String result = convertStreamToString(inputStream);
                    Message message = new Message();
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
