package com.truongsinh.toolbar;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.truongsinh.model.HomeFragment;
import com.truongsinh.model.PastaFragment;
import com.truongsinh.model.PizzasFragment;
import com.truongsinh.model.StoresFragment;

public class MainActivity extends AppCompatActivity implements Runnable{
    HomeFragment homeFragment;
    PastaFragment pastaFragment;
    PizzasFragment pizzasFragment;
    StoresFragment storesFragment;
    int values = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ViewPager pager = findViewById(R.id.view_paper);
        SectionPaperAdapter sectionPaperAdapter = new SectionPaperAdapter(getSupportFragmentManager());
        pager.setAdapter(sectionPaperAdapter);
        TabLayout tab = findViewById(R.id.tab_layout);
        tab.setupWithViewPager(pager);
        //
        homeFragment = new HomeFragment();
        pastaFragment = new PastaFragment();
        pizzasFragment = new PizzasFragment();
        storesFragment = new StoresFragment();

        //
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        shareAction(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action:
                Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                startActivity(intent);
                break;

            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void shareAction(Menu menu) {
        // return menuItem
        MenuItem item = menu.findItem(R.id.shareAction);
        // get ShareActionProvider
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        //create an intent . Note: Don't use startActivity because shareActionProvider'method had describe method startActivity
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"sinh and Lien");
        //setShareIntent
        shareActionProvider.setShareIntent(intent);
    }
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            homeFragment.inCreaseValues((Integer) msg.obj);
        }
    };
    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(2000);
                Message message = new Message();
                message.obj = values;
                handler.sendMessage(message);
                values = ((values+1))%7;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class SectionPaperAdapter extends FragmentPagerAdapter {

        public SectionPaperAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {

                case 0:return  homeFragment;
                case 1: return storesFragment;
                case 2: return pizzasFragment;
                case 3: return pastaFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0: return getResources().getString(R.string.home);
                case 1: return getResources().getString(R.string.stores);
                case 2: return getResources().getString(R.string.pizzas);
                case 3: return getResources().getString(R.string.pasta);
            }
            return super.getPageTitle(position);
        }
    }
}
