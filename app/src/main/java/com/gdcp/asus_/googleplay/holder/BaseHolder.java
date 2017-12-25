package com.gdcp.asus_.googleplay.holder;

import android.view.View;

/**
 * Created by asus- on 2017/12/14.
 */

public abstract class BaseHolder<T>{
    private View rootView;
    private T data;
    public BaseHolder(){
        rootView=initView();
        rootView.setTag(this);
    }

    public void setData(T data){
        this.data=data;
        refreshView(data);
    }
    // 获取当前item的数据
    public T getData() {
        return data;
    }
    public View getRootView() {
        return rootView;
    }

    public abstract View initView();
    public abstract void refreshView(T data);
}
