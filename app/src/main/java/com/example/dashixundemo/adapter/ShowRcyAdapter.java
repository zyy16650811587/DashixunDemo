package com.example.dashixundemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dashixundemo.R;
import com.example.dashixundemo.bean.ShowBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.OnItemClick;

public class ShowRcyAdapter extends RecyclerView.Adapter {
    private ArrayList<ShowBean> list;
    private Context context;
    private boolean isSelect;

    public ShowRcyAdapter(ArrayList<ShowBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_item_rcy, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemOnClick != null){
                    itemOnClick.onclick(holder.getLayoutPosition());
                }
            }
        });
        return holder;
    }
    public interface ItemOnClick{
        void onclick(int position);
    }
    public ItemOnClick itemOnClick;

    public void setItemOnClick(ItemOnClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShowBean showBean = list.get(position);
        ViewHolder holder1= (ViewHolder) holder;
        holder1.tv.setText(showBean.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static
    class ViewHolder extends RcyAdapter.ViewHolder {
        public View rootView;
        public TextView tv;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.tv = (TextView) rootView.findViewById(R.id.tv);
        }

    }
}
