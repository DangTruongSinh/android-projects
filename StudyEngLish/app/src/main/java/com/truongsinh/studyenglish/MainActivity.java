package com.truongsinh.studyenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static ArrayList<ItemModel> arrayList = new ArrayList<>();
    private ListView listView;
    private EditText editText;
    private int mode = 0;

    private final ArrayList<ItemModel> arrSearch = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find view
        listView = findViewById(R.id.lvDanhSach);
        editText = findViewById(R.id.edtInput);
        // adapter
        final AdapterItem adapterItem = new AdapterItem(this,arrayList);
        //
        initiateItem();
        
        listView.setAdapter(adapterItem);
        listView.setOnItemClickListener(this);
        //
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().length() == 0)
                {
                    adapterItem.setArr(arrayList);
                    mode = 0;
                }
                else
                {
                    mode = 1;
                   arrSearch.clear();
                   for(ItemModel x : arrayList) {
                       if (x.describe.toLowerCase().contains(editText.getText().toString().trim()))
                       {
                           arrSearch.add(x);
                       }

                   }
                   adapterItem.setArr(arrSearch);

                }
                listView.setAdapter(adapterItem);
            }
        });

    }

    private void initiateItem() {
        arrayList.add(new ItemModel(0,"About"));
        arrayList.add(new ItemModel(1,"Asking questions 1"));
        arrayList.add(new ItemModel(2,"Asking questions 2"));
        arrayList.add(new ItemModel(3,"Can"));
        arrayList.add(new ItemModel(4,"Can have and could have"));
        arrayList.add(new ItemModel(5,"Could"));
        arrayList.add(new ItemModel(6,"For"));
        arrayList.add(new ItemModel(7,"For 2"));
        arrayList.add(new ItemModel(8,"Going to or will"));
        arrayList.add(new ItemModel(9,"Had better"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,DetailActivity.class);
        ItemModel itemModel;
        if(mode == 0)
           itemModel= arrayList.get(position);
        else
            itemModel = arrSearch.get(position);

        intent.putExtra(DetailActivity.EXTRA,itemModel.id);
        startActivity(intent);
    }
}
