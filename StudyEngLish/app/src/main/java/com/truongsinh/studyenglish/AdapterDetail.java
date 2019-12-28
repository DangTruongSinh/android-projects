package com.truongsinh.studyenglish;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.util.ArrayList;

public class AdapterDetail extends PagerAdapter {
    private Context context;
    private ArrayList<ItemModel> arr;
    public AdapterDetail(Context context, ArrayList<ItemModel> arr)
    {
        this.context = context;
        this.arr = arr;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_detai,container,false);
        WebView webView = view.findViewById(R.id.webView);
        String url = "file:///android_asset/"+arr.get(position).describe+".htm";
        /*final ProgressDialog progressDialog = ProgressDialog.show(context,"Loadding","Please wait.....");
        webView.setWebViewClient(new WebViewClient()
        {
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
                new AlertDialog.Builder(context).setTitle("Error")
                        .setMessage("Don't load data").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                });
            }
        });*/
        webView.loadUrl(url);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        container.addView(view);
        return view;

    }
}
