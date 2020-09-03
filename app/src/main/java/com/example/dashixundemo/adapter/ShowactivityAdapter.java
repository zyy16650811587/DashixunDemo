package com.example.dashixundemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dashixundemo.R;
import com.example.dashixundemo.bean.CategoryItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowactivityAdapter extends RecyclerView.Adapter {
    private ArrayList<CategoryItem> lists;
    private Context context;

    public ShowactivityAdapter(ArrayList<CategoryItem> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_category_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ontext!=null){
                    ontext.onitem(holder.getLayoutPosition());
                }
            }
        });

        return holder;
    }
    public interface Ontext{
        void onitem(int positon);
    }
    private Ontext ontext;

    public void setOntext(Ontext ontext) {
        this.ontext = ontext;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryItem categoryItem = lists.get(position);
        ViewHolder holder1= (ViewHolder) holder;
        holder1.tv_cate_item_text.setText(categoryItem.getGoodsDesc());
        int i = Integer.parseInt(categoryItem.getGoodsDefaultPrice());
        int price=i/100;
        holder1.tv_cate_item_money.setText("￥"+price+".00");
        holder1.tv_cate_item_Sales.setText("销量"+categoryItem.getGoodsSalesCount()+""+"件");
        holder1.tv_cate_item_Stock.setText("库存"+categoryItem.getGoodsStockCount()+"");
        Glide.with(context).load(categoryItem.getGoodsDefaultIcon()).into(holder1.iv_cate_item_img);

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public static
    class ViewHolder extends ShowListRcyAdapter.ViewHolder {
        public View rootView;
        public ImageView iv_cate_item_img;
        public TextView tv_cate_item_text;
        public TextView tv_cate_item_money;
        public TextView tv_cate_item_Sales;
        public TextView tv_cate_item_Stock;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.iv_cate_item_img = (ImageView) rootView.findViewById(R.id.iv_cate_item_img);
            this.tv_cate_item_text = (TextView) rootView.findViewById(R.id.tv_cate_item_text);
            this.tv_cate_item_money = (TextView) rootView.findViewById(R.id.tv_cate_item_money);
            this.tv_cate_item_Sales = (TextView) rootView.findViewById(R.id.tv_cate_item_Sales);
            this.tv_cate_item_Stock = (TextView) rootView.findViewById(R.id.tv_cate_item_Stock);
        }

    }
}

