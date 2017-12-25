package com.gdcp.asus_.googleplay.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/22.
 */

public class DetailAppSafeHolder extends BaseHolder<AppInfo>{

    private ImageView[] ivSafes;// 安全标识的控件数组
    private LinearLayout[] llDes;// 安全描述控件数组
    private ImageView[] ivDes;// 安全描述图片控件数组
    private TextView[] tvDes;// 安全描述文字控件数组
    private LinearLayout llDesRoot;// 安全描述根布局
    private int mDesRootHeight;// 安全描述整体布局高度
    private boolean isExpanded = false;// 标记当前安全描述打开还是关闭的状态
    private ImageView ivArrow;// 安全标识小箭头
    private ViewGroup.LayoutParams mParams;// 安全描述整体控件布局参数
    private RelativeLayout rlDesRoot;
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
        ivSafes = new ImageView[4];
        ivSafes[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        ivSafes[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        ivSafes[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        ivSafes[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        llDes = new LinearLayout[4];
        llDes[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        llDes[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        llDes[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        llDes[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        ivDes = new ImageView[4];
        ivDes[0] = (ImageView) view.findViewById(R.id.iv_des1);
        ivDes[1] = (ImageView) view.findViewById(R.id.iv_des2);
        ivDes[2] = (ImageView) view.findViewById(R.id.iv_des3);
        ivDes[3] = (ImageView) view.findViewById(R.id.iv_des4);

        tvDes = new TextView[4];
        tvDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        tvDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        tvDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        tvDes[3] = (TextView) view.findViewById(R.id.tv_des4);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);

        mParams = llDesRoot.getLayoutParams();
        mParams.height = 0;
        llDesRoot.setLayoutParams(mParams);
        rlDesRoot= (RelativeLayout) view.findViewById(R.id.rl_des_root);
        rlDesRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        return view;
    }

    private void toggle() {
        ValueAnimator animator=null;
        if (isExpanded){
            isExpanded=false;
            animator = ValueAnimator.ofInt(mDesRootHeight, 0);
        }else {
            isExpanded=true;
            animator = ValueAnimator.ofInt(0, mDesRootHeight);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                 Integer value= (Integer) valueAnimator.getAnimatedValue();
                mParams.height=value;
                llDesRoot.setLayoutParams(mParams);
            }
        });
      animator.addListener(new AnimatorListenerAdapter() {
          @Override
          public void onAnimationCancel(Animator animation) {
              super.onAnimationCancel(animation);
          }

          @Override
          public void onAnimationEnd(Animator animation) {
              // 更新安全标识小箭头方向
              if (isExpanded) {
                  ivArrow.setImageResource(R.drawable.arrow_up);
              } else {
                  ivArrow.setImageResource(R.drawable.arrow_down);
              }
          }

          @Override
          public void onAnimationRepeat(Animator animation) {
              super.onAnimationRepeat(animation);
          }

          @Override
          public void onAnimationStart(Animator animation) {
              super.onAnimationStart(animation);
          }
      });
// 设置动画时间
        animator.setDuration(200);
        // 开启动画
        animator.start();
    }

    @Override
    public void refreshView(AppInfo data) {
        if (data != null) {
            ArrayList<AppInfo.SafeInfo> safe = data.getSafeInfos();
            if (safe != null) {
                for (int i = 0; i < 4; i++) {
                    if (i < safe.size()) {
                        AppInfo.SafeInfo safeInfo = safe.get(i);

                        ivSafes[i].setVisibility(View.VISIBLE);
                        llDes[i].setVisibility(View.VISIBLE);

                        tvDes[i].setText(safeInfo.getSafeDes());
                        Glide.with(UIUtils.getContext()).load( HttpHelper.URL
                                + "image?name=" + safeInfo.getSafeUrl()).into(ivSafes[i]);
                        Glide.with(UIUtils.getContext()).load( HttpHelper.URL
                                + "image?name=" + safeInfo.getSafeDesUrl()).into(ivDes[i]);
                    } else {
                        ivSafes[i].setVisibility(View.GONE);
                        llDes[i].setVisibility(View.GONE);
                    }
                }
            }

            // 计算安全描述布局的整体高度
            llDesRoot.measure(0, 0);
            mDesRootHeight = llDesRoot.getMeasuredHeight();
        }
    }
}
