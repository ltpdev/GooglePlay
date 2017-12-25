package com.gdcp.asus_.googleplay.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.gdcp.asus_.googleplay.R;

/**
 * Created by asus- on 2017/12/19.
 */

public class RatioLayout extends FrameLayout{
    private float ratio;

    public RatioLayout(@NonNull Context context) {
        super(context);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
         ratio=typedArray.getFloat(R.styleable.RatioLayout_ratio,-1);
        typedArray.recycle();
    }


    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY&&heightMode!=MeasureSpec.EXACTLY&&ratio>0){
            int imageWidth=width-getPaddingLeft()-getPaddingRight();
            int imageHeight=(int)(imageWidth/ratio+0.5f);
            height=imageHeight+getPaddingTop()+getPaddingBottom();
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
