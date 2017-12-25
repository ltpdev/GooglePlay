package com.gdcp.asus_.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.SubjectInfo;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/19.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo>{
    private ImageView ivSubject;
    private TextView tvTitle;

    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.item_list_subject);
        ivSubject= (ImageView) view.findViewById(R.id.iv_sub);
        tvTitle= (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        tvTitle.setText(data.getDes());
        Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name="+data.getUrl()).placeholder(R.drawable.ic_default).into(ivSubject);
    }
}
