package com.truongsinh.foodyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.truongsinh.foodyapp.view.fragment.FragmentFood;
import com.truongsinh.foodyapp.view.fragment.FragmentPlaces;

public class AdapterViewPager  extends FragmentStatePagerAdapter {
    FragmentFood fragmentFood;
    FragmentPlaces fragmentPlaces;
    public AdapterViewPager(FragmentManager fm) {
        super(fm);
        fragmentFood = new FragmentFood();
        fragmentPlaces = new FragmentPlaces();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return fragmentPlaces;
            case 1:
                return fragmentFood;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
