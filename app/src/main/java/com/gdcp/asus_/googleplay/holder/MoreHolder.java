package com.gdcp.asus_.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/14.
 */

public class MoreHolder extends BaseHolder<Integer>{
    //加载更多有几种状态
       public static final int STATE_MORE_MORE=1;//可以加载更多
    public static final int STATE_MORE_ERROR=2;//加载错误
    public static final int STATE_MORE_NONE=3;//没有更多数据
    private LinearLayout loadMoreLinearLayout;
    private TextView tvLoadError;

    public MoreHolder(boolean hasMore) {
        setData(hasMore?STATE_MORE_MORE:STATE_MORE_NONE);
    }

    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.item_list_more);
         loadMoreLinearLayout= (LinearLayout) view.findViewById(R.id.ll_load_more);
          tvLoadError= (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refreshView(Integer data) {
            switch (data){
                case STATE_MORE_MORE:
                    loadMoreLinearLayout.setVisibility(View.VISIBLE);
                    tvLoadError.setVisibility(View.GONE);
                    break;
                case STATE_MORE_ERROR:
                    loadMoreLinearLayout.setVisibility(View.GONE);
                    tvLoadError.setVisibility(View.VISIBLE);
                    break;
                case STATE_MORE_NONE:
                    loadMoreLinearLayout.setVisibility(View.GONE);
                    tvLoadError.setVisibility(View.GONE);
                    break;
            }
    }



}
