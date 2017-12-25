package com.gdcp.asus_.googleplay.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.holder.DetailAppInfoHolder;
import com.gdcp.asus_.googleplay.holder.DetailAppPicsHolder;
import com.gdcp.asus_.googleplay.holder.DetailAppSafeHolder;
import com.gdcp.asus_.googleplay.holder.DetailDesHolder;
import com.gdcp.asus_.googleplay.holder.DetailDownloadHolder;
import com.gdcp.asus_.googleplay.http.protocol.HomeDetailProtocol;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.utils.UIUtils;

public class HomeDetailActivity extends AppCompatActivity {

    private LoadingPage loadingPage;
    private String packageName;
    private AppInfo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         loadingPage=new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };
        setContentView(loadingPage);
        packageName=getIntent().getStringExtra("packageName");
        loadingPage.loadData();
    }


    public View onCreateSuccessView() {
        View view=UIUtils.inflate(R.layout.page_home_detail);
        FrameLayout appinfoFrameLayout= (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
        DetailAppInfoHolder detailAppInfoHolder=new DetailAppInfoHolder();
        detailAppInfoHolder.setData(data);
        appinfoFrameLayout.addView(detailAppInfoHolder.getRootView());

        FrameLayout flDetailSafeInfo = (FrameLayout) view
                .findViewById(R.id.fl_detail_safeinfo);
        DetailAppSafeHolder appSafeHolder = new DetailAppSafeHolder();
        flDetailSafeInfo.addView(appSafeHolder.getRootView());
        appSafeHolder.setData(data);
        HorizontalScrollView hsvDetailPics = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pics);
        DetailAppPicsHolder detailAppPicsHolder=new DetailAppPicsHolder();
        hsvDetailPics.addView(detailAppPicsHolder.getRootView());
        detailAppPicsHolder.setData(data);
        FrameLayout flDetailDes = (FrameLayout) view
                .findViewById(R.id.fl_detail_des);
        DetailDesHolder detailDesHolder=new DetailDesHolder();
        flDetailDes.addView(detailDesHolder.getRootView());
        detailDesHolder.setData(data);
        FrameLayout flDetailDownload = (FrameLayout) view.findViewById(R.id.fl_detail_download);
        DetailDownloadHolder detailDownloadHolder=new DetailDownloadHolder();
        detailDownloadHolder.setData(data);
        flDetailDownload.addView(detailDownloadHolder.getRootView());
        return view;
    }
    public LoadingPage.ResultState onLoad() {
        HomeDetailProtocol homeDetailProtocol=new HomeDetailProtocol(packageName);
         data=homeDetailProtocol.getData(0);
        if (data!=null){
            return LoadingPage.ResultState.STATE_SUCEESS;
        }else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }

}
