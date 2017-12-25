package com.gdcp.asus_.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/22.
 */

public class DetailAppInfoHolder extends BaseHolder<AppInfo>{
    private TextView tvName;
    private TextView tvDownloadNum;
    private TextView tvSize;
    private TextView tvDate;
    private TextView tvVersion;
    private ImageView ivIcon;
    private RatingBar rbStar;
    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.layout_detail_appinfo);
        tvName= (TextView) view.findViewById(R.id.tv_name);
        tvDownloadNum= (TextView) view.findViewById(R.id.tv_download_num);
        tvSize= (TextView) view.findViewById(R.id.tv_size);
        tvDate= (TextView) view.findViewById(R.id.tv_date);
        tvVersion= (TextView) view.findViewById(R.id.tv_version);
        ivIcon= (ImageView) view.findViewById(R.id.iv_icon);
        rbStar= (RatingBar) view.findViewById(R.id.rb_star);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        if (data!=null){
            tvName.setText(data.getName());
            tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),
                    data.getSize()));
            tvDownloadNum.setText("下载量:" + data.getDownloadNum());
            tvDate.setText(data.getDate());
            tvVersion.setText("版本:" + data.getVersion());
            rbStar.setRating((float) data.getStars());

            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name=" + data.getIconUrl()).into(ivIcon);
                    ;
        }
    }
}
