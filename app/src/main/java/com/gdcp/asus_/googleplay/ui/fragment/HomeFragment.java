package com.gdcp.asus_.googleplay.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.adapter.MyBaseAdapter;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.holder.BaseHolder;
import com.gdcp.asus_.googleplay.holder.HomeHeaderHolder;
import com.gdcp.asus_.googleplay.holder.HomeHolder;
import com.gdcp.asus_.googleplay.http.protocol.HomeProtocol;
import com.gdcp.asus_.googleplay.ui.activity.HomeDetailActivity;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.ui.view.MyListView;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus- on 2017/12/13.
 */

public class HomeFragment extends BaseFragment{
    private ArrayList<AppInfo>data;
    private ArrayList<String> picStrings;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView=new MyListView(UIUtils.getContext());
        HomeHeaderHolder homeHeaderHolder=new HomeHeaderHolder();
        if (picStrings!=null){
            homeHeaderHolder.setData(picStrings);
        }
        myListView.addHeaderView(homeHeaderHolder.getRootView());
        myListView.setAdapter(new HomeAdapter(data));
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AppInfo appInfo=data.get(i-1);//去掉头布局
                if (appInfo!=null){
                    Intent intent=new Intent(UIUtils.getContext(), HomeDetailActivity.class);
                    intent.putExtra("packageName",appInfo.getPackageName());
                    startActivity(intent);
                }
            }
        });
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HomeProtocol homeProtocol=new HomeProtocol();
         data=homeProtocol.getData(0);
        picStrings=homeProtocol.getPicStrings();
        return check(data);
    }
    class HomeAdapter extends MyBaseAdapter<AppInfo>{


        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new HomeHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            HomeProtocol homeProtocol=new HomeProtocol();
            ArrayList<AppInfo>moreData=homeProtocol.getData(getDataSize());
            return moreData;
        }

        @Override
        public boolean hasMore() {
            return true;
        }

        /*@Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view==null){
                view=UIUtils.inflate(R.layout.item_list_home);
                viewHolder=new ViewHolder();
                viewHolder.tvContent= (TextView) view.findViewById(R.id.tv_content);
                view.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) view.getTag();
            }
            String con=getItem(i);
            viewHolder.tvContent.setText(con);
            return view;
        }*/

    }

}
