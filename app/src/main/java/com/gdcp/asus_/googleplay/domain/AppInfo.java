package com.gdcp.asus_.googleplay.domain;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/15.
 */

public class AppInfo {
   /* "id": 1525490,
            "name": "有缘网",
            "packageName": "com.youyuan.yyhl",
            "iconUrl": "app/com.youyuan.yyhl/icon.jpg",
            "stars": 4,
            "size": 3876203,
            "downloadUrl": "app/com.youyuan.yyhl/com.youyuan.yyhl.apk",
            "des": "产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、"*/

   private String id;
    private String name;
    private String packageName;
    private String iconUrl;
    private float stars;
    private String downloadUrl;
    private String des;
    private long size;

    //补充字段，供应用详情使用
    private String author;
    private String date;
    private String downloadNum;
    private String version;
    private ArrayList<SafeInfo>safeInfos;
    private ArrayList<String>screen;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<SafeInfo> getSafeInfos() {
        return safeInfos;
    }

    public void setSafeInfos(ArrayList<SafeInfo> safeInfos) {
        this.safeInfos = safeInfos;
    }

    public ArrayList<String> getScreen() {
        return screen;
    }

    public void setScreen(ArrayList<String> screen) {
        this.screen = screen;
    }

    public static  class SafeInfo{
        private String safeDes;
        private String safeDesUrl;
        private String safeUrl;

        public String getSafeDes() {
            return safeDes;
        }

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public String getSafeUrl() {
            return safeUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }
    }

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
