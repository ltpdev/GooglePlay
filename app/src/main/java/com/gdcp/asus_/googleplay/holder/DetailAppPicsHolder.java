package com.gdcp.asus_.googleplay.holder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/23.
 */

public class DetailAppPicsHolder extends BaseHolder<AppInfo>{
    private ImageView[] mImageViews;

    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.layout_detail_picinfo);
        mImageViews = new ImageView[5];
        mImageViews[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        mImageViews[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        mImageViews[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        mImageViews[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        mImageViews[4] = (ImageView) view.findViewById(R.id.iv_pic5);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        if (data != null) {
            ArrayList<String> list = data.getScreen();
            for (int i = 0; i < 5; i++) {
                if (i < list.size()) {
                    mImageViews[i].setVisibility(View.VISIBLE);
                    Glide.with(UIUtils.getContext()).load(HttpHelper.URL
                            + "image?name=" + list.get(i)).into(mImageViews[i]);
                } else {
                    mImageViews[i].setVisibility(View.GONE);
                }
            }
        }
    }



}
