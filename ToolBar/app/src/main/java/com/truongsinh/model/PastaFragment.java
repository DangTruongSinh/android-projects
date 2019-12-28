package com.truongsinh.model;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.truongsinh.toolbar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PastaFragment extends ListFragment {


    public PastaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<String> arr= new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_list_item_1
        ,getResources().getStringArray(R.array.passta_fragment));
        setListAdapter(arr);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
