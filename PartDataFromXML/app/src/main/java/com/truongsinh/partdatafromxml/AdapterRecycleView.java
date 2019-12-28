package com.truongsinh.partdatafromxml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder> {
    ArrayList<Item> arr;
    phanhoiEvent phanhoiEvent;
    public interface phanhoiEvent{
        void setPhanHoi(int position);
    }

    public AdapterRecycleView(ArrayList<Item> arr,Context context) {
        phanhoiEvent = (AdapterRecycleView.phanhoiEvent) context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public AdapterRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item,viewGroup,false);

        return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycleView.ViewHolder viewHolder, int i) {
        Item item = arr.get(i);

        viewHolder.txt_Description.setText(item.getNoidung());
        viewHolder.txt_title.setText(item.getTitle());
        try {
            viewHolder.imageView.setImageBitmap(new loadImage().execute(item.getImg_hinh()).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_title;
        TextView txt_Description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_hinh);
            txt_title = itemView.findViewById(R.id.txtTitle);
            txt_Description = itemView.findViewById(R.id.txtDesCripsion);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phanhoiEvent.setPhanHoi(getPosition());
                }
            });
        }
    }
    class loadImage extends AsyncTask<String,String,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openStream();
                bitmap  = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }


    }

}
