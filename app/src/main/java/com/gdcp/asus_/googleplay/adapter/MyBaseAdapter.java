package com.gdcp.asus_.googleplay.adapter;

import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.gdcp.asus_.googleplay.holder.BaseHolder;
import com.gdcp.asus_.googleplay.holder.MoreHolder;
import com.gdcp.asus_.googleplay.manager.ThreadManager;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus- on 2017/12/14.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    private static  final int TYPE_NORMAL=1;//正常布局类型
    private static  final int TYPE_MORE=0;//加载更多布局类型
    private ArrayList<T>data;
    public MyBaseAdapter(ArrayList<T> data){
          this.data=data;
    }
    @Override
    public int getCount() {
        return data.size()+1;
    }

    public int getDataSize(){
        return data.size();
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getCount()-1){//最后一个
            return TYPE_MORE;
        }else {
            return getInnerType(position);
        }

    }

    public int getInnerType(int position){
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseHolder baseHolder;
        if (view==null){
            if (getItemViewType(i)==TYPE_MORE){
                baseHolder=new MoreHolder(hasMore());
            }else {
                baseHolder=getHolder(i);
            }
        }else{
            baseHolder= (BaseHolder) view.getTag();
        }
        if (getItemViewType(i)!=TYPE_MORE){
            baseHolder.setData(getItem(i));
        }else {
             //加载更多布局
            MoreHolder holder= (MoreHolder) baseHolder;
            if (holder.getData()==MoreHolder.STATE_MORE_MORE){
                loadMore(holder);
            }
        }

        return baseHolder.getRootView();
    }

    public boolean hasMore(){
        return true;
    }

    public abstract BaseHolder<T> getHolder(int position);
  private boolean isLoadMore=false;
    //加载更多数据
    public void loadMore(final MoreHolder holder){
        if (!isLoadMore) {
            isLoadMore=true;
           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();

                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                if (moreData.size() < 20) {
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据", Toast.LENGTH_LONG).show();
                                } else {
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                data.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                //加载更多失败
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore=false;
                        }
                    });
                }
            }).start();*/
            ThreadManager.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();

                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                if (moreData.size() < 20) {
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据", Toast.LENGTH_LONG).show();
                                } else {
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                data.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                //加载更多失败
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore=false;
                        }
                    });
                }
            });

        }
    }
    //加载更多数据，由子类实现
    public abstract ArrayList<T> onLoadMore();
}
