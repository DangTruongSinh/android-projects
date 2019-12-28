package com.truongsinh.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<User> list;
    public CustomAdapter(ArrayList<User> arrayList) {
        list = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_tem,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = list.get(i);
        viewHolder.hoten.setText(user.getHoten());
        viewHolder.tuoi.setText(user.getTuoi());
        viewHolder.gioitinh.setText(user.isGioitinh()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hoten,tuoi,gioitinh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hoten = itemView.findViewById(R.id.txtHoTen);
            tuoi = itemView.findViewById(R.id.txtTuoi);
            gioitinh = itemView.findViewById(R.id.txtGioiTinh);
        }
    }
}
