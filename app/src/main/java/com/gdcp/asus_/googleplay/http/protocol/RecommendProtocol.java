package com.gdcp.asus_.googleplay.http.protocol;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by asus- on 2017/12/20.
 */

public class RecommendProtocol extends BaseProtocol<ArrayList<String>>{
    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String> mRecommendList = new ArrayList<String>();
            for (int i = 0; i < ja.length(); i++) {
                String str = ja.getString(i);
                mRecommendList.add(str);
            }

            return mRecommendList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
