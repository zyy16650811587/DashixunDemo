package com.example.dashixundemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.dashixundemo.bean.CategoryDitail;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CateGroyAdapter extends RecyclerView.Adapter {
    private ArrayList<CategoryDitail> list;
    private Context context;

    public CateGroyAdapter(ArrayList<CategoryDitail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
