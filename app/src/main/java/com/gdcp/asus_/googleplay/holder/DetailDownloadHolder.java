package com.gdcp.asus_.googleplay.holder;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.domain.DownloadInfo;
import com.gdcp.asus_.googleplay.manager.DownloadManager;
import com.gdcp.asus_.googleplay.ui.view.ProgressHorizontal;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.io.File;

/**
 * Created by asus- on 2017/12/23.
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver, View.OnClickListener {
    private DownloadManager downloadManager;
    private int currentState;
    private float currentProgress;
    private FrameLayout flDownload;
    private ProgressHorizontal progressHorizontal;
    private Button btnDownload;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);
        flDownload = (FrameLayout) view.findViewById(R.id.fl_download);
        btnDownload = (Button) view.findViewById(R.id.btn_download);
        progressHorizontal = new ProgressHorizontal(UIUtils.getContext());
        progressHorizontal.setProgressBackgroundResource(R.drawable.progress_bg);//进度条背景图片
        progressHorizontal.setProgressResource(R.drawable.progress_normal);//进度条图片
        progressHorizontal.setProgressTextColor(Color.WHITE);
        progressHorizontal.setProgressTextSize(UIUtils.dip2px(16));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        flDownload.addView(progressHorizontal, layoutParams);
        downloadManager = DownloadManager.getInstance();
        downloadManager.registerObserver(this);
        btnDownload.setOnClickListener(this);
        flDownload.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        //判断当前应用是否下载过
        /*DownloadInfo downloadInfo1=DownloadInfo.copy(data);
        if (downloadInfo1!=null){
            String path=downloadInfo1.getPath();
            File file=new File(path);
            if (file.exists()&&!file.isDirectory()&&file.length()==downloadInfo1.getSize()){
                currentState = DownloadManager.STATE_SUCCESS;
                //currentProgress = 0;
                refreshUI(currentState, currentProgress);
                return;
            }
        }*/
        DownloadInfo downloadInfo = downloadManager.getDownloadInfo(data);
        if (downloadInfo != null) {
            //之前下载过
            currentState = downloadInfo.getCurrentState();
            currentProgress = downloadInfo.getProgress();
        } else {
            //没有下载过

            currentState = DownloadManager.STATE_UNDO;
            currentProgress = 0;
        }
        refreshUI(currentState, currentProgress);

    }

    private void refreshUI(int currentState, float currentProgress) {
        this.currentProgress = currentProgress;
        this.currentState = currentState;
        switch (currentState) {
            case DownloadManager.STATE_UNDO://未下载
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;
            case DownloadManager.STATE_WAITING://等待下载
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中..");
                break;
            case DownloadManager.STATE_DOWNLOADING://正在下载
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                progressHorizontal.setCenterText("");
                progressHorizontal.setProgress(currentProgress);
                break;
            case DownloadManager.STATE_PAUSE://暂停下载
                flDownload.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                progressHorizontal.setCenterText("暂停");
                progressHorizontal.setProgress(currentProgress);
                break;
            case DownloadManager.STATE_ERROR://下载失败
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载失败");
                break;
            case DownloadManager.STATE_SUCCESS://下载成功
                flDownload.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;
        }
    }


    private void refreshUIOnMainThread(final DownloadInfo downloadInfo) {
        AppInfo appInfo = getData();
        //判断下载对象是否是当前应用
        if (appInfo.getId().equals(downloadInfo.getId())) {
            UIUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    refreshUI(downloadInfo.getCurrentState(), downloadInfo.getProgress());
                }
            });
        }
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo downloadInfo) {
        refreshUIOnMainThread(downloadInfo);
        System.out.println("正在下载"+ downloadInfo.getProgress()+"状态："+downloadInfo.getCurrentState());
    }

    @Override
    public void onDownloadProgressChanged(DownloadInfo downloadInfo) {
        Log.i("download", "正在下载" + downloadInfo.getProgress());
        System.out.println("正在下载"+ downloadInfo.getProgress());
        refreshUIOnMainThread(downloadInfo);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_download:
            case R.id.fl_download:
                if (currentState == DownloadManager.STATE_UNDO || currentState == DownloadManager.STATE_ERROR
                        || currentState == DownloadManager.STATE_PAUSE) {
                    downloadManager.download(getData());
                    System.out.println("进入下载");
                } else if (currentState == DownloadManager.STATE_DOWNLOADING || currentState == DownloadManager.STATE_WAITING) {
                    downloadManager.pause(getData());
                } else if (currentState == DownloadManager.STATE_SUCCESS) {
                    downloadManager.install(getData());
                }

                break;
        }
    }
}
