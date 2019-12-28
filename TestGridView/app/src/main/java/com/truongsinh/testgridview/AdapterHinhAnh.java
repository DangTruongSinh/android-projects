package com.truongsinh.testgridview;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class AdapterHinhAnh<T> extends BaseAdapter {
    Context context;
    ArrayList<HinhAnh> arr;
    int layout;
    boolean checked = false;
    public AdapterHinhAnh(Context context, ArrayList<HinhAnh> arr, int layout) {
        this.context = context;
        this.arr = arr;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
   // class
    class ViewHolder {
       ImageView imageView;
       Switch aSwitch;
   }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final HinhAnh hinhAnh = arr.get(position);
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imgView);
            viewHolder.aSwitch = convertView.findViewById(R.id.switch_bar);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xulySuKienNhan(hinhAnh,viewHolder);
                }
            });
            viewHolder.aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xulySuKienNhan(hinhAnh,viewHolder);
                }
            });
            convertView.setTag(viewHolder);
        }
       else
           viewHolder = (ViewHolder) convertView.getTag();


        viewHolder.imageView.setImageResource(hinhAnh.getImgHinh());

        return convertView;
    }
    private void xulySuKienNhan(HinhAnh hinhAnh,ViewHolder viewHolder)
    {
        if(!hinhAnh.isBlock())
        {
            checked = true;
            viewHolder.imageView.setImageResource(R.drawable.ledsang);

        }
        else
        {
            checked = false;
            viewHolder.imageView.setImageResource(R.drawable.ledtat);
        }
        viewHolder.aSwitch.setChecked(checked);
        hinhAnh.setBlock(checked);
    }
}
