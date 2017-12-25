package com.gdcp.asus_.googleplay.ui.fragment;

import android.app.LoaderManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/13.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         loadingPage= new LoadingPage(UIUtils.getContext()) {
             @Override
             public View onCreateSuccessView() {
                 return BaseFragment.this.onCreateSuccessView();
             }

             @Override
             public ResultState onLoad() {
                 return BaseFragment.this.onLoad();
             }
         };
        return loadingPage;
    }
    //加载成功后显示的布局，必须由子类来实现
    public abstract View onCreateSuccessView();
    //加载网络数据，返回表示请求网络结束后的状态
    public abstract LoadingPage.ResultState onLoad();

    public void loadData(){
        if (loadingPage!=null){
            loadingPage.loadData();
        }
    }

    public LoadingPage.ResultState check(Object object){
           if (object!=null){
                if (object instanceof ArrayList){
                    ArrayList list= (ArrayList) object;
                    if (list.isEmpty()){
                        return  LoadingPage.ResultState.STATE_EMPTY;
                    }else {
                        return  LoadingPage.ResultState.STATE_SUCEESS;
                    }
                }
           }
           return  LoadingPage.ResultState.STATE_ERROR;
    }
}
