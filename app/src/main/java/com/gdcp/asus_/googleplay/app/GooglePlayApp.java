package com.gdcp.asus_.googleplay.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.text.AndroidCharacter;

/**
 * Created by asus- on 2017/12/12.
 */

public class GooglePlayApp extends Application{
  public static Context context;
  //主线程id
  public static int mainThreadId;
  public static Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
       context=getApplicationContext();
       handler=new Handler();
        mainThreadId=android.os.Process.myTid();
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }
}
