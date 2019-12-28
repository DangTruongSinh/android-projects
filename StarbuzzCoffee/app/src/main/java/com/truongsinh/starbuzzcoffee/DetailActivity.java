package com.truongsinh.starbuzzcoffee;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
    public static String EXTRA_TEXT = "extra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int pos = getIntent().getIntExtra(EXTRA_TEXT,0);
        Drinks x = Drinks.drinks[pos];
        // matched
        ImageView imageView = findViewById(R.id.img);
        imageView.setImageResource(x.getImage());
        TextView txtName = findViewById(R.id.name);
        txtName.setText(x.getName());
        TextView txtMota = findViewById(R.id.descri);
        txtMota.setText(x.getMota());
    }
}
