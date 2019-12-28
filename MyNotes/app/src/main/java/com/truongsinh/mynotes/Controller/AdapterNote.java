package com.truongsinh.mynotes.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.truongsinh.mynotes.Model.Note;
import com.truongsinh.mynotes.R;
import com.truongsinh.mynotes.View.MainActivity;

import java.util.ArrayList;

public class AdapterNote extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private ArrayList<Note> arr;

    public AdapterNote(Context context, ArrayList<Note> arr)
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
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.layout_note,parent,false);
            ViewHolder holder = new ViewHolder();
            holder.txtContent = view.findViewById(R.id.textView2);
            holder.txtDateTime = view.findViewById(R.id.textView3);
            holder.imgDelete = view.findViewById(R.id.imageView);
            view.setTag(holder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Note note = arr.get(position);
        viewHolder.txtContent.setText(note.content);
        viewHolder.txtDateTime.setText(note.dateTime);
        viewHolder.imgDelete.setTag(note);
        viewHolder.imgDelete.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Note note = (Note) v.getTag();
        ((MainActivity)context).deleteNote(note);
    }

    class ViewHolder
    {
        TextView txtContent;
        TextView txtDateTime;
        ImageView imgDelete;
    }
}
