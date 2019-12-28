package com.truongsinh.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.truongsinh.toolbar.R;



public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.ViewHolder> {
    private Listener listener;

    private int imageIds[];
    private String captions[];

    public CustomeAdapter(int[] imageIds, String[] captions) {
        this.imageIds = imageIds;
        this.captions = captions;
    }

    interface Listener
    {
        void onclick(int id);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,
                false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;
        ImageView img = cardView.findViewById(R.id.img_anh);
        TextView txt = cardView.findViewById(R.id.txtDescribe);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(),imageIds[i]);
        img.setImageDrawable(drawable);
        txt.setText(captions[i]);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onclick(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return captions.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView;
        }
    }

    public void setListener(Listener x)
    {
        this.listener = x;
    }


}
