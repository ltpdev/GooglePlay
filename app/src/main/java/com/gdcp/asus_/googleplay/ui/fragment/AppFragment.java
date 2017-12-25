package com.gdcp.asus_.googleplay.ui.fragment;

import android.view.View;

import com.gdcp.asus_.googleplay.adapter.MyBaseAdapter;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.holder.AppHolder;
import com.gdcp.asus_.googleplay.holder.BaseHolder;
import com.gdcp.asus_.googleplay.http.protocol.AppProtocol;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.ui.view.MyListView;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/13.
 */

public class AppFragment extends BaseFragment{
    private ArrayList<AppInfo> data;

    //数据加载成功回调此方法
    @Override
    public View onCreateSuccessView() {
        MyListView myListView=new MyListView(UIUtils.getContext());
        myListView.setAdapter(new AppAdapter(data));
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol appProtocol=new AppProtocol();
        data=appProtocol.getData(0);
        return check(data);
    }


    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol appProtocol=new AppProtocol();
            ArrayList<AppInfo> moreData = appProtocol.getData(getDataSize());
            return moreData;
        }
    }
}
