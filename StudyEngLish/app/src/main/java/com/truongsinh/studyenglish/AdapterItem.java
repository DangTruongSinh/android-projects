package com.truongsinh.studyenglish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterItem extends BaseAdapter {
    private Context context;
    private ArrayList<ItemModel> arr;
    public AdapterItem(Context context, ArrayList<ItemModel> arr)
    {
        this.context = context;
        this.arr = arr;
    }
    public void setArr(ArrayList<ItemModel> arr)
    {
        this.arr =arr;
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
        View view = convertView;
        if(view ==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.layout_model, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtDescribe = view.findViewById(R.id.txtName);
            view.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtDescribe.setText(arr.get(position).describe);
        return view;
    }
    class ViewHolder
    {
        TextView txtDescribe;
    }
}
