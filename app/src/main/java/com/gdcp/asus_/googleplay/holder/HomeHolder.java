package com.gdcp.asus_.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.domain.DownloadInfo;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.manager.DownloadManager;
import com.gdcp.asus_.googleplay.ui.view.ProgressArc;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/14.
 */

public class HomeHolder extends BaseHolder<AppInfo> implements DownloadManager.DownloadObserver, View.OnClickListener {
    private TextView tvName;
    private ImageView ivIcon;
    private RatingBar ratingBar;
    private TextView tvSize;
    private TextView tvDesc;
    private FrameLayout flDownload;
    private TextView tvDownload;
    private ProgressArc pbProgress;
    private DownloadManager mDownloadManager;
    private float mProgress;// 当前下载进度
    private int mCurrentState;// 当前下载状态

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.item_list_home);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        ratingBar = (RatingBar) view.findViewById(R.id.rb_star);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        flDownload = (FrameLayout) view.findViewById(R.id.fl_download);
        tvDownload = (TextView) view.findViewById(R.id.tv_download);
        pbProgress = new ProgressArc(UIUtils.getContext());
        //设置圆形进度条直径
        pbProgress.setArcDiameter(UIUtils.dip2px(26));
        //设置进度条颜色
        pbProgress.setProgressColor(UIUtils.getColor(R.color.progress));
        //设置进度条宽高布局参数
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                UIUtils.dip2px(27), UIUtils.dip2px(27));
        flDownload.addView(pbProgress, params);
        flDownload.setOnClickListener(this);

        mDownloadManager = DownloadManager.getInstance();
        // 监听下载进度
        mDownloadManager.registerObserver(this);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.getName());
        ratingBar.setRating(data.getStars());
        tvDesc.setText(data.getDes());
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),
                data.getSize()));
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.getIconUrl()).placeholder(R.drawable.ic_default).into(ivIcon);
        DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data);
        if (downloadInfo != null) {
            //之前下载过
            mCurrentState = downloadInfo.getCurrentState();
            mProgress = downloadInfo.getProgress();
        } else {
            //没有下载过
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }
        refreshUI(mProgress, mCurrentState, data.getId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_download:
                // 根据当前状态来决定相关操作
                if (mCurrentState == DownloadManager.STATE_UNDO
                        || mCurrentState == DownloadManager.STATE_PAUSE
                        || mCurrentState == DownloadManager.STATE_ERROR) {
                    // 开始下载
                    mDownloadManager.download(getData());
                } else if (mCurrentState == DownloadManager.STATE_DOWNLOADING
                        || mCurrentState == DownloadManager.STATE_WAITING) {
                    // 暂停下载
                    mDownloadManager.pause(getData());
                } else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
                    // 开始安装
                    mDownloadManager.install(getData());
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo downloadInfo) {
        refreshOnMainThread(downloadInfo);
    }

    @Override
    public void onDownloadProgressChanged(DownloadInfo downloadInfo) {
        refreshOnMainThread(downloadInfo);
    }


    // 主线程刷新ui
    private void refreshOnMainThread(final DownloadInfo info) {
        // 判断要刷新的下载对象是否是当前的应用
        AppInfo appInfo = getData();
        if (info.getId().equals(appInfo.getId())) {
            UIUtils.runOnUIThread(new Runnable() {

                @Override
                public void run() {
                    refreshUI(info.getProgress(), info.getCurrentState(), info.getId());
                }
            });
        }
    }


    /**
     * 刷新界面
     *
     * @param progress
     * @param state
     */
    private void refreshUI(float progress, int state, String id) {
        //由于listview重用机制，要确保刷新之前，确实是同一个应用
        if (!getData().getId().equals(id)) {
            return;
        }
        mCurrentState = state;
        mProgress = progress;
        switch (state) {
            case DownloadManager.STATE_UNDO:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText(UIUtils.getString(R.string.app_state_download));
                break;
            case DownloadManager.STATE_WAITING:
                pbProgress.setBackgroundResource(R.drawable.ic_download);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
                tvDownload.setText(UIUtils.getString(R.string.app_state_waiting));
                break;
            case DownloadManager.STATE_DOWNLOADING:
                pbProgress.setBackgroundResource(R.drawable.ic_pause);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
                pbProgress.setProgress(progress, true);
                tvDownload.setText((int) (progress * 100) + "%");
                break;
            case DownloadManager.STATE_PAUSE:
                pbProgress.setBackgroundResource(R.drawable.ic_resume);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                flDownload.setVisibility(View.VISIBLE);
                break;
            case DownloadManager.STATE_ERROR:
                pbProgress.setBackgroundResource(R.drawable.ic_redownload);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload.setText(UIUtils.getString(R.string.app_state_error));
                break;
            case DownloadManager.STATE_SUCCESS:
                pbProgress.setBackgroundResource(R.drawable.ic_install);
                pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
                tvDownload
                        .setText(UIUtils.getString(R.string.app_state_downloaded));
                break;

            default:
                break;
        }
    }

}
