package com.gdcp.asus_.googleplay.holder;

import android.provider.Contacts;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.CategoryInfo;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/20.
 */

public class CategoryHolder extends BaseHolder<CategoryInfo> implements View.OnClickListener{
    private ImageView ivIcon1, ivIcon2, ivIcon3;
    private TextView tvName1, tvName2, tvName3;
    private LinearLayout llGrid1, llGrid2, llGrid3;
    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(),
                R.layout.list_item_category, null);
        ivIcon1 = (ImageView) view.findViewById(R.id.iv_icon1);
        ivIcon2 = (ImageView) view.findViewById(R.id.iv_icon2);
        ivIcon3 = (ImageView) view.findViewById(R.id.iv_icon3);

        tvName1 = (TextView) view.findViewById(R.id.tv_name1);
        tvName2 = (TextView) view.findViewById(R.id.tv_name2);
        tvName3 = (TextView) view.findViewById(R.id.tv_name3);

        llGrid1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        llGrid2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        llGrid3 = (LinearLayout) view.findViewById(R.id.ll_grid3);
        llGrid1.setOnClickListener(this);
        llGrid2.setOnClickListener(this);
        llGrid3.setOnClickListener(this);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        if (data != null) {
            tvName1.setText(data.getName1());
            tvName2.setText(data.getName2());
            tvName3.setText(data.getName3());
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name="
                    + data.getUrl1()).into(ivIcon1);
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name="
                    + data.getUrl2()).into(ivIcon2);
            Glide.with(UIUtils.getContext()).load(HttpHelper.URL + "image?name="
                    + data.getUrl3()).into(ivIcon3);
        }
    }

    @Override
    public void onClick(View view) {
        CategoryInfo data = getData();
        switch (view.getId()) {
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(), data.getName1(), Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(), data.getName2(), Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(), data.getName3(), Toast.LENGTH_SHORT)
                        .show();
                break;

            default:
                break;
        }
    }
}
