package com.example.dashixundemo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dashixundemo.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RcyAdapter extends RecyclerView.Adapter {
    private ArrayList<String> list;
    private Context context;

    public RcyAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_rcy, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String s = list.get(position);
        ViewHolder holder1= (ViewHolder) holder;
        holder1.home_recycler_title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder1.home_recycler_title.setText(s);
        Glide.with(context).load(s).into(holder1.home_recycler_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView home_recycler_img;
        public TextView home_recycler_text;
        public TextView home_recycler_title;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.home_recycler_img = (ImageView) rootView.findViewById(R.id.home_recycler_img);
            this.home_recycler_text = (TextView) rootView.findViewById(R.id.home_recycler_text);
            this.home_recycler_title = (TextView) rootView.findViewById(R.id.home_recycler_title);
        }

    }
}
