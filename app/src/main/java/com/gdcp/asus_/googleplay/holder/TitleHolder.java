package com.gdcp.asus_.googleplay.holder;

import android.view.View;
import android.widget.TextView;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.CategoryInfo;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/20.
 */

public class TitleHolder  extends BaseHolder<CategoryInfo>{
    private TextView tvTitle;

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(),
                R.layout.list_item_title, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvTitle.setText(data.getTitle());
    }
}
