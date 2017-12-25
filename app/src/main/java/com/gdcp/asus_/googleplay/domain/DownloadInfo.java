package com.gdcp.asus_.googleplay.domain;

import android.os.Environment;

import com.gdcp.asus_.googleplay.manager.DownloadManager;

import java.io.File;

/**
 * 下载信息的封装
 */

public class DownloadInfo {
    private String id;
    private String name;
    private String downloadUrl;
    private long size;
    private String packageName;
    //当前下载位置
    private long currentPos;
    //当前下载状态
    private int currentState;
    private String path;//返回本地下载路径
   public static final String GOOGLE_MARKET="GOOGLE_MARKET";
    public static final String DOWNLOAD="download";
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(long currentPos) {
        this.currentPos = currentPos;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //获取下载进度（0-1）
    public float getProgress() {
        if (size==0){
            return 0;
        }
        float progress = currentPos / (float) size;
        return progress;
    }
//获取本地下载路径
    public String getFilePath(){
            StringBuffer sb=new StringBuffer();
            String sdCard= Environment.getExternalStorageDirectory().getAbsolutePath();
            sb.append(sdCard);
            sb.append(File.separator);
            sb.append(GOOGLE_MARKET);
            sb.append(File.separator);
            sb.append(DOWNLOAD);
            if (createDir(sb.toString())){
                //文件夹存在或者创建完成
                return sb.toString()+File.separator+name+".apk";//返回文件路径
            }
            return null;
    }

    public static DownloadInfo copy(AppInfo appInfo){
        DownloadInfo downloadInfo=new DownloadInfo();
        downloadInfo.setId(appInfo.getId());
        downloadInfo.setCurrentPos(0);
        downloadInfo.setCurrentState(DownloadManager.STATE_UNDO);
        downloadInfo.setDownloadUrl(appInfo.getDownloadUrl());
        downloadInfo.setName(appInfo.getName());
        downloadInfo.setPackageName(appInfo.getPackageName());
        downloadInfo.setPath(downloadInfo.getFilePath());
        downloadInfo.setSize(appInfo.getSize());
        return downloadInfo;
    }

    private boolean createDir(String dir){
        File fileDir=new File(dir);
        //文件夹不存在或者不是一个文件夹
        if (!fileDir.exists()||!fileDir.isDirectory()){
            return fileDir.mkdirs();
        }
        return true;//文件夹存在
    }
}
