package com.gdcp.asus_.googleplay.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gdcp.asus_.googleplay.http.protocol.HotProtocol;
import com.gdcp.asus_.googleplay.ui.view.FlowLayout;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.utils.DrawableUtils;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by asus- on 2017/12/13.
 */

public class HotFragment extends BaseFragment{
    private ArrayList<String> data;

    @Override
    public View onCreateSuccessView() {
        int padding = UIUtils.dip2px(10);
        // 为了使布局可以上下滑动,需要用ScrollView包装起来
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        // 设置ScrollView边距
        scrollView.setPadding(padding, padding, padding, padding);
        FlowLayout flowLayout=new FlowLayout(UIUtils.getContext());
        for (int i = 0; i < data.size(); i++) {
            TextView textView=new TextView(UIUtils.getContext());
            textView.setText(data.get(i));
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(padding, padding, padding, padding);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            Random random = new Random();
            int r = 30 + random.nextInt(210);
            int g = 30 + random.nextInt(210);
            int b = 30 + random.nextInt(210);

            int color = 0xffcecece;// 按下后偏白的背景色

            // 根据默认颜色和按下颜色, 生成圆角矩形的状态选择器
            Drawable selector = DrawableUtils.getStateListDrawable(
                    Color.rgb(r, g, b), color, UIUtils.dip2px(6));
            textView.setBackgroundDrawable(selector);
            textView.setClickable(true);
           /* textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });*/
            flowLayout.addView(textView);
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }

    //子类在这请求网络数据，并且将请求结果返回
    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        data = protocol.getData(0);
        return check(data);
    }
}
