package com.gdcp.asus_.googleplay.http.protocol;

import com.gdcp.asus_.googleplay.domain.AppInfo;
import com.gdcp.asus_.googleplay.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/15.
 */

public class AppProtocol extends BaseProtocol<ArrayList<AppInfo>>{
    @Override
    public String getKey() {
        return "app";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        LogUtils.i("parseData");
        try {
            JSONArray jsonArray=new JSONArray(result);
            ArrayList<AppInfo>arrayList=new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo=jsonArray.getJSONObject(i);
                AppInfo appInfo=new AppInfo();
                appInfo.setDes(jo.getString("des"));
                appInfo.setDownloadUrl(jo.getString("downloadUrl"));
                appInfo.setIconUrl(jo.getString("iconUrl"));
                appInfo.setId(jo.getString("id"));
                appInfo.setName(jo.getString("name"));
                appInfo.setPackageName(jo.getString("packageName"));
                appInfo.setSize(jo.getLong("size"));
                appInfo.setStars((float) jo.getDouble("stars"));
                arrayList.add(appInfo);
            }
           /* JSONArray picJSONArray=jsonObject.getJSONArray("picture");
            ArrayList<String>picStrings=new ArrayList<>();
            for (int i = 0; i < picJSONArray.length(); i++) {
                picStrings.add(picJSONArray.getString(i));
            }*/
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.i(e.getMessage());
        }
        return null;
    }
}
