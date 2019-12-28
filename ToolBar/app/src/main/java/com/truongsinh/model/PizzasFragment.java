package com.truongsinh.model;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.truongsinh.toolbar.DetailActivity;
import com.truongsinh.toolbar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PizzasFragment extends Fragment {


    public PizzasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.pizzas_fragment,container,false);
        String caption[]=new String[DataPizzas.dataPizzas.length];
        int id[]= new int[DataPizzas.dataPizzas.length];
        for(int i = 0;i<DataPizzas.dataPizzas.length;i++)
        {
            caption[i] = DataPizzas.dataPizzas[i].getCaption();
            id[i] = DataPizzas.dataPizzas[i].getId();
        }
        CustomeAdapter customeAdapter = new CustomeAdapter(id,caption);
        customeAdapter.setListener(new CustomeAdapter.Listener() {
            @Override
            public void onclick(int id) {
                Intent intent = new Intent(inflater.getContext(),DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID,id);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(customeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        return recyclerView;
    }

}
