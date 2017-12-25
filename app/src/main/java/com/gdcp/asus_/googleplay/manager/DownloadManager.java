package com.gdcp.asus_.googleplay.manager;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.domain.DownloadInfo;
import com.gdcp.asus_.googleplay.http.HttpClientFactory;
import com.gdcp.asus_.googleplay.http.HttpHelper;
import com.gdcp.asus_.googleplay.utils.IOUtils;
import com.gdcp.asus_.googleplay.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by asus- on 2017/12/24.
 */

public class DownloadManager {
    public static final int STATE_UNDO = 1;
    public static final int STATE_WAITING = 2;
    public static final int STATE_DOWNLOADING = 3;
    public static final int STATE_PAUSE = 4;
    public static final int STATE_ERROR = 5;
    public static final int STATE_SUCCESS = 6;
    private static DownloadManager downloadManager = new DownloadManager();
    //观察者集合
    private List<DownloadObserver> downloadObservers = new ArrayList<>();
    //下载对象的集合,线程安全
    private ConcurrentHashMap<String, DownloadInfo> downloadInfoHashMap = new ConcurrentHashMap<>();
    //下载任务的集合，线程安全
    private ConcurrentHashMap<String,DownloadTask>downloadTaskHashMap=new ConcurrentHashMap<>();

    private DownloadManager() {

    }

    public static DownloadManager getInstance() {
        return downloadManager;
    }

    //注册观察者
    public void registerObserver(DownloadObserver downloadObserver) {
        if (downloadObserver != null && !downloadObservers.contains(downloadObserver)) {
            downloadObservers.add(downloadObserver);
        }
    }

    //注销观察者
    public void unRegisterObserver(DownloadObserver downloadObserver) {
        if (downloadObserver != null && downloadObservers.contains(downloadObserver)) {
            downloadObservers.remove(downloadObserver);
        }
    }


    //通知下载状态发生变化
    public void notifyDownloadStateChanged(DownloadInfo downloadInfo) {
        for (DownloadObserver downloadObserver : downloadObservers) {
            downloadObserver.onDownloadStateChanged(downloadInfo);
        }
    }

    //通知下载进度发生变化
    public void notifyDownloadProgressChanged(DownloadInfo downloadInfo) {
        for (DownloadObserver downloadObserver : downloadObservers) {
            downloadObserver.onDownloadProgressChanged(downloadInfo);
        }
    }

    //开始下载
    public synchronized void download(AppInfo appInfo) {
        //生成一个下载对象
        //如果之前下载过，接着下载，实现断点续传
        DownloadInfo downloadInfo = downloadInfoHashMap.get(appInfo.getId());
        if (downloadInfo == null) {
            downloadInfo = DownloadInfo.copy(appInfo);
        }
        downloadInfo.setCurrentState(STATE_WAITING);//状态切换为等待状态
        Log.i("download", "等待下载");
        System.out.println("等待下载");
        notifyDownloadStateChanged(downloadInfo);
        //将下载对象放入集合中
        downloadInfoHashMap.put(downloadInfo.getId(), downloadInfo);
        //初始化下载任务，并放入线程池运行
        DownloadTask downloadTask=new DownloadTask(downloadInfo);
        ThreadManager.getThreadPool().execute(downloadTask);
        //将下载任务放入集合
        downloadTaskHashMap.put(downloadInfo.getId(),downloadTask);
    }

