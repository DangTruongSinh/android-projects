package com.truongsinh.starbuzzcoffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        ListView lv = findViewById(R.id.lv_drink);
        ArrayAdapter<Drinks> x = new ArrayAdapter<Drinks>(this,android.R.layout.simple_list_item_1,Drinks.drinks);
        lv.setAdapter(x);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DrinkCategoryActivity.this,DetailActivity
                        .class);
                intent.putExtra(DetailActivity.EXTRA_TEXT,position);
                startActivity(intent);
            }
        });
    }
}
