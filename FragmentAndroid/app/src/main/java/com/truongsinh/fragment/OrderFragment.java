package com.truongsinh.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truongsinh.fragment.module.Workout;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private int workId;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null)
            workId = savedInstanceState.getInt("id",0);
        else
        {
            TestFragment testFragment = new TestFragment();
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.watch_fragment,testFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
       View view= getView();
       if(view!=null)
       {
               Workout x = Workout.arr[workId];
               TextView title = view.findViewById(R.id.id_title);
               TextView describe = view.findViewById(R.id.id_describe);
               title.setText(x.getName());
               describe.setText(x.getDescribe());

       }
    }
    public void setWorkId(int id)
    {
        this.workId = id;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",workId);
    }
}
