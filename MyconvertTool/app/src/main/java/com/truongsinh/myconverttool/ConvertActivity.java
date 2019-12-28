package com.truongsinh.myconverttool;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ConvertActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA = "124";
    Spinner spinner;
    EditText edtInputConvert;
    TextView txtResult;
    private  int currentUnit=0;
    int position;
    String arr[]={};
    private int id=1;
    private float[][] arrDistanceConvert = {{1,0.000621371f,100f,39.3071f, 1.09361f}, // meter
            {1609.339f, 1, 160933.9f, 63359.8f, 1759.99f},//mile
            {0.01f, 9.999969f, 1, 0.39369f, 0.01093f},//cm
            {0.025399f, 1.5782f, 2.53999f, 1, 0.02777f},//inch
            {0.914f, 0.000568f, 91.4397f, 35.999f, 1}};//yd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // find view
        spinner =findViewById(R.id.list);
        edtInputConvert = findViewById(R.id.inputConvert);
        txtResult = findViewById(R.id.txtResult);
        //Add spinner
        addSpinner();
        edtInputConvert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    conVert(currentUnit,filterConvert(id));
            }
        });
        spinner.setOnItemSelectedListener(this);
        id = getIntent().getIntExtra(EXTRA,1);
    }

    private void conVert(int currentUnit, float[][] arrDistanceConvert) {
        if(edtInputConvert.getText().toString().trim().equals(""))
        {
            txtResult.setText("Result");

        }
        else
        {
            String result="";
            int values = Integer.parseInt(edtInputConvert.getText().toString());
            for(int i =0 ;i < arr.length;i++)
            {
                result += arr[i]+":";
                result += values*arrDistanceConvert[currentUnit][i]+"\n";
            }
            txtResult.setText(result);
        }
    }

    private void addSpinner() {
        position = getIntent().getIntExtra(EXTRA,0);

        switch (position)
        {
            case 0:
                arr = getResources().getStringArray(R.array.distance);
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConvertActivity.this,R.layout.support_simple_spinner_dropdown_item,
                arr);
        spinner.setAdapter(adapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id1) {
        currentUnit = position;
        conVert(currentUnit,filterConvert(id));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private float[][] filterConvert(int id)
    {
        switch (id)
        {
            case 0:
                return arrDistanceConvert;

        }
        return null;
    }
}
