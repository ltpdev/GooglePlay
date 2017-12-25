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
 * Created by asus- on 2017/12/14.
 */

public class AppHolder extends BaseHolder<AppInfo>{
    private TextView tvName;
    private ImageView ivIcon;
   private RatingBar ratingBar;
   private TextView tvSize;
    private TextView tvDesc;

    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.item_list_home);
         tvName= (TextView) view.findViewById(R.id.tv_name);
        ivIcon= (ImageView) view.findViewById(R.id.iv_icon);
        ratingBar=(RatingBar) view.findViewById(R.id.rb_star);
        tvSize=(TextView) view.findViewById(R.id.tv_size);
        tvDesc=(TextView) view.findViewById(R.id.tv_desc);
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.getName());
        ratingBar.setRating(data.getStars());
        tvDesc.setText(data.getDes());
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),
                data.getSize()));
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name="+data.getIconUrl()).placeholder(R.drawable.ic_default).into(ivIcon);
    }
}
