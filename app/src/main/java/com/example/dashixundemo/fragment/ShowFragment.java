package com.example.dashixundemo.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dashixundemo.activity.ShowActivity;
import com.example.dashixundemo.adapter.CatoryAdapter;
import com.example.dashixundemo.R;
import com.example.dashixundemo.adapter.ShowListRcyAdapter;
import com.example.dashixundemo.adapter.ShowRcyAdapter;
import com.example.dashixundemo.base.BasePresenter;
import com.example.dashixundemo.bean.CategoryItem;
import com.example.dashixundemo.bean.ShowList;
import com.example.dashixundemo.constacts.CategoryConstacts;
import com.example.dashixundemo.bean.CategoryPrams;
import com.example.dashixundemo.bean.ShowBean;
import com.example.httplibrary1.utils.LogUtils;
import com.example.mvplibrary.base2.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends BaseMvpFragment<CategoryConstacts.CategoryView, BasePresenter> implements CategoryConstacts.CategoryView {

    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    @BindView(R.id.rcy_shu)
    RecyclerView rcyShu;
    @BindView(R.id.im)
    ImageView im;
    @BindView(R.id.tv)
    TextView tv;
    private ShowRcyAdapter showRcyAdapter;
    private ArrayList<ShowBean> showBeans;
    private ArrayList<ShowList> lists;
    private ShowListRcyAdapter showListRcyAdapter;
    private CatoryAdapter catoryAdapter;
    private CategoryPrams categoryPrams;


    public ShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void showCategoryTab(List<ShowBean> tabs) {
        Log.i("111", "showCategoryTab: "+33333);
        LogUtils.e(tabs.toString());
//       ShowBean showBean = showBeans.get(0);
//        String categoryName = showBean.getCategoryName();
      //  ShowBean bean = new ShowBean();
        showBeans.addAll(tabs);
        catoryAdapter.setNewData(showBeans);
       // showRcyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoryList(List<ShowList> categoryLists) {
        lists.clear();
        lists.addAll(categoryLists);
        showListRcyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showActivity(List<CategoryItem> itemParmesans) {

    }


    @Override
    protected BasePresenter initPreszenter() {
        return new BasePresenter();
    }

    @Override
    protected void initEvent() {


        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getActivity().finish();
            }
        });
        categoryPrams = new CategoryPrams();
        categoryPrams.setParentId("0");
        mPresenter.getCategoryTab(categoryPrams);
        categoryPrams.setParentId("1");
        mPresenter.getCategoryList(categoryPrams);
            initbuju();
          initIntent();

//        showRcyAdapter.setItemOnClick(new ShowRcyAdapter.ItemOnClick() {
//            @Override
//            public void onclick(int position) {
//                CategoryPrams prams = new CategoryPrams();
//                int i = position + 1;
//                prams.setParentId(i+"");
//                mPresenter.getCategoryList(prams);
//            }
//        });
    }

    private void initIntent() {
        showListRcyAdapter.setOnitemClicket(new ShowListRcyAdapter.OnitemClicket() {
            @Override
            public void itemclick(int position) {
                Intent intent = new Intent(getContext(), ShowActivity.class);
                ShowList showList = lists.get(position);
                int id = showList.getId();
                int parentId = showList.getParentId();

                intent.putExtra("id",id);
                intent.putExtra("parentId",parentId);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

    }

    private void initbuju() {

        showBeans = new ArrayList<>();
        lists = new ArrayList<>();
        rcy.setLayoutManager(new LinearLayoutManager(getContext()));
        rcyShu.setLayoutManager(new GridLayoutManager(getActivity(),3));
        rcy.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        // showRcyAdapter = new ShowRcyAdapter(showBeans, getContext());
        showListRcyAdapter = new ShowListRcyAdapter(lists, getActivity());
        //  rcy.setAdapter(showRcyAdapter);
        catoryAdapter = new CatoryAdapter(showBeans);
        rcy.setAdapter(catoryAdapter);
        rcyShu.setAdapter(showListRcyAdapter);

        catoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (i<2){
                    int parentId=i+1;
                    categoryPrams.setParentId(parentId+"");
                    mPresenter.getCategoryList(categoryPrams);
                    catoryAdapter.checks.setValueAt(i,true);
                } else {
                    lists.clear();
                    showListRcyAdapter.notifyDataSetChanged();
                }
                catoryAdapter.setmParme(i);


//                    if(catoryAdapter.checksposition!=-1){
//                        catoryAdapter.checks.setValueAt(catoryAdapter.checksposition,false);
//                        catoryAdapter.notifyItemChanged(catoryAdapter.checksposition);
//                    }

                catoryAdapter.notifyItemChanged(i);
            }
        });



    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_show;
    }

    @Override
    public void onError(String msg, int code) {
        LogUtils.e(msg);
    }

    @Override
    public void onCannal() {

    }


}
