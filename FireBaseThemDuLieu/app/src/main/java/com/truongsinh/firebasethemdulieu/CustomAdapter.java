package com.truongsinh.firebasethemdulieu;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<User> ds;
    public CustomAdapter(ArrayList<User> arrayList) {
        this.ds = arrayList;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder viewHolder, int i) {
        User user = ds.get(i);
        viewHolder.hoTen.setText(user.getHoten());
        viewHolder.gioiTinh.setText(user.isGioitinh()+"");
        viewHolder.tuoi.setText(user.getTuoi());
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hoTen,gioiTinh,tuoi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hoTen = itemView.findViewById(R.id.txtHoTen);
            gioiTinh = itemView.findViewById(R.id.txtGioiTinh);
            tuoi = itemView.findViewById(R.id.txtTuoi);
        }
    }
}
