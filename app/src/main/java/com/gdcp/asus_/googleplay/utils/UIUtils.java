package com.gdcp.asus_.googleplay.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.AndroidCharacter;
import android.view.View;

import com.gdcp.asus_.googleplay.app.GooglePlayApp;

/**
 * Created by asus- on 2017/12/12.
 */

public class UIUtils {

    public static Context getContext(){
        return GooglePlayApp.getContext();
    }
    public static Handler getHandler(){
        return GooglePlayApp.getHandler();
    }
    public static int  getMainThreadId(){
        return GooglePlayApp.getMainThreadId();
    }

    //获取字符串数组
    public static String[]  getStringArray(int id){
        return getContext().getResources().getStringArray(id);
    }

    //获取字符串
    public static String  getString(int id){
        return getContext().getResources().getString(id);
    }

    //获取图片
    public static Drawable getDrawable(int id){
        return getContext().getResources().getDrawable(id);
    }
    //获取颜色
    public static int getColor(int id){
        return getContext().getResources().getColor(id);
    }

    //获取尺寸(返回具体像素值)
    public static int getDimen(int id){
        return getContext().getResources().getDimensionPixelSize(id);
    }
    //dip转px
    public static int dip2px(float dip){
        float density=getContext().getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }
    //px转dip
    public static float px2dip(int px){
        float density=getContext().getResources().getDisplayMetrics().density;
        return px/density;
    }
    //加载布局
    public static View  inflate(int id){
        return  View.inflate(getContext(),id,null);
    }
    //判断是否运行在主线程
    public static boolean isRunOnUIThread(){
      int mid= android.os.Process.myTid();
      if(mid==getMainThreadId()){
                  return true;
      }
      return false;
    }

//运行在主线程
    public static void  runOnUIThread(Runnable runnable){
        if (isRunOnUIThread()){
            runnable.run();
        }else {
            getHandler().post(runnable);
        }
    }

}
