package com.truongsinh.mydirectory.controler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truongsinh.mydirectory.R;
import com.truongsinh.mydirectory.Util;
import com.truongsinh.mydirectory.model.ModelItem;

import java.util.ArrayList;

public class AdapterModel extends BaseAdapter {
    private Context context;
    private ArrayList<ModelItem> arr;
    public AdapterModel(Context context, ArrayList<ModelItem> arr)
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
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.layout_item,parent,false);
        }
        LinearLayout mau = view.findViewById(R.id.maunen);
        TextView txtThu = view.findViewById(R.id.txtThu);
        TextView txtNgay = view.findViewById(R.id.txtNgayThang);
        TextView txtTime = view.findViewById(R.id.txtTime);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtContent = view.findViewById(R.id.txtContent);
        ImageView img = view.findViewById(R.id.imgItem);
        ModelItem modelItem = arr.get(position);
        // set time
        String[] dateTime = Util.detachTime(modelItem.tgian);
        txtThu.setText(dateTime[0]);
        txtNgay.setText(dateTime[1]+" "+ dateTime[2]);
        String kq = dateTime[3];
        kq = kq.replace(" ",":");
        txtTime.setText(kq);
        // set mau
        mau.setBackgroundColor(Color.parseColor(modelItem.maMau));
        // set title and content
        txtTitle.setText(modelItem.title);
        txtContent.setText(modelItem.content);
        //

                Bitmap bitmap = Util.readBitmap(modelItem.imgHinh,context);
                img.setImageBitmap(bitmap);



        return view;
    }

}
