package com.truongsinh.countrywiki;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterModel extends BaseAdapter {
    private Context context;
    private ArrayList<ModelItem> arr;
    public  AdapterModel(Context context, ArrayList<ModelItem> arr)
    {
        this.context = context;
        this.arr = arr;
    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        if(view == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.layout_list,parent,false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.textView);
            viewHolder.imageView = view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        ModelItem modelItem = arr.get(position);
        holder.textView.setText(modelItem.name);
        String url = "http://www.geognos.com/api/en/countries/flag/"+ modelItem.iso2Code+".png";
        Util.setBitmapToImage(url,holder.imageView);


        return view;
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
