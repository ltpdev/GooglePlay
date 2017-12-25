package com.gdcp.asus_.googleplay.http.protocol;

import com.gdcp.asus_.googleplay.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/21.
 */

public class HomeDetailProtocol extends BaseProtocol<AppInfo>{

    private String packageName;

    public HomeDetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParams() {
        return "&packageName="+packageName;
    }

    @Override
    public AppInfo parseData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
                AppInfo appInfo=new AppInfo();
                appInfo.setDes(jsonObject.getString("des"));
                appInfo.setDownloadUrl(jsonObject.getString("downloadUrl"));
                appInfo.setIconUrl(jsonObject.getString("iconUrl"));
                appInfo.setId(jsonObject.getString("id"));
                appInfo.setName(jsonObject.getString("name"));
                appInfo.setPackageName(jsonObject.getString("packageName"));
                appInfo.setSize(jsonObject.getLong("size"));
                appInfo.setStars((float) jsonObject.getDouble("stars"));

                appInfo.setAuthor(jsonObject.getString("author"));
                appInfo.setDate(jsonObject.getString("date"));
                appInfo.setDownloadNum(jsonObject.getString("downloadNum"));
                appInfo.setVersion(jsonObject.getString("version"));

             JSONArray safeJSONArray=jsonObject.getJSONArray("safe");
             ArrayList<AppInfo.SafeInfo>safeInfos=new ArrayList<>();
            for (int i = 0; i < safeJSONArray.length(); i++) {
                JSONObject jsonObject1 = safeJSONArray.getJSONObject(i);
                AppInfo.SafeInfo safeInfo=new AppInfo.SafeInfo();
                safeInfo.setSafeDes(jsonObject1.getString("safeDes"));
                safeInfo.setSafeDesUrl(jsonObject1.getString("safeDesUrl"));
                safeInfo.setSafeUrl(jsonObject1.getString("safeUrl"));
                safeInfos.add(safeInfo);
            }
            appInfo.setSafeInfos(safeInfos);
            JSONArray screenJSONArray=jsonObject.getJSONArray("screen");
            ArrayList<String>screens=new ArrayList<>();
            for (int i = 0; i < screenJSONArray.length(); i++) {
                screens.add(screenJSONArray.getString(i));
            }
            appInfo.setScreen(screens);
         return appInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
