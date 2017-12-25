package com.gdcp.asus_.googleplay.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gdcp.asus_.googleplay.R;
import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.utils.UIUtils;

/**
 * Created by asus- on 2017/12/23.
 */

public class DetailDesHolder extends BaseHolder<AppInfo> {
    private TextView tvDes;
    private TextView tvAuthor;
    private ImageView ivArrow;
    private RelativeLayout rlDetailToggle;
    private LinearLayout.LayoutParams layoutParams;
    private boolean isOpen=false;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
        tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        rlDetailToggle= (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);
        rlDetailToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvDes.setText(data.getDes());
        tvAuthor.setText(data.getAuthor());
        layoutParams= (LinearLayout.LayoutParams) tvDes.getLayoutParams();
        layoutParams.height=getShortHeight();
        tvDes.setLayoutParams(layoutParams);
    }

    private void toggle(){
        ValueAnimator animator=null;
        int longHeight=getLongHeight();
        int shortHeight=getShortHeight();
        if (isOpen){
            isOpen=false;
            if (longHeight>shortHeight) {
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        }else {
            isOpen=true;
            if (longHeight>shortHeight) {
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }

        if (animator!=null){
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                     Integer value= (Integer) valueAnimator.getAnimatedValue();
                     layoutParams.height=value;
                     tvDes.setLayoutParams(layoutParams);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //ScrollView滑动到最底部
                     final ScrollView scrollView=getScrollView();
                     scrollView.post(new Runnable() {
                         @Override
                         public void run() {
                               scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                         }
                     });

                    if (isOpen) {
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

            animator.setDuration(200);
            animator.start();
        }
    }


    /*获取7行textview的高度
    *
    * */
    private int getShortHeight() {
        int width = tvDes.getMeasuredWidth();
        //模拟一个TextView
        TextView textView = new TextView(UIUtils.getContext());
        //设置文字
        textView.setText(getData().getDes());
        //设置文字大小
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setMaxLines(7);

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，确定值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高度包裹内容，想多大有多大，但不能超过2000
        //开始测量
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }


    /*获取完整textview的高度
   *
   * */
    private int getLongHeight() {
        int width = tvDes.getMeasuredWidth();
        //模拟一个TextView
        TextView textView = new TextView(UIUtils.getContext());
        //设置文字
        textView.setText(getData().getDes());
        //设置文字大小
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);//宽不变，确定值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);//高度包裹内容
        //开始测量
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }


    private ScrollView getScrollView(){
        ViewParent parent=tvDes.getParent();
        while (!(parent instanceof ScrollView)){
            parent=parent.getParent();
        }
        return (ScrollView) parent;
    }

}
