package com.gdcp.asus_.googleplay.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/21.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>>{
    private ViewPager viewPager;
    ArrayList<String> data;
    private LinearLayout llContainer;
    private int prePosition;

    @Override
    public View initView() {
        RelativeLayout root=new RelativeLayout(UIUtils.getContext());
        //初始化布局参数，根布局上层是listview
        AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,UIUtils.dip2px(180));
        root.setLayoutParams(layoutParams);
         viewPager=new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams layoutParams1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        root.addView(viewPager,layoutParams1);
        //初始化指示器
        int padding=UIUtils.dip2px(10);
         llContainer=new LinearLayout(UIUtils.getContext());
        llContainer.setOrientation(LinearLayout.HORIZONTAL);
        llContainer.setPadding(padding,padding,padding,padding);
        RelativeLayout.LayoutParams llLayoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        llLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        root.addView(llContainer,llLayoutParams);
        return root;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {
        this.data=data;
        viewPager.setAdapter(new HomeHeaderAdapter());
        viewPager.setCurrentItem(data.size()*10000);
        for (int i = 0; i < data.size(); i++) {
            ImageView point=new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams  pointLayoutParams=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i==0){
                     point.setImageResource(R.drawable.indicator_selected);
            }else {
                point.setImageResource(R.drawable.indicator_normal);
                pointLayoutParams.leftMargin=UIUtils.dip2px(4);
            }
            point.setLayoutParams(pointLayoutParams);
            llContainer.addView(point);

        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                position=position%data.size();
                ImageView point= (ImageView) llContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);
                ImageView prePoint= (ImageView) llContainer.getChildAt(prePosition);
                prePoint.setImageResource(R.drawable.indicator_normal);
                prePosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        HomeHeaderTask homeHeaderTask=new HomeHeaderTask();
        homeHeaderTask.start();
    }

    class  HomeHeaderTask implements Runnable{

        public void start(){
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,3000);
        }
        @Override
        public void run() {
            int currentItem=viewPager.getCurrentItem();
            currentItem++;
            viewPager.setCurrentItem(currentItem);
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%data.size();
            String url=data.get(position);
            ImageView imageView=new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name="
                    + url).into(imageView);
            container.addView(imageView);
            return imageView;
        }
    }
}
