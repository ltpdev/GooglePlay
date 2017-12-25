package com.gdcp.asus_.googleplay.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by asus- on 2017/12/20.
 */

public class DrawableUtils {
    /**
     * 创建圆角矩形
     *
     * @param color
     *            颜色值
     * @param radius
     *            圆角半径
     * @return
     */
    @SuppressLint("WrongConstant")
    public static GradientDrawable getGradientDrawable(int color, int radius){
          GradientDrawable drawable=new GradientDrawable();
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        // 设置颜色
        drawable.setColor(color);
        // 设置圆角半径
        drawable.setCornerRadius(radius);
        return drawable;
    }

    /**
     * 返回状态选择器对象(selector)
     *
     * @param normal
     *            默认图像
     * @param pressed
     *            按下图像
     */
    public static Drawable getStateListDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable stateListDrawable=new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed }, pressed);
        stateListDrawable.addState(new int[] {}, normal);
        return stateListDrawable;
    }
/**
 * 返回状态选择器对象(selector)
 *
 * @param normalColor
 *            默认颜色
 * @param pressedColor
 *            按下颜色
 * @param radius
 *            圆角半径
 * @return
 */
public static Drawable getStateListDrawable(int normalColor, int pressedColor, int radius) {
    Drawable normal = getGradientDrawable(normalColor, radius);
    Drawable pressed = getGradientDrawable(pressedColor, radius);
    return getStateListDrawable(normal, pressed);
}
}
