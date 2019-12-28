package com.truongsinh.ungdungdocbao;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements InterfaceAction{
    WebView webView;
    ReceiverInternet receiverInternet;
    public static boolean condition =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //

    }

    @Override
    protected void onStart() {
        super.onStart();
        receiverInternet = new ReceiverInternet(this);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(receiverInternet,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(receiverInternet);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
                webView.goBack();
            return true;


        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleInternetChanged() {
        if(MainActivity.condition)
            initateView();
        else
            setContentView(R.layout.layout_notification);

    }
    private void initateView()
    {


        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web_view);
        final ProgressDialog progressDialog = ProgressDialog.show(this,"Loading","Please wait...");
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         view.loadUrl(url);
                                         return true;
                                     }

                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         super.onPageFinished(view, url);
                                         progressDialog.hide();

                                     }

                                     @Override
                                     public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                                         super.onReceivedError(view, request, error);
                                         Toast.makeText(MainActivity.this, "Please check your internet", Toast.LENGTH_SHORT).show();
                                     }
                                 }
        );
        webView.loadUrl("http://viettuts.vn/");
    }
}
