package com.example.dashixundemo.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.dashixundemo.GalleryFlow;
import com.example.dashixundemo.R;
import com.example.dashixundemo.adapter.MyViewPagerAdapter;
import com.example.dashixundemo.adapter.RcyAdapter;
import com.example.dashixundemo.huabu.GallyPageTransformer;
import com.example.dashixundemo.zxing.ScanActivity;
import com.example.dashixundemo.zxing.utils.BitmapUtil;
import com.example.mvplibrary.base2.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuyeFragment extends BaseFragment {

    private static final int PERMS_REQUEST_CODE =202 ;
    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.im)
    ImageView im;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    private int SCAN_REQUEST_CODE=200;
    private int SELECT_IMAGE_REQUEST_CODE=201;
    GalleryFlow galleryFlow;
    String HOME_BAN_ONE = "http:\\/\\/www.qubaobei.com\\/ios\\/cf\\/uploadfile\\/132\\/11\\/10509.jpg";

    String HOME_BAN_TWO = "http://www.qubaobei.com//ios//cf//uploadfile//132//11//10172.jpg";

    String HOME_BAN_THREE = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502876281925&di=f33e7ef8be268e90ffbffd315f5fb0a3&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F013e1b57d2731c0000018c1beeca11.jpg%40900w_1l_2o_100sh.jpg";
    String HOME_BAN_FOUR = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503471047&di=679d7a6c499f59d1b0dcd56b62a9aa6c&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.90sheji.com%2Fdianpu_cover%2F11%2F14%2F64%2F55%2F94ibannercsn_1200.jpg";
    /*
       首页折扣图片
     */
    String HOME_DISCOUNT_ONE = "https://img14.360buyimg.com/n0/jfs/t3157/231/5756125504/98807/97ab361d/588084a1N06efb01d.jpg";
    String HOME_DISCOUNT_TWO = "https://img10.360buyimg.com/n7/jfs/t5905/106/1120548052/61075/6eafa3a5/592f8f7bN763e3d30.jpg";
    String HOME_DISCOUNT_THREE = "https://img10.360buyimg.com/n7/jfs/t5584/99/6135095454/371625/423b9ba5/59681d91N915995a7.jpg";
    String HOME_DISCOUNT_FOUR = "https://img11.360buyimg.com/n7/jfs/t4447/301/1238553109/193354/13c7e995/58db19a7N25101fe4.jpg";
    String HOME_DISCOUNT_FIVE = "https://img14.360buyimg.com/n1/s190x190_jfs/t7525/189/155179632/395056/e200017f/598fb8a4N7800dee6.jpg";
    String HOME_TOPIC_FOUR = "http://img.zcool.cn/community/01796c58772f66a801219c77e4fc04.png@1280w_1l_2o_100sh.png";
    String HOME_TOPIC_FIVE = "http://img.zcool.cn/community/0154805791ffeb0000012e7edba495.jpg@900w_1l_2o_100sh.jpg";


    @BindView(R.id.vf)
    ViewFlipper vf;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.linearLayout3)
    LinearLayout ll_main;
    @BindView(R.id.saoyisao)
    ImageView saoyisao;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;


    private ArrayList<String> list;
    private RcyAdapter rcyAdapter;
    private ArrayList<String> title;
    private int pagerWidth;
    private ArrayList<String> strings;
    private ArrayList<ImageView> imageViews;


    public ZhuyeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zhuye, container, false);
        ButterKnife.bind(this, view);

        //6.0版本或以上需请求权限
        String[] permissions = new String[]{Manifest.permission.
                WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }


        mViewPager.setOffscreenPageLimit(3);
        pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        mViewPager.setLayoutParams(lp);
        mViewPager.setPageMargin(-50);
        ll_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });
        mViewPager.setPageTransformer(true, new GallyPageTransformer());
        mViewPager.setAdapter(new MyViewPagerAdapter(imageViews));
        mViewPager.setPageTransformer(true, new GallyPageTransformer());
        mViewPager.setAdapter(new MyViewPagerAdapter(imageViews));
        initListener();
        return view;
    }

    @Override
    protected void initData() {
        ArrayList<String> image = new ArrayList<>();
        image.add(HOME_DISCOUNT_ONE);
        image.add(HOME_BAN_TWO);
        image.add(HOME_BAN_THREE);
        image.add(HOME_BAN_FOUR);
        banner.setImages(image)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                })
                .setDelayTime(3000)
                .isAutoPlay(true)
                .start();
        initTong();
        initRcy();
        initData1();

    }

    private void initListener() {
        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ScanActivity.class);
                startActivityForResult(intent,SCAN_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SELECT_IMAGE_REQUEST_CODE){//从图库选择图片
            String[] proj = {MediaStore.Images.Media.DATA};
            // 获取选中图片的路径
            Cursor cursor = getContext().getContentResolver().query(data.getData(),proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String photoPath = cursor.getString(columnIndex);
                String result= BitmapUtil.parseQRcode(photoPath);
                if (!TextUtils.isEmpty(result)) {
                    Toast.makeText(mActivity, "从图库选择的图片识别结果:"+result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "从图库选择的图片不是二维码图片", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        }else if (requestCode == SCAN_REQUEST_CODE && resultCode == -1) {
            String input = data.getStringExtra(ScanActivity.INTENT_EXTRA_RESULT);
            Toast.makeText(mActivity, "扫描结果:"+input, Toast.LENGTH_SHORT).show();
        }
    }
    private void initTong() {
        title = new ArrayList<>();
        title.add("没有什么退路，只有咬牙坚持走下去的路！");
        title.add("今天的你依旧帅气如初 ");
        title.add("愿十年之后的自己会感谢当初努力奋斗的你");

        for (int i = 0; i < title.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.title_view, null);
            TextView tvTitle = view.findViewById(R.id.tvItem);
            //赋值
            tvTitle.setText(title.get(i));
            //动态添加视图
            vf.addView(view);
        }

        //设置的时间间隔来开始切换所有的View,切换会循环进行
        vf.startFlipping();
        //视图进入动画
        vf.setInAnimation(context, R.anim.new_in);
        //视图退出动画
        vf.setOutAnimation(context, R.anim.new_out);
        //自动开始滚动
        vf.setAutoStart(true);
        //视图的切换间隔
        vf.setFlipInterval(3000);


    }

    private void initRcy() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcy.setLayoutManager(layoutManager);
//        rcy.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        list = new ArrayList<>();
        list.add(HOME_DISCOUNT_ONE);
        list.add(HOME_DISCOUNT_TWO);
        list.add(HOME_DISCOUNT_THREE);
        list.add(HOME_DISCOUNT_FOUR);
        list.add(HOME_DISCOUNT_FIVE);

        rcyAdapter = new RcyAdapter(list, getActivity());
        rcy.setAdapter(rcyAdapter);

    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_zhuye;
    }


    private void initData1() {
        imageViews = new ArrayList<>();
        ImageView first = new ImageView(getActivity());
        Glide.with(this).load(R.drawable.cccc).into(first);
        //    first.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.cccc, getActivity()));
//        first.setImageResource(R.mipmap.first);
        ImageView second = new ImageView(getActivity());
        // second.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.caise, getActivity()));
        Glide.with(this).load(R.drawable.caise).into(second);
//        second.setImageResource(R.mipmap.second);
        ImageView third = new ImageView(getActivity());
        // third.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.cccc, getActivity()));
        Glide.with(this).load(R.drawable.cccc).into(third);
//        third.setImageResource(R.mipmap.third);
        ImageView fourth = new ImageView(getActivity());
        //  fourth.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.caise,getActivity()));
        //      fourth.setImageResource(R.drawable.cccc);
        Glide.with(this).load(R.drawable.caise).into(fourth);
        ImageView fifth = new ImageView(getActivity());
        Glide.with(this).load(R.drawable.cccc).into(fifth);
        //   fifth.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.carr, getActivity()));
//        fifth.setImageResource(R.mipmap.fifth);
        imageViews.add(first);
        imageViews.add(second);
        imageViews.add(third);
        imageViews.add(fourth);
        imageViews.add(fifth);
    }
}

