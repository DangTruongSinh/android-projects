package com.truongsinh.fragment;


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

import com.truongsinh.fragment.module.Workout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends ListFragment {
    private Listener1 listen;
    public interface Listener1{
        void setWorkout(int id);
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayAdapter<Workout> adapter = new ArrayAdapter<Workout>(inflater.getContext(),android.R.layout.simple_list_item_1,Workout.arr);
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,null);

}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


       this.listen = (Listener1) context;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        listen.setWorkout(position);
    }
}