    class DownloadTask implements Runnable {
        private DownloadInfo downloadInfo;
        public DownloadTask(DownloadInfo downloadInfo){
            this.downloadInfo=downloadInfo;
        }
        @Override
        public void run() {
            //状态切换为正在下载状态
            downloadInfo.setCurrentState(STATE_DOWNLOADING);
            Log.i("download", "开始下载");
            System.out.println("开始下载");
            //通知更新下载状态
            notifyDownloadStateChanged(downloadInfo);
            File file=new File(downloadInfo.getPath());
            HttpHelper.HttpResult httpResult;
            if (!file.exists()||file.length()!=downloadInfo.getCurrentPos()||downloadInfo.getCurrentPos()==0){
                   //从头开始下载
                //删除无效文件
                file.delete();
                //当前下载位置重置为0
                downloadInfo.setCurrentPos(0);
                //从头开始下载
                 httpResult= HttpHelper.download(HttpHelper.URL+"download?name="+
                        downloadInfo.getDownloadUrl());
            }else {
                 //断点续传下载
                httpResult= HttpHelper.download(HttpHelper.URL+"download?name="+
                        downloadInfo.getDownloadUrl()+"&range="+file.length());
            }

            if (httpResult!=null&&httpResult.getInputStream()!=null){
                 InputStream inputStream=httpResult.getInputStream();
                FileOutputStream fileOutputStream=null;
                try {
                     fileOutputStream=new FileOutputStream(file,true);//true表示在原有文件上追加数据
                    int len=0;
                    byte[]bytes=new byte[1024*10];
                    while ((len=inputStream.read(bytes))!=-1&&downloadInfo.getCurrentState()==STATE_DOWNLOADING){
                        fileOutputStream.write(bytes,0,len);
                        fileOutputStream.flush();//把剩余数据刷入本地
                        long curPos=downloadInfo.getCurrentPos()+len;
                        downloadInfo.setCurrentPos(curPos);
                        //更新下载进度的变化
                        notifyDownloadProgressChanged(downloadInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    IOUtils.close(inputStream);
                    IOUtils.close(fileOutputStream);
                }
                //文件下载结束
                if (file.length()==downloadInfo.getSize()){
                    //表示文件下载完整，下载成功
                    downloadInfo.setCurrentState(STATE_SUCCESS);
                    notifyDownloadStateChanged(downloadInfo);
                }else if (downloadInfo.getCurrentState()==STATE_PAUSE){
                    notifyDownloadStateChanged(downloadInfo);
                }else {
                    //下载失败
                    file.delete();
                    downloadInfo.setCurrentState(STATE_ERROR);
                    downloadInfo.setCurrentPos(0);
                    notifyDownloadStateChanged(downloadInfo);
                }
            }else {
                //网络异常
                file.delete();//删除无效文件
                downloadInfo.setCurrentState(STATE_ERROR);
                downloadInfo.setCurrentPos(0);
                notifyDownloadStateChanged(downloadInfo);
            }
               //从任务集合中移除
            downloadTaskHashMap.remove(downloadInfo.getId());

        }
    }


    //开始安装
    public void install(AppInfo appInfo) {
         DownloadInfo downloadInfo=downloadInfoHashMap.get(appInfo.getId());
         if (downloadInfo!=null){
             //跳到系统安装页面进行安装
             Intent intent=new Intent(Intent.ACTION_VIEW);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             intent.setDataAndType(Uri.parse("file://" + downloadInfo.getPath()),
                     "application/vnd.android.package-archive");
             UIUtils.getContext().startActivity(intent);
         }
    }

    //下载暂停
    public synchronized void pause(AppInfo appInfo) {
        //取出下载对象
        DownloadInfo downloadInfo=downloadInfoHashMap.get(appInfo.getId());
        if (downloadInfo!=null){
            if (downloadInfo.getCurrentState()==STATE_DOWNLOADING||
                    downloadInfo.getCurrentState()==STATE_WAITING){
                //下载状态改为暂停
                downloadInfo.setCurrentState(STATE_PAUSE);
                notifyDownloadStateChanged(downloadInfo);
                DownloadTask downloadTask=downloadTaskHashMap.get(downloadInfo.getId());
                if (downloadTask!=null){
                    //移除下载任务
                    ThreadManager.getThreadPool().cancle(downloadTask);
                }
            }
        }
    }

    /*  声明观察者的接口*/
    public interface DownloadObserver {
        //下载状态发生变化
        public void onDownloadStateChanged(DownloadInfo downloadInfo);

        //下载进度发生变化
        public void onDownloadProgressChanged(DownloadInfo downloadInfo);
    }

    public DownloadInfo getDownloadInfo(AppInfo appInfo){
        return downloadInfoHashMap.get(appInfo.getId());
    }

}
