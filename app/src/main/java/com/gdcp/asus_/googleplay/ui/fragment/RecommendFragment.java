package com.gdcp.asus_.googleplay.ui.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.gdcp.asus_.googleplay.http.protocol.RecommendProtocol;
import com.gdcp.asus_.googleplay.ui.view.LoadingPage;
import com.gdcp.asus_.googleplay.ui.view.fly.ShakeListener;
import com.gdcp.asus_.googleplay.ui.view.fly.StellarMap;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by asus- on 2017/12/13.
 */

public class RecommendFragment extends BaseFragment{
    private ArrayList<String> data;

    @Override
    public View onCreateSuccessView() {
        final StellarMap stellarMap=new StellarMap(UIUtils.getContext());
        int padding =UIUtils.dip2px(10);
        stellarMap.setInnerPadding(padding,padding,padding,padding);
        stellarMap.setAdapter(new RecommendAdapter());
        // 设定展示规则,9行6列(具体以随机结果为准)
        stellarMap.setRegularity(6, 9);
        // 设置默认组为第0组
        stellarMap.setGroup(0, true);
        ShakeListener shakeListener=new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellarMap.zoomIn();
            }
        });
        return stellarMap;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecommendProtocol recommendProtocol=new RecommendProtocol();
       data=recommendProtocol.getData(0);
        return check(data);
    }
    class RecommendAdapter implements StellarMap.Adapter{

        // 返回组的数量
        @Override
        public int getGroupCount() {
            return 2;
        }
        // 某个组下返回孩子的个数
        @Override
        public int getCount(int group) {
            int count=data.size()/getGroupCount();
            //用总数除以组个数就是每组应该展示的孩子的个数
            if (group == getGroupCount() - 1) {// 由于上一行代码不一定整除, 最后一组,将余数补上
                count += data.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            if (group > 0) {// 如果发现不是第一组,需要更新position, 要加上之前几页的总数,才是当前组的准确位置
                position = position + getCount(group - 1) * group;
            }


            TextView view = new TextView(UIUtils.getContext());
            view.setText(data.get(position));
            Random random=new Random();
            int size=16+random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            int r=30+random.nextInt(210);
            int g = 30 + random.nextInt(210);
            int b = 30 + random.nextInt(210);
            view.setTextColor(Color.rgb(r, g, b));
            return view;
        }
//返回哪组
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            // 下一组
            if (!isZoomIn){
               if (group<getGroupCount()-1)  {
                   return group++;
               }else {
                   // 如果没有下一页了,就跳到第一组
                   return 0;
               }
            }else {
                // 上一组
                if (group > 0) {
                    return group--;
                } else {
                    return getGroupCount() - 1;// 如果没有上一页了,就跳到最后一组
                }
            }

        }
    }
}
