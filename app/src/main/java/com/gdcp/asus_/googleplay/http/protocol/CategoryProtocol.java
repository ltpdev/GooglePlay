package com.gdcp.asus_.googleplay.http.protocol;

import com.gdcp.asus_.googleplay.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/20.
 */

public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>>{
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        try {
            ArrayList<CategoryInfo> categoryInfos = new ArrayList<CategoryInfo>();
            JSONArray ja = new JSONArray(result);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jsonObject=ja.getJSONObject(i);
                if (jsonObject.has("title")){
                 CategoryInfo categoryInfo=new CategoryInfo();
                 categoryInfo.setTitle(jsonObject.getString("title"));
                    categoryInfo.setTitle(true);
                    categoryInfos.add(categoryInfo);
                }
                if (jsonObject.has("infos")){
                    JSONArray jsonArray=jsonObject.getJSONArray("infos");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jo=jsonArray.getJSONObject(j);
                        CategoryInfo categoryInfo=new CategoryInfo();
                        categoryInfo.setName1(jo.getString("name1"));
                        categoryInfo.setName2(jo.getString("name2"));
                        categoryInfo.setName3(jo.getString("name3"));
                        categoryInfo.setUrl1(jo.getString("url1"));
                        categoryInfo.setUrl2(jo.getString("url2"));
                        categoryInfo.setUrl3(jo.getString("url3"));
                        categoryInfo.setTitle(false);
                        categoryInfos.add(categoryInfo);
                    }
                }
            }

            return categoryInfos;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
