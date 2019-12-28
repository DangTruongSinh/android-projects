package com.truongsinh.countrywiki;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA = "123";
    ImageView imageView2;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView2 = findViewById(R.id.imageView2);
        textView2  = findViewById(R.id.textView2);


        int position = getIntent().getIntExtra(EXTRA,0);
        ModelItem modelItem = MainActivity.arr.get(position);
        String url = "http://www.geognos.com/api/en/countries/flag/"+ modelItem.iso2Code+".png";
        Util.setBitmapToImage(url,imageView2);
        String result = "Country:"+ modelItem.name+"\n";
        result +="CapitalCity:" + modelItem.capitalCity+"\n";
        result += "Region:" +modelItem.region + "\n";
        result +=  "longitude:"+ modelItem.longitude+"\n";
        result +=  "laitude:"+ modelItem.latitude+"\n";
        textView2.setText(result);
    }
}
